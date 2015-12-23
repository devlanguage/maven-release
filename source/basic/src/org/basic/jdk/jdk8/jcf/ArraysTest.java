/****************************************************************************
 *                 TELLABS PROPRIETARY AND CONFIDENTIAL
 *              UNPUBLISHED WORK COPYRIGHT 2009 TELLABS
 *                          ALL RIGHTS RESERVED
 *      NO PART OF THIS DOCUMENT MAY BE USED OR REPRODUCED WITHOUT
 *                   THE WRITTEN PERMISSION OF TELLABS.
 *  Last modifed on 1:05:38 PM Apr 30, 2014
 *
 *****************************************************************************
 */
package org.basic.jdk.jdk8.jcf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * Created on Apr 30, 2014, 1:05:38 PM
 */
public class ArraysTest {
    public static void main(String[] args) {
        int i = 1000;
        while (i-- > 0) {
            if(i%100==0){
                System.out.println("Call System.gc(), i="+i);
                System.gc();
            }
            testFinalize(i);
        }
        while(true){
            try {
                System.out.println("Thread state changed");
                Thread.sleep(5000);
                System.gc();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
            }
        }
    }

    static class FinailzeTester<T> {
        int i;

        FinailzeTester(int i) {
            this.i = i;
        }

        @Override
        protected void finalize() throws Throwable {
            System.out.println("\t Finalize: "+i);
            super.finalize();
        }
    }

    private static void testFinalize(int i) {
        FinailzeTester<String> f = new FinailzeTester<String>(i);
        
    }

    static void testArrray() {
        // String[] f1 = new String[] { "2.0F", "8.0F", "4.5F" };
        Double[] f1 = new Double[] { 2.0, -2.3, 8.0, 4.0, 4.5 };
        System.out.println("before sort:");
        Arrays.asList(f1).stream().forEach(x -> {
            System.out.println(x);
        });
        Arrays.parallelSort(f1, (a, b) -> {
            return (int) (a.compareTo(b));
        });
        java.util.function.Consumer<Double> d = new Consumer<Double>() {
            @Override
            public void accept(Double arg0) {
                // TODO Auto-generated method stub

            }
        };
        java.util.function.Consumer<Double> d2 = (d21) -> {
        };
        java.util.function.Consumer<Double> d3 = (Double d31) -> {
        };

        System.out.println("after sort:");
        Arrays.asList(f1).stream().forEach(System.out::println);
        System.out.println("filter out minus data:");
        Arrays.asList(f1).stream().filter(x -> (x > 0)).forEach(System.out::println);
        // Performat test
        int max = 1000000;
        List<String> values = new ArrayList<>(max);
        for (int i = 0; i < max; i++) {
            UUID uuid = UUID.randomUUID();
            values.add(uuid.toString());
        }
        // sort serially
        long t10 = System.nanoTime();
        long count1 = values.stream().sorted().count();
        System.out.println(count1);
        long t11 = System.nanoTime();
        long millis1 = TimeUnit.NANOSECONDS.toMillis(t11 - t10);

        System.out.println(String.format("sequential sort took: %d ms", millis1));
        long t20 = System.nanoTime();
        long count2 = values.parallelStream().sorted().count();
        System.out.println(count2);
        long t21 = System.nanoTime();
        long millis2 = TimeUnit.NANOSECONDS.toMillis(t21 - t20);
        System.out.println(String.format("parallel sort took: %d ms", millis2));

    }
}
