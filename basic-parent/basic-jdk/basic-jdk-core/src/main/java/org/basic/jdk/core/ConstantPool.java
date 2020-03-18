package org.basic.jdk.core;

public class ConstantPool {
    public static void testHeapObject() {
        Integer i1 = new Integer(1);
        Integer i2 = new Integer(1);
        // i1,i2分别位于堆中不同的内存空间

        System.out.println(i1 == i2);// 输出false

        Integer i3 = 1;
        Integer i4 = 1;
        // i3,i4指向常量池中同一个内存空间
        System.out.println(i3 == i4);// 输出true

        // 很显然，i1,i3位于不同的内存空间
        System.out.println(i1 == i3);// 输出false

    }

    public static void testString() {
        // s1,s2分别位于堆中不同空间

        String s1 = new String("hello");
        String s2 = new String("hello");
        System.out.println(s1 == s2);// 输出false

        // s3,s4位于池中同一空间
        String s3 = "hello";
        String s4 = "hello";
        System.out.println(s3 == s4);// 输出true
    }

    public static void testPrimitive() {
        // java中基本类型的包装类的大部分都实现了常量池技术，这些类是Byte,Short,Integer,Long,Character,Boolean,另外两种浮点数类型的包装类则没有实现。另外Byte,Short,Integer,Long,Character这5种整型的包装类也只是在对应值小于等于127时才可使用对象池，也即对象不负责创建和管理大于127的这些类的对象。以下是一些对应的测试代码
        // 5种整形的包装类Byte,Short,Integer,Long,Character的对象，

        // 在值小于127时可以使用常量池

        Integer i1 = 127;
        Integer i2 = 127;
        System.out.println(i1 == i2);// 输出true

        // 值大于127时，不会从常量池中取对象
        Integer i3 = 128;
        Integer i4 = 128;
        System.out.println(i3 == i4);// 输出false

        // Boolean类也实现了常量池技术
        Boolean bool1 = true;
        Boolean bool2 = true;
        System.out.println(bool1 == bool2);// 输出true

        // 浮点类型的包装类没有实现常量池技术
        Double d1 = 1.0;
        Double d2 = 1.0;
        System.out.println(d1 == d2);// 输出false
    }

    public static void main(String[] args) {

    }
}
