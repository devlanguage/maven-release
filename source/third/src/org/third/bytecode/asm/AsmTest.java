
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;

import org.basic.jvm.hello.Helloworld;

public class AsmTest extends ClassLoader implements org.objectweb.asm.Opcodes {

    public static void main(final String args[]) throws Exception {

        // creates a ClassWriter for the Example public class,
        // which inherits from Object

        org.objectweb.asm.ClassWriter cw = new org.objectweb.asm.ClassWriter(0);
        cw.visit(V1_1, ACC_PUBLIC, "Example", null, "java/lang/Object", null);
        org.objectweb.asm.MethodVisitor mw = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
        mw.visitVarInsn(ALOAD, 0);
        mw.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V");
        mw.visitInsn(RETURN);
        mw.visitMaxs(1, 1);
        mw.visitEnd();
        mw = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
        mw.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mw.visitLdcInsn("Hello world!");
        mw.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V");
        mw.visitInsn(RETURN);
        mw.visitMaxs(2, 2);
        mw.visitEnd();
        byte[] code = cw.toByteArray();
        FileOutputStream fos = new FileOutputStream("Example.class");
        fos.write(code);
        fos.close();
        AsmTest loader = new AsmTest();
        Class asmTest = loader.defineClass("Example", code, 0, code.length);
        asmTest.getMethods()[0].invoke(null, new Object[] { null });

        // ------------------------------------------------------------------------
        // Same example with a GeneratorAdapter (more convenient but slower)
        // ------------------------------------------------------------------------

        cw = new org.objectweb.asm.ClassWriter(org.objectweb.asm.ClassWriter.COMPUTE_MAXS);
        cw.visit(V1_1, ACC_PUBLIC, "Example", null, "java/lang/Object", null);
        org.objectweb.asm.commons.Method m = org.objectweb.asm.commons.Method.getMethod("void <init> ()");
        org.objectweb.asm.commons.GeneratorAdapter mg = new org.objectweb.asm.commons.GeneratorAdapter(ACC_PUBLIC, m, null, null, cw);
        mg.loadThis();
        mg.invokeConstructor(org.objectweb.asm.Type.getType(Object.class), m);
        mg.returnValue();
        mg.endMethod();
        m = org.objectweb.asm.commons.Method.getMethod("void main (String[])");
        mg = new org.objectweb.asm.commons.GeneratorAdapter(ACC_PUBLIC + ACC_STATIC, m, null, null, cw);
        mg.getStatic(org.objectweb.asm.Type.getType(System.class), "out", org.objectweb.asm.Type.getType(PrintStream.class));
        mg.push("Hello world!");
        mg.invokeVirtual(org.objectweb.asm.Type.getType(PrintStream.class),
                org.objectweb.asm.commons.Method.getMethod("void println (String)"));
        mg.returnValue();
        mg.endMethod();
        cw.visitEnd();
        code = cw.toByteArray();
        
        loader = new AsmTest();
        asmTest = loader.defineClass("Example", code, 0, code.length);
        asmTest.getMethods()[0].invoke(null, new Object[] { null });
    }
}
