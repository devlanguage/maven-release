package org.basic.jdk.core.s2_logic;

import static org.basic.common.util.SystemUtils.*;

class Y {
    Y(String a) {
        println(a + ":" + Y.class.getSimpleName());
    }
}

class X {
    static Y y1 = new Y("StaticX1:");
    static {
        println("static block: " + X.class.getSimpleName());
    }
    static Y y2 = new Y("StaticX2");
    Y b = new Y("CallerX");

    X() {
        println("X");
    }
}

class Z extends X {
    static {
        println("static block: " + Z.class.getSimpleName());
    }
    Y y = new Y("CallerZ");

    Z() {
        println("Z");
    }

    public static void main(String[] args) {
        new Z();
    }
}

public class for_while {
    native void test();

    public static void main(String[] args) {
        Z.main(args);

        int a = 5;
        System.out.println("Value is - " + ((a < 5) ? 9.9 : 9)); // Value is - 9.0

        int[] ageArray = new int[] { 100, 101, 102, 103 };

        test_for_语句(ageArray);
        test_while_语句(ageArray);

    }

    private static void test_while_语句(int[] ageArray) {
        // 第一种while循环
        System.out.println("=============第一种while循环==================");
        int i = 0;
        while (i < ageArray.length) {
            System.out.println("age=" + ageArray[i]);
            i++;
        }

        int k = 0;
        System.out.println("=============第一种while, do-while循环==================");
        do {
            System.out.println("age=" + ageArray[k]);
            k++;
        } while (k < ageArray.length);

        int l = 0;
        do {
            System.out.println("Doing it for l is:" + l);
        } while (--l > 0);
        System.out.println("Finish");

    }

    private static void test_for_语句(int[] ageArray) {
        // 第一种for循环
        System.out.println("=============第一种for循环==================");
        for (int age : ageArray) {
            System.out.println("age=" + age);
        }

        // 第一种for循环
        System.out.println("=============第二种for循环==================");
        for (int i = 0; i < ageArray.length; i++) {
            System.out.println("age=" + ageArray[i]);
        }
    }
}
