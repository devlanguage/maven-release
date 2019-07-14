/**
 * Copyright reserved by Tellabs Communication Corp. LTD. The file
 * org.basic.threads.WriterThread.java is created on 2008-3-12
 */
package org.basic.grammar.ucf.apple;

/**
 * 
 */
// public class ThreadWriter extends Thread {
public class ThreadWriter implements Runnable {

    private boolean running;

    public ThreadWriter() {

        this.setRunning(true);
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

    public void run() {

        ThreadIDGenerator idGenerator = ThreadIDGenerator.getInstance();
        AppleCache repository = AppleCache.getInstance();
        int i = 0;
        String id;
        while (this.isRunning() && !Thread.currentThread().isInterrupted()) {
            id = idGenerator.generateId() + "_" + (++i);
            Apple apple01 = new Apple(id, "tianshui-" + id, AppleCache.COLOR_GREEN,
                    AppleCache.VALID_COLORS.contains(AppleCache.COLOR_GREEN));
            repository.put(apple01);
            System.out.println(Thread.currentThread().getName() + " Put: " + apple01);

            try {
                Thread.sleep(ThreadTester.PAUSE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
