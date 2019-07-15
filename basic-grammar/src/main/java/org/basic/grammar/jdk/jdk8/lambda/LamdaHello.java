package org.basic.grammar.jdk.jdk8.lambda;

import java.util.ArrayList;
import java.util.List;

public final class LamdaHello {
    public String fstr1 = "public_string_f1";
    public int fint1 = 100;

    public static void main(String[] args) {
        testVariable();
        testLamda();
    }

    @FunctionalInterface
    interface FunctionInterfaceUser<T> {
        public boolean convertObject(T x, T y);
    }

    public static final void testLamda() {
        List<String> users = new ArrayList<String>(); // dup : duplicate top
                                                      // single-word item on the
                                                      // stack
                                                      // ASTORE 0: store object
                                                      // reference in local
                                                      // variable
        users.stream().filter(x -> {
            return true;
        }).forEach(x -> {
        });

        Runnable r = () -> System.out.println("hello world");
        FunctionInterfaceUser<Object> u1 = (Object x, Object y) -> {
            System.out.println("Call function interface convertObject()");
            return true;
        };
        u1.convertObject(12, 234);

        LamdaHello h = new LamdaHello();
        // h.testFunctionInterface(u1);

    }

    private void testFunctionInterface(FunctionInterfaceUser u) {

    }

    // Line7
    public static final void testLogical() throws RuntimeException {
        if (true) {
            int x = 1;
        }
        int sw_int1 = 3;
        switch (sw_int1) {
            case 1000: {
                int sw1 = 10001;
                break;
            }
            case 2000: {
                int sw2 = 20001;
                break;
            }
            default: {
                int sw3 = 30001;
            }
        }
        String sw_str1 = "3";// LDC "3" (Push SingleWord constant onto stack),
                             // ASTORE 1(Store Object Ref in local variable)
        switch (sw_str1) { // ALOAD 1: sw_str1, retrieve object ref from local
                           // variable
            case "1000": {
                int sw1 = 10001;
                break;
            }
            case "2000": {
                int sw2 = 20001;
                break;
            }
            default: {
                int sw3 = 30001;
            }
        }

    }

    private static void testVariable() {
        byte byte1 = 127;// BIPUSH 127, ISTORE 1
        short short1 = 32767;// SIPUSH 32767, ISTORE 2
        int int1 = 32767; // SIPUSH 32767, ISTORE 3
        int int2 = 32768; // LDC 32768 ISTORE 4
        int int3 = 2147483647; // LDC 2147483647 ISTORE 5

        int x, b, c;

        System.out.println(Integer.MAX_VALUE);
        // Integer,
        // -1: ICONST_M1
        // 0-5: ICONST_0-ICONST_5
        // -128~-2/ 6~127: BIPUSH
        // <-128/ >127: SIPUSH

        Long a1 = -129L; // a1!=a2, a1!=a3, (int)a1=a2, int(a1)=a3
        Long a2 = -129L; //
        Long a3 = new Long(128);
        System.out.println(a1 == a2); // -128<=a1<=127: true, otherwise: false
        System.out.println(a1.compareTo(a3));

        try {
//            System.exit(-1);

            return;
        } finally {
            int abc = 123;
        }

    }
}
