/**
 * Copyright reserved by Tellabs Communication Corp. LTD. The file
 * org.basic.threads.consumer.test1.ConsumerThread.java is created on 2008-9-16
 */
package org.basic.concurrent.synchronized_method;

/**
 * 
 */
public class ProducerThread implements Runnable {

    private SharedData s;

    ProducerThread(SharedData s) {

        this.s = s;
    }

    public void run() {

        for (char ch = 'a'; ch <= 'z'; ch++) {
            try {
                Thread.sleep((int) Math.random() * 4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 生产
            s.setCharacter(ch);
            System.out.println("produce: " + ch);
        }
    }

}
