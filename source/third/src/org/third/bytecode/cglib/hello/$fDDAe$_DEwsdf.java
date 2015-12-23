package org.third.bytecode.cglib.hello;

import com.sun.org.apache.bcel.internal.generic.GOTO;

public class $fDDAe$_DEwsdf {

    static void testGoto(int type) {

        label1: for (int i = 0; i < 10; i++) {
            // label1: Error, continue cannot find out the Label label1
            System.out.println("i = " + i);

            for (int x = 0; x < 10; x++) {
                System.out.println("x = " + x);
                continue label1;
            }
        }

    }

    public static void main(String[] args) {

        testGoto(12);
    }
}
