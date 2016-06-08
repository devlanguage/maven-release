package org.basic.jdk.jdk7.e2_concurrent.transfer_queue;

import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.Test;

/**
 * <pre>
 *
 * </pre>
 */
public class TransferQueueTest {

    @Test
    public void TransferQueue() throws InterruptedException {
        ThreadFactory tf = java.util.concurrent.Executors.defaultThreadFactory();

        java.util.concurrent.TransferQueue<String> tqueue = new LinkedTransferQueue<String>();
        Runnable r = () -> {
            try {
                while (true)
                {
                    Thread.sleep(1000);
                    System.out.println("Remvoing_"+tqueue.take());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        
        Runnable w = () -> {
            try {
                while (true){
                    Thread.sleep(100);
                    String newElement ="adding_Testx_"+ThreadLocalRandom.current().nextLong();
                    System.out.println(newElement+", "+tqueue.size()+", waiting="+tqueue.getWaitingConsumerCount());
                    tqueue.offer(newElement);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        
        tf.newThread(r).start();
        tf.newThread(r).start();
        tf.newThread(r).start();
        tf.newThread(r).start();
        
        tf.newThread(w).start();

        tqueue.add("abcd1");
        tqueue.add("abc");
        tqueue.add("abcd2");
        tqueue.add("abc");
        tqueue.transfer("abc");

        System.out.println(tqueue.remainingCapacity());
        System.out.println("Main Thread Restore:" + tqueue.remove("abc"));
        tqueue.stream().forEach((x) -> {
            System.out.println(x);
        });

    }

    public static void main(String[] args) throws InterruptedException {
        new TransferQueueTest().TransferQueue();
        

    }
}
