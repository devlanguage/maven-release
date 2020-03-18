package org.basic.jdk.core.ucf.synchronized_block.multiple_object_lock;

/**
 * Resource1.java演示了三个线程（包括main线程）试图进入某个类的三个不同的方法的同步块中，这些同步块处在不同的方法中，
 * 并且是同步到三个不同的对象（synchronized(this)，synchronized (syncObject1)，synchronized (syncObject2)），
 * �?��对它们的方法中的临界资源访问是独立的�? */

public class Tester {

    public static void main(String argv[]) {

        final ResourceA2 rs = new ResourceA2();

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
