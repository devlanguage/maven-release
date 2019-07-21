package org.third.message.imq.util.helper;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Lock used by Nbi Messaging to notify/wait sender or receiver
 */
public class MessageLock {
    private Lock lock;
    private Condition lockCondition;

    public MessageLock() {
        lock = new ReentrantLock();
        lockCondition = lock.newCondition();
    }

    public void lock() {
        lock.lock();
    }

    public void unlock() {
        lock.unlock();
    }

    public void await(long timeSecs) throws InterruptedException {
        lockCondition.await(timeSecs, TimeUnit.SECONDS);
    }

    public void signal() {
        lockCondition.signal();
    }
    
    public void locksignal() {
        lock();
        lockCondition.signal();
        unlock();
    }
}
