package org.basic.jdk.core.jcf;

import java.util.HashMap;
import java.util.concurrent.CyclicBarrier;

/**
 * Created on Mar 19, 2014, 12:31:05 PM
 */


public class HashMapCpu100 {
    private static final HashMap hashMap = new HashMap(1, 0.9F);
    static final int THREAD_SIZE = 10;
    static final CyclicBarrier barrier = new CyclicBarrier(THREAD_SIZE);
    static final CyclicBarrier barrier2 = new CyclicBarrier(THREAD_SIZE, new Runnable() {

        @Override
        public void run() {
            System.out.println("All parties finished, start to merge result");
        }
    });

    public static void main(String[] args) {
        // for (int j = 0; j < THREAD_SIZE; ++j) {
        // new Thread() {
        // public void run() {
        // String name = Thread.currentThread().getName();
        // try {
        // System.out.println(name + " initialize" + ", waitting=" + barrier.getNumberWaiting() + ",parties="
        // + barrier.getParties());
        // Thread.sleep(1000);
        // System.out.println("name: " + barrier.await());
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
        // System.out.println(name + ": hash test");
        // for (int i = 0; i < 500; i++) {
        // hashMap.put(new Integer(i), Integer.valueOf(i));
        // }
        // }
        // }.start();
        // }
        //
        for (int j = 1; j < THREAD_SIZE; ++j) {
            new Thread() {
                public void run() {
                    String name = Thread.currentThread().getName();
                    try {
                        System.out.println(name + " initialize" + ", waitting=" + barrier2.getNumberWaiting() + ",parties="
                                + barrier2.getParties());
                        Thread.sleep(1000);
                        System.out.println("name: " + barrier2.await());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println(name + ": hash test");
                    for (int i = 0; i < 500; i++) {
                        hashMap.put(new Integer(i), Integer.valueOf(i));
                    }
                }
            }.start();
        }
        while(true)
        try {
            System.out.println( ", waitting=" + barrier2.getNumberWaiting() + ",parties="
                    + barrier2.getParties());
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}