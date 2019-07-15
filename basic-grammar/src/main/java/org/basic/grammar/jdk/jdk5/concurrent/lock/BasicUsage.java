package org.basic.grammar.jdk.jdk5.concurrent.lock;

import java.util.concurrent.locks.ReentrantLock;

public class BasicUsage {
    private final ReentrantLock lock = new ReentrantLock();
    public void m() {

        lock.lock(); // block until condition holds
        try {
            // ... method body
        } finally {
            lock.unlock();
        }
    }
}
