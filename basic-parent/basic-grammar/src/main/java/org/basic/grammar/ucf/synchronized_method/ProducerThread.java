package org.basic.grammar.ucf.synchronized_method;

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
