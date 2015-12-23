/****************************************************************************
 *                 TELLABS PROPRIETARY AND CONFIDENTIAL
 *              UNPUBLISHED WORK COPYRIGHT 2009 TELLABS
 *                          ALL RIGHTS RESERVED
 *      NO PART OF THIS DOCUMENT MAY BE USED OR REPRODUCED WITHOUT
 *                   THE WRITTEN PERMISSION OF TELLABS.
 *  Last modifed on 10:49:01 AM Apr 4, 2014
 *
 *****************************************************************************
 */
package org.basic.concurrent.unsafe;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashSet;

import sun.misc.Unsafe;

/**
 * Created on Apr 4, 2014, 10:49:01 AM
 */
class PrivatePlayer {
    private int age = 12;

    private PrivatePlayer() {
        System.out.println("Calling the privarte Constructor");
        this.age = 50;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

public class UnsafeTest {
    private static sun.misc.Unsafe getUnsafe() {
        // Unsafe unsafe = new Unsafe(); //Private Construct
        // Unsafe unsafe = Unsafe.getUnsafe(); //Exception in thread "main" java.lang.SecurityException: Unsafe

        sun.misc.Unsafe usf = null;
        try {
            java.lang.reflect.Field f;
            f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            usf = (sun.misc.Unsafe) f.get(null);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return usf;
    }

    /**
     * @param usf
     * @throws InstantiationException
     */
    private void buildObjectWithoutConstructor(Unsafe usf) throws Exception {
        // PrivatePlayer p = new PrivatePlayer();//The constructor PrivatePlayer() is not visible
        PrivatePlayer p = (PrivatePlayer) usf.allocateInstance(PrivatePlayer.class); // Avoid calling constructor()
        System.out.println("PrivatePlayer.getAge():" + p.getAge()); // // Print 0
        p.setAge(1000);
        System.out.println("PrivatePlayer.getAge():" + p.getAge()); // // Print 1000

        Field pfAge = p.getClass().getDeclaredField("age");
        pfAge.setAccessible(true);
        pfAge.set(p, 1999);
        System.out.println("PrivatePlayer.getAge():" + p.getAge()); // // Print 1000
    }

    public static void main(String[] args) {
        UnsafeTest ust = new UnsafeTest();
        try {
            Unsafe usf = getUnsafe();
            ust.buildObjectWithoutConstructor(usf);
            ust.buildBigArray(usf);
            ust.buildClassFromByteCode(usf);
            ust.buildMemoryCopy(usf);
            ust.buildObjectClone(usf);
            // ust.buildMultpleInheritance(usf);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * @param usf
     */
    private void buildMultpleInheritance(Unsafe usf) {
        long intClassAddress = normalize(getUnsafe().getInt(new Integer(0), 4L));
        long strClassAddress = normalize(getUnsafe().getInt("", 4L));
        getUnsafe().putAddress(intClassAddress + 36, strClassAddress);
        String a = (String) (Object) (new Integer(666));
        System.out.println(a);

    }

    private static long normalize(int value) {
        if (value >= 0)
            return value;
        return (~0L >>> 32) & value;
    }

    /**
     * ///Memory conflict
     * 
     * @param obj
     * @return
     */

    static Object shallowCopy(Object obj) {
        long size = sizeOf(obj);
        long start = toAddress(obj);
        long address = getUnsafe().allocateMemory(size);
        getUnsafe().copyMemory(start, address, size);
        return fromAddress(address);
    }

    static long toAddress(Object obj) {
        Object[] array = new Object[] { obj };
        long baseOffset = getUnsafe().arrayBaseOffset(Object[].class);
        return normalize(getUnsafe().getInt(array, baseOffset));
    }

    static Object fromAddress(long address) {
        Object[] array = new Object[] { null };
        long baseOffset = getUnsafe().arrayBaseOffset(Object[].class);
        getUnsafe().putLong(array, baseOffset, address);
        return array[0];
    }

    /**
     * Much simpler sizeOf can be achieved if we just read size value from the class struct for this object, which
     * located with offset 12 in JVM 1.7 32 bit.
     * 
     * @param object
     * @return
     */
    public static long sizeOfFromClassStruct(Object object) {
        return getUnsafe().getAddress(normalize(getUnsafe().getInt(object, 4L)) + 12L);
    }

    public static long sizeOf(Object o) {
        Unsafe u = getUnsafe();
        HashSet<Field> fields = new HashSet<Field>();
        Class c = o.getClass();
        while (c != Object.class) {
            for (Field f : c.getDeclaredFields()) {
                if ((f.getModifiers() & Modifier.STATIC) == 0) {
                    fields.add(f);
                }
            }
            c = c.getSuperclass();
        }

        // get offset
        long maxSize = 0;
        for (Field f : fields) {
            long offset = u.objectFieldOffset(f);
            if (offset > maxSize) {
                maxSize = offset;
            }
        }

        return ((maxSize / 8) + 1) * 8; // padding
    }

    /**
     * @param usf
     * @throws Exception
     */
    private void buildObjectClone(Unsafe usf) throws Exception {
        UnsafeClass u1 = new UnsafeClass();
        UnsafeClass u2 = UnsafeClass.class.newInstance();
        UnsafeClass u3 = (UnsafeClass) usf.allocateInstance(UnsafeClass.class);
        UnsafeClass u4 = (UnsafeClass) shallowCopy(u3); // /Memory conflict
    }

    /**
     * 这个好似很有趣吧？实事就是这样的。开发人员创建密码或者是保证密码到字符串中，然后在应用程序的代码中使用这些密码，使用过后，聪明的程序员会把字符串的引用设为NULL，因此它就不会被引用着并且很容易被垃圾收集器给回收掉。
     * 但是从你将引用设为NULL到被垃圾收集器收集的这个时间段之内（原文：But from the time, you made the reference null to the time garbage collector
     * kicks in），它是处于字符串池中的，并且在你系统中进行一个复杂的攻击（原文：And a sophisticated attack on your
     * system），也是可以读取到你的内存区域并且获得密码，虽然机会很小，但是总是存在的。 这就是为什么建议使用char[]数组存放密码，当使用完过后，你可以迭代处理当前数组，修改/清空这些字符。
     * 另外一个方式就是使用魔术类Unsafe
     * 。你可以创建另外一个和当前密码字符串具有相同长度的临时字符串，将临时密码中的每个字符都设值为"?"或者"*"（任何字符都可以），当你完成密码的逻辑后，你只需要简单的将临时密码中的字节数组拷贝到原始的密码串中
     * ，这就是使用临时密码覆盖真实的密码。
     */
    private void buildMemoryCopy(Unsafe usf) {
        String password = new String("l00k@myHor$e");
        String fake = new String(password.replaceAll(".", "?"));
        System.out.println(password); // l00k@myHor$e
        System.out.println(fake); // ????????????

        getUnsafe().copyMemory(fake, 0L, null, toAddress(password), sizeOf(password));

        System.out.println(password); // ????????????
        System.out.println(fake); // ????????????
    }

    /**
     * @param usf
     */
    // Method to read .class file
    private static byte[] getClassContent() throws Exception {
        File f = new File("C:/Users/ygong/workspace/JavaBasic_output/org/basic/concurrent/unsafe/UnsafeClass.class");
        FileInputStream input = new FileInputStream(f);
        byte[] content = new byte[(int) f.length()];
        input.read(content);
        input.close();
        return content;
    }

    private void buildClassFromByteCode(Unsafe usf) throws Exception {
        // Sample code to craeet classes
        byte[] classContents = getClassContent();
        // CodeSource cs = new CodeSource(new URL(""), certs)
        java.security.ProtectionDomain pd = null; // new ProtectionDomain(codesource, permissions)
        ClassLoader cl = getClass().getClassLoader();
        Class c = getUnsafe().defineClass(null, classContents, 0, classContents.length, cl, pd);

        Object helloClass = c.newInstance();
        java.lang.reflect.Method unsafeClassHello = c.getMethod("hello", new Class[] { String.class });
        unsafeClassHello.invoke(helloClass, new Object[] { "guest" });

    }

    /**
     * @param usf
     */
    private void buildBigArray(Unsafe usf) {
        class SuperArray {
            private final static int BYTE = 1;
            private long size;
            private long address;

            public SuperArray(long size) {
                this.size = size;
                // 得到分配内存的起始地址
                // allocateMemory will not allocate the physical memory immeidately, so some issue will happens if you
                // set the value into memory area started from this pointer
                address = getUnsafe().allocateMemory(size * BYTE);
            }

            public void set(long i, byte value) {
                getUnsafe().putByte(address + i * BYTE, value);
            }

            public int get(long idx) {
                return getUnsafe().getByte(address + idx * BYTE);
            }

            public long size() {
                return size;
            }
        }
        long SUPER_SIZE = (long) Integer.MAX_VALUE; // 4G
        SuperArray array = new SuperArray(SUPER_SIZE);
        long sum = 0;
        System.out.println("Array size:" + array.size()); // 4294967294
        for (int i = 0, size = 100; i < size; i++) {
            array.set((long) Integer.MAX_VALUE + i, (byte) 3); // if increase "size" by 100000,
                                                               // EXCEPTION_ACCESS_VIOLATION (0xc0000005) at
                                                               // pc=0x000000006e6bde85, pid=25264, tid=27348 Failed to
                                                               // write core dump. Minidumps are not enabled by default
                                                               // on client versions of Windows
            sum += array.get((long) Integer.MAX_VALUE + i);
        }
        System.out.println("Sum of 100 elements:" + sum); // 300
    }
}
