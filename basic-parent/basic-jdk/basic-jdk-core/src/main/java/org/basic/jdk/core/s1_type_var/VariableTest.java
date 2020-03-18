package org.basic.jdk.core.s1_type_var;

import java.util.List;

public class VariableTest {

    public static void main(String[] args) {
        VariableTest obj1 = new VariableTest();
        obj1.test();
    }

    public void test() {

        // 有效值: true, false
        boolean b1 = true;
        boolean b2;
        Boolean b3; // it should be initialized before using
        Boolean b4 = new Boolean("test");// equalIgnoreCase("true")
        // System.out.println(b3);// error: variable b3 might not have been initialized
        System.out.println(b4);// print: false

        // 有效值, -128 ~ 127
        byte bt0 = (byte) -129; // if < -128, print (256-abs(bt0)) if > 127, Print (bt0-256)
        System.out.println(bt0);
        byte bt1 = 23;
        byte bt3 = 0X7F;
        byte bt4 = 0B00111;
        byte bt5 = 0B0010_10_01;
        System.out.println("byte range:" + Byte.MIN_VALUE + " ~ " + Byte.MAX_VALUE);

        short sht1 = 233;
        short sht2 = 0X32, sht3 = 0b010;
        System.out.println("short range:" + Short.MIN_VALUE + " ~ " + Short.MAX_VALUE);
        int int1 = 233;
        System.out.println("int range:" + Integer.MIN_VALUE + " ~ " + Integer.MAX_VALUE);

        long long1 = 233, long2 = 23L;
        long long3 = 0x123_333_FF, long4 = 0b0000_1101_1010;
        System.out.println("long range:" + Long.MIN_VALUE + " ~ " + Long.MAX_VALUE);

        char char1 = '啊'; // 任意的"一个"unicode, 比如'a', 'd', '?', '@', '$', '字','符'
        System.out.println("byte range:" + Character.MIN_VALUE + " ~ " + Character.MAX_VALUE);

        float f1 = 23.43F;
        System.out.println("float range:" + Float.MIN_VALUE + " ~ " + Float.MAX_VALUE);
        double double1 = 232342.3423;
        System.out.println("double range:" + Double.MIN_VALUE + " ~ " + Double.MAX_VALUE);
    }
}
