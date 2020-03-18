package org.basic.jdk.core.ucf.synchronized_block.single_object_lock;

/**
 * ResourceA.java演示了三个线程（包括main线程）试图进入某个类的三个不同的方法的同步块中，
 * 虽然这些同步块处在不同的方法中，但由于是同步到同�?��对象（当前对象synchronized （this）），所以对它们的方法依然是互斥的�?
 * 
 * 
 */

public class Tester {

    public static void main(String argv[]) {

        final ResourceA1 rs = new ResourceA1();

        new Thread() {

            public void run() {

                rs.testMethod1();
            }
        }.start();

        new Thread() {

            public void run() {

                rs.testMethod2();
            }
        }.start();

        rs.testMethod3();
    }
}
