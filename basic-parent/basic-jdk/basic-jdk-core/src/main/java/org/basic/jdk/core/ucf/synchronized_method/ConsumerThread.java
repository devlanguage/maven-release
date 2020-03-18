package org.basic.jdk.core.ucf.synchronized_method;

/**
 * 
 */
public class ConsumerThread implements Runnable {

    private SharedData s;

    ConsumerThread(SharedData s) {

        this.s = s;
    }

    public void run() {

        char ch;

        do {
            try {
                Thread.sleep((int) Math.random() * 4000);
            } catch (InterruptedException e) {
            }
            // 消费
            ch = s.getCharacter();
            System.out.println("consume: " + ch);
        } while (ch != 'z');
    }

}
