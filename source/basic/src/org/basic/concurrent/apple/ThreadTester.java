/**
 * Copyright reserved by Tellabs Communication Corp. LTD. The file
 * org.basic.threads.ThreadTester.java is created on 2008-3-12
 */
package org.basic.concurrent.apple;

/**
 * 
 */
public class ThreadTester {

    public final static int PAUSE = 1000;
    public final static long WAIT = 100;

    public static void main(String[] args) {

        ThreadTester tester = new ThreadTester();
        tester.startThreadTest();
    }

    void startThreadTest() {

        ThreadReader reader01 = new ThreadReader();
        ThreadWriter writer01 = new ThreadWriter();
        ThreadWriter writer02 = new ThreadWriter();

        new Thread(reader01, "Reader01").start();
        new Thread(writer01, "Writer01").start();
        new Thread(writer02, "Writer02").start();
        // reader.start();
        // writer.start();
        

        System.out.println("main: Starting:....");
         try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("main: awake....");
        reader01.setRunning(false);
        writer01.setRunning(false);
        writer02.setRunning(false);
        System.out.println("main: end....");
    }
}
