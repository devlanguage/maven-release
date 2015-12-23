package org.basic.concurrent.mutiple_method.synchronizedd;

public class BusyFlag {

    protected int lockDeep = 0;
    protected Thread lockOwner = null;

    public synchronized void unlock() {

        if (getOwner() == Thread.currentThread()) {
            --lockDeep;
            if (lockDeep == 0) {
                lockOwner = null;
                notify();
            }
        }
    }

    public synchronized void lock() {

        while (tryGetLock() == false) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized Thread getOwner() {

        return lockOwner;
    }

    public synchronized boolean hasLocked() {

        return (tryGetLock() == false);
    }

    private synchronized boolean tryGetLock() {

        boolean gotLock = false;

        if (this.lockOwner == null) {
            this.lockOwner = Thread.currentThread();
            this.lockDeep = 1;
            gotLock = true;
        } else if (this.lockOwner == Thread.currentThread()) {
            ++this.lockDeep;
            gotLock = true;
        }

        return gotLock;
    }
}
