package org.basic.jdk.core.ucf.apple;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

/**
 * 
 */
// public class ThreadReader extends Thread {
public class ThreadReader implements Runnable {

    private boolean running;

    public ThreadReader() {

        this.setRunning(true);
    }

    public void run() {

        AppleCache repository = AppleCache.getInstance();
        while (this.isRunning() && !Thread.currentThread().isInterrupted()) {
            int size = repository.size();
            for (int i = 0; i < size; ++i) {
                System.out.println(Thread.currentThread().getName() + " Remove: "
                        + repository.remove(i));
            }

            try {
                Thread.sleep(ThreadTester.PAUSE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @return get method for the field running
     */
    public boolean isRunning() {

        return this.running;
    }

    /**
     * @param running
     *            the running to set
     */
    public void setRunning(boolean running) {

        this.running = running;
    }
}
