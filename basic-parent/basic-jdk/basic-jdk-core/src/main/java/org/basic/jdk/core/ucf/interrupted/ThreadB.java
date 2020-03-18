package org.basic.jdk.core.ucf.interrupted;

/**
 * 
 */
public class ThreadB extends Thread {

    int count = 0;

    public void run() {

        System.out.println(getName() + " asdf...");

        while (!this.isInterrupted()) {
            System.out.println(getName() + " 23sdfasdf" + count++);

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println(getName() + " 收到中断命令!");
                int sleeptime = (int) (Math.random() * 10000);
                System.out.println(getName() + " 先睡�" + sleeptime + " 毫秒。觉醒后立即运行直到终止�");

                try {
                    Thread.sleep(sleeptime);
                } catch (InterruptedException m) {
                    e.printStackTrace();
                }

                System.out.println(getName() + " 已经觉醒，运行终�..");
                this.interrupt();
            }
        }

        System.out.println(getName() + " 终止�");
    }

}
