package org.basic.jdk.core.jdk.jdk5.concurrent.betterPerformance;

public class SynchronizeBenchmark  implements CounterIntf {
    private long count = 0;

    public long getValue() {
        return count;
    }

    public synchronized void increment() {
        count++;
    }
}
