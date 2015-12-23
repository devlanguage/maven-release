package org.basic.jdk.jdk5.concurrent.betterPerformance;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockBeanchmark implements CounterIntf {

    private volatile long count = 0;

    private Lock lock;

    public ReentrantLockBeanchmark() {

        // ʹ�÷ǹ�ƽ����true���ǹ�ƽ��
        lock = new ReentrantLock(false);
    }

    public long getValue() {

        // TODO Auto-generated method stub
        return count;
    }

    public void increment() {

        lock.lock();
        try {
            count++;
        } finally {
            lock.unlock();
        }
    }

}
