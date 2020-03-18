package org.basic.jdk.core.ucf.interrupted;

/**
 * 
 */
public class ThreadA extends Thread {

    private ThreadB thdother;

    ThreadA(ThreadB thdother) {

        this.thdother = thdother;
    }

    public void run() {

        System.out.println(getName() + ".");

        int sleeptime = 10000;
        System.out.println(getName() + "" + sleeptime + " 毫秒�");

        try {
            Thread.sleep(sleeptime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(getName() + " 觉醒，即将中�");
        thdother.interrupt();
    }

}
