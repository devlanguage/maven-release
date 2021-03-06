package org.basic.jdk.core.ucf.synchronized_block.multiple_object_lock;

import java.util.concurrent.TimeUnit;

/**
 * 
 */
public class ResourceA2 {

    private Object syncObject1 = new Object();
    private Object syncObject2 = new Object();

    public void testMethod1() {

        // other operations should not be locked...
        System.out.println(Thread.currentThread().getName() + ":not synchronized in testMethod1()");
        synchronized (this) {
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName()
                        + ":synchronized in testMethod1()");
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void testMethod2() {

        // other operations should not be locked...
        System.out.println(Thread.currentThread().getName() + ":not synchronized in testMethod2()");
        synchronized (syncObject1) {
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName()
                        + ":synchronized in testMethod2()");
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void testMethod3() {

        // other operations should not be locked...
        System.out.println(Thread.currentThread().getName() + ":not synchronized in testMethod3()");
        synchronized (syncObject2) {
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName()
                        + ":synchronized in testMethod3()");
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
