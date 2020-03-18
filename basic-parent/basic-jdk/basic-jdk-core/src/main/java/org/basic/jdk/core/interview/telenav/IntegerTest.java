package org.basic.jdk.core.interview.telenav;

public class IntegerTest {

    private static Integer i = new Integer(30);

    public static void main(String args[]) {

        Integer i = new Integer(10);
        Integer i1 = new Integer(10);
        int ii = 5;
        System.out.println(i + " " + i1 + " " + ii);
        test(i, i1, ii);
        System.out.println(i + " " + i1 + " " + ii);

        IntegerTest it = new IntegerTest();
        System.out.println(it);
        test(it);
        System.out.println(it);
        // Output:
        //  10 10 5
        //  10 10 5
        //  org.basic.interview.telenav.IntegerTest@8813f2
        //  org.basic.interview.telenav.IntegerTest@8813f2

    }

    private static void test(Integer i, Integer i1, int ii) {

        i = new Integer(20);
        i1 = IntegerTest.i;
        ii += 10;
    }

    private static void test(IntegerTest it) {

        it = new IntegerTest();
    }
}
