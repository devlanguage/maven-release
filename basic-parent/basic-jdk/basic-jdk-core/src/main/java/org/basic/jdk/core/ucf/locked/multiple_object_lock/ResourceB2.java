package org.basic.jdk.core.ucf.locked.multiple_object_lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 */
public class ResourceB2 {

    private Lock lock1 = new ReentrantLock();
    private Lock lock2 = new ReentrantLock();
    private Lock lock3 = new ReentrantLock();

    public void testMethod1() {

        // other operations should not be locked...
        System.out.println(Thread.currentThread().getName() + ":not synchronized in testMethod1()");
        lock1.lock();
        try {
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName()
                        + ":synchronized in testMethod1()");
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            lock1.unlock();
        }
    }

    public void testMethod2() {

        // other operations should not be locked...
        System.out.println(Thread.currentThread().getName() + ":not synchronized in testMethod2()");
        lock2.lock();
        try {
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName()
                        + ":synchronized in testMethod2()");
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            lock2.unlock();
        }
    }

    public void testMethod3() {

        // other operations should not be locked...
        System.out.println(Thread.currentThread().getName() + ":not synchronized in testMethod3()");
        lock3.lock();
        try {
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName()
                        + ":synchronized in testMethod3()");
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            lock3.unlock();
        }
    }

   
}
