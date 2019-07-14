package org.basic.grammar.ucf.concurrent_jdk15;

public class BooleanLock {

    private boolean value;

    public BooleanLock() {

        this(false);
    }

    public BooleanLock(boolean initialValue) {

        this.value = initialValue;
    }

    public synchronized boolean isTrue() {

        return this.value;
    }

    public synchronized boolean isFalse() {

        return !this.value;
    }

    public synchronized void setValue(boolean newValue) {

        if (this.value != newValue) {
            this.value = newValue;
            notifyAll();
        }
    }

    public synchronized boolean waitToSetTrue(long msTimeout) throws InterruptedException {

        boolean success = waitUntilFalse(msTimeout);
        if (success) {
            setValue(true);
        }

        return success;
    }

    public synchronized boolean waitToSetFalse(long msTimeout) throws InterruptedException {

        boolean success = waitUntilTrue(msTimeout);
        if (success) {
            setValue(false);
        }

        return success;
    }

    public synchronized boolean waitUntilTrue(long msTimeout) throws InterruptedException {

        return waitUntilStateIs(true, msTimeout);
    }

    public synchronized boolean waitUntilFalse(long msTimeout) throws InterruptedException {

        return waitUntilStateIs(false, msTimeout);
    }

    public synchronized boolean waitUntilStateIs(boolean state, long msTimeout) throws InterruptedException {

        if (msTimeout == 0L) {
            while (value != state) {
                wait();
            }

            return true;
        }

        long endTime = System.currentTimeMillis() + msTimeout;
        long msRemaining = msTimeout;

        while ((value != state) && (msRemaining > 0L)) {
            wait(msRemaining);
            msRemaining = endTime - System.currentTimeMillis();
        }

        return (value == state);
    }

    /**
     * @param b
     */
    public void setAlive(boolean b) {
        setValue(b);
    }

    /**
     * @return
     */
    public boolean isAlive() {
        return value;
    }
}
