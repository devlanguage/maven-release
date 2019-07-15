package org.basic.grammar.ucf.locked.single_object_lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 */
public class ResourceB1 {

    private Lock lock = new ReentrantLock();

    public void testMethod1() {

        // other operations should not be locked...
        System.out.println(Thread.currentThread().getName() + ":not synchronized in testMethod1()");
        lock.lock();
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
            lock.unlock();
        }
    }

    public void testMethod2() {

        // other operations should not be locked...
        System.out.println(Thread.currentThread().getName() + ":not synchronized in testMethod2()");
        lock.lock();
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
            lock.unlock();
        }
    }

    public void testMethod3() {

        // other operations should not be locked...
        System.out.println(Thread.currentThread().getName() + ":not synchronized in testMethod3()");
        lock.lock();
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
            lock.unlock();
        }
    }

}
