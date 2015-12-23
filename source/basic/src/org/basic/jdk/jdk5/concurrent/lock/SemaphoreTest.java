package org.basic.jdk.jdk5.concurrent.lock;

import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemaphoreTest {
    static int NUM_OF_CONSUMER = 50;
    static int NUM_OF_PRODUCER = 50;

    static ExecutorService executor = Executors.newFixedThreadPool(100);

    // CompletionService<Long> completionServ = new
    // ExecutorCompletionService<Long>(executor);

    public static void main(String[] args) {

        int permits = 10;
        final java.util.concurrent.Semaphore semaphore = new Semaphore(permits);
//        for (int i = 0; i < NUM_OF_CONSUMER; ++i) {
//            Runnable r1 = new Runnable() {
//                public void run() {
//                    while (true) {
//                        // /Start work
//                        System.out.println("Deconstruct the house");
//                    }
//                }
//            };
//            executor.submit(r1);
//        }

        for (int i = 0; i < NUM_OF_PRODUCER; ++i) {
            Runnable r1 = new Runnable() {
                public void run() {
                    while (true) {
                        try {
                            semaphore.acquire();
                            
                            String semphoreInfo = semaphore.availablePermits()+","+semaphore.hasQueuedThreads()+semaphore.isFair();
                            String builder = Thread.currentThread().getName();
                            // /Start work
                            System.out.println(builder+" Start to build one new House, semaphore:"+semphoreInfo);                            
                            Thread.sleep(2000);
                            System.out.println(builder+" End to build one new House, semaphore:"+semphoreInfo);
                            
                            semaphore.release();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            executor.submit(r1);
        }
        executor.shutdown();

    }
}
