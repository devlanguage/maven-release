package org.basic.concurrent.deadlock;

class ClassA {

    synchronized void first(ClassB b) {

        String name = Thread.currentThread().getName();
        System.out.println(name + " entered A.first()");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("A interrupted!");
        }
        System.out.println(name + " trying to call B.last()...");
        b.last();
    }

    synchronized void last() {

        System.out.println("A\'s last");
    }

}

class ClassB {

    synchronized void first(ClassA a) {

        String name = Thread.currentThread().getName();
        System.out.println(name + " entered B.first()");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("B interrupted!");
        }
        System.out.println(name + " trying to call A.last()...");
        a.last();
    }

    synchronized void last() {

        System.out.println("B\'s last");
    }

}

public class SyncMethod_DeadLock implements Runnable {

    ClassA a = new ClassA();
    ClassB b = new ClassB();

    SyncMethod_DeadLock() {

        Thread.currentThread().setName("MainThread");
        Thread t = new Thread(this, "RacingThread");
        t.start(); // 启动新的线程
        a.first(b); // 此处和新的线程并发执行。
        // 调用a的first()方法，获得a的监视器，方法中代码的执行：
        // 先休眠1秒再调用b的last()方法，此时出现死锁
        System.out.println("Back in main thread");
    }

    public void run() {

        // 在此处添加后面注释中的代码可避免死锁
        b.first(a); // 调用b的first()方法，获得b的监视器，方法中的代码执行：
        // 先休眠1秒再调用ａ的last()方法，此时出现死锁
        System.out.println("Back in main thread");
    }

    public static void main(String[] args) {

        new SyncMethod_DeadLock();
    }
}
