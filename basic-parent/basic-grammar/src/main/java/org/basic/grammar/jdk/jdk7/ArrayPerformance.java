package org.basic.grammar.jdk.jdk7;

import java.util.Date;

public class ArrayPerformance {

    public final static int frequency = 1000;
    public final static int array_count = 1000;

    public static void main(String[] args) {

        Counter c = new Counter(frequency);
        for (int i = 0; i < 10; i++) {
            ArrayConstructor constructor = new ArrayConstructor(c);
            constructor.start();
        }

    }

    static class ArrayConstructor extends Thread {

        Counter c;

        ArrayConstructor(Counter c) {

            this.c = c;
        }

        public void test(Date[] ds) {

            for (int i = 0; i < array_count; i++) {
                Date d = new Date();
                d.setDate(234234);
                String t = d.toString();
                if ("TEST_STRING".equals(t)) {

                } else {

                }
                // ds[i] = d;
            }
        }

        @Override
        public void run() {

            while (true) {
                c.printPerformance("main");
                Date[] ds = new Date[array_count];
                test(ds);
            }
        }
    }

}
