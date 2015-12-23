/****************************************************************************
 *                 TELLABS PROPRIETARY AND CONFIDENTIAL
 *              UNPUBLISHED WORK COPYRIGHT 2009 TELLABS
 *                          ALL RIGHTS RESERVED
 *      NO PART OF THIS DOCUMENT MAY BE USED OR REPRODUCED WITHOUT
 *                   THE WRITTEN PERMISSION OF TELLABS.
 *  Last modifed on 10:28:33 AM Apr 9, 2014
 *
 *****************************************************************************
 */
package org.basic.aop.asm;

import java.io.File;
import java.io.FileOutputStream;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * Created on Apr 9, 2014, 10:28:33 AM
 */
public class AsmTest {
    class AddSecurityCheckMethodAdapter extends org.objectweb.asm.MethodVisitor {
        public AddSecurityCheckMethodAdapter(MethodVisitor mv) {
            super(Opcodes.ASM5, mv);
        }

        public void visitCode() {
            visitMethodInsn(org.objectweb.asm.Opcodes.INVOKESTATIC, "SecurityChecker", "checkSecurity", "()V", true);
        }
    }

    class AddSecurityCheckClassAdapter extends org.objectweb.asm.ClassVisitor {

        public AddSecurityCheckClassAdapter(org.objectweb.asm.ClassVisitor cv) {
            // Responsechain 的下一个 ClassVisitor，这里我们将传入 ClassWriter，
            // 负责改写后代码的输出
            super(0, cv);
        }

        // 重写 visitMethod，访问到 "operation" 方法时，
        // 给出自定义 MethodVisitor，实际改写方法内容
        public org.objectweb.asm.MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature,
                                                           final String[] exceptions) {
            org.objectweb.asm.MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
            MethodVisitor wrappedMv = mv;
            if (mv != null) {
                // 对于 "operation" 方法
                if (name.equals("operation")) {
                    // 使用自定义 MethodVisitor，实际改写方法内容
                    wrappedMv = new AddSecurityCheckMethodAdapter(mv);
                }
            }
            return wrappedMv;
        }
    }

    public static final String OUTPUT = "C:/Users/ygong/workspace/JavaBasic_output";

    public static void updateClassFile() throws Exception {
        org.objectweb.asm.ClassReader cr = new ClassReader("org.basic.aop.asm.AsmAccount");
        org.objectweb.asm.ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        // org.objectweb.asm.ClassAdapter classAdapter = new AddSecurityCheckClassAdapter(cw);
        // cr.accept(classAdapter, ClassReader.SKIP_DEBUG);
        cr.accept(cw, ClassReader.SKIP_DEBUG);
        byte[] data = cw.toByteArray();
        // /JavaBasic/JavaBasic_output/org/basic/aop/asm/AsmAccount.class
        // C:/Users/ygong/workspace/JavaBasic_output/org/basic/aop/asm/AsmAccount.class
        File file = new File(OUTPUT + "/org/basic/aop/asm/AsmAccount.class");
        FileOutputStream fout = new FileOutputStream(file);
        fout.write(data);
        fout.close();
    }

    public static void generateNewClass() throws Exception {

        // creates a ClassWriter for the Example public class,
        // which inherits from Object
        ClassWriter cw = new ClassWriter(0);
        cw.visit(org.objectweb.asm.Opcodes.V1_1, org.objectweb.asm.Opcodes.ACC_PUBLIC, "org.basic.aop.asm.AsmHello", null,
                "java/lang/Object", null);

        // creates a MethodWriter for the (implicit) constructor
        MethodVisitor mw = cw.visitMethod(org.objectweb.asm.Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
        // pushes the 'this' variable
        mw.visitVarInsn(org.objectweb.asm.Opcodes.ALOAD, 0);
        // invokes the super class constructor
        mw.visitMethodInsn(org.objectweb.asm.Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
        mw.visitInsn(org.objectweb.asm.Opcodes.RETURN);
        // this code uses a maximum of one stack element and one local variable
        mw.visitMaxs(1, 1);
        mw.visitEnd();

        // creates a MethodWriter for the 'main' method
        mw = cw.visitMethod(org.objectweb.asm.Opcodes.ACC_PUBLIC + org.objectweb.asm.Opcodes.ACC_STATIC, "main", "([Ljava/lang/String;)V",
                null, null);
        // pushes the 'out' field (of type PrintStream) of the System class
        mw.visitFieldInsn(org.objectweb.asm.Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        // pushes the "Hello World!" String constant
        mw.visitLdcInsn("Hello world!");
        // invokes the 'println' method (defined in the PrintStream class)
        mw.visitMethodInsn(org.objectweb.asm.Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
        mw.visitInsn(org.objectweb.asm.Opcodes.RETURN);
        // this code uses a maximum of two stack elements and two local
        // variables
        mw.visitMaxs(2, 2);
        mw.visitEnd();

        // gets the bytecode of the Example class, and loads it dynamically
        byte[] code = cw.toByteArray();

        FileOutputStream fos = new FileOutputStream(OUTPUT + "/org/basic/aop/asm/AsmHello.class");
        fos.write(code);
        fos.close();
        Thread.sleep(100);
        org.basic.aop.asm.AsmHello.main(null);
        //
        // Helloworld loader = new Helloworld();
        // Class<?> exampleClass = loader.defineClass("Example", code, 0, code.length);
        //
        // // uses the dynamically generated class to print 'Helloworld'
        // exampleClass.getMethods()[0].invoke(null, new Object[] { null });
        //
        // // ------------------------------------------------------------------------
        // // Same example with a GeneratorAdapter (more convenient but slower)
        // // ------------------------------------------------------------------------
        //
        // cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        // cw.visit(org.objectweb.asm.Opcodes.V1_1, org.objectweb.asm.Opcodes.ACC_PUBLIC, "Example", null,
        // "java/lang/Object", null);
        //
        // // creates a GeneratorAdapter for the (implicit) constructor
        // org.objectweb.asm.commons.Method m = org.objectweb.asm.commons.Method.getMethod("void <init> ()");
        // org.objectweb.asm.commons.GeneratorAdapter mg = new org.objectweb.asm.commons.GeneratorAdapter(
        // org.objectweb.asm.Opcodes.ACC_PUBLIC, m, null, null, cw);
        // mg.loadThis();
        // mg.invokeConstructor(org.objectweb.asm.Type.getType(Object.class), m);
        // mg.returnValue();
        // mg.endMethod();
        //
        // // creates a GeneratorAdapter for the 'main' method
        // m = org.objectweb.asm.commons.Method.getMethod("void main (String[])");
        // mg = new org.objectweb.asm.commons.GeneratorAdapter(org.objectweb.asm.Opcodes.ACC_PUBLIC +
        // org.objectweb.asm.Opcodes.ACC_STATIC, m,
        // null, null, cw);
        // mg.getStatic(org.objectweb.asm.Type.getType(System.class), "out",
        // org.objectweb.asm.Type.getType(PrintStream.class));
        // mg.push("Hello world!");
        // mg.invokeVirtual(org.objectweb.asm.Type.getType(PrintStream.class),
        // org.objectweb.asm.commons.Method.getMethod("void println (String)"));
        // mg.returnValue();
        // mg.endMethod();
        //
        // cw.visitEnd();
        //
        // code = cw.toByteArray();
        // loader = new Helloworld();
        // exampleClass = loader.defineClass("Example", code, 0, code.length);
        //
        // // uses the dynamically generated class to print 'Helloworld'
        // exampleClass.getMethods()[0].invoke(null, new Object[] { null });
    }

    public static void main(String[] args) {
        try {
            generateNewClass();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // try {
        // updateClassFile();
        // } catch (Exception e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
        // AsmAccount acc = new AsmAccount();
        // acc.operation();
        // Generates the bytecode corresponding to the following Java class:
        //
        // public class Example {
        // public static void main (String[] args) {
        // System.out.println("Hello world!");
        // }
        // }

    }
}
