package org.basic.concurrent.countdownlatch;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class Solver {     
    //     
    private final String[][] data;     
    private final CyclicBarrier barrier;     
    private final CountDownLatch latch;     
    
    public Solver(String[][] data) {     
        this.data = data;     
        this.barrier = new CyclicBarrier(data.length, new BarrierAction());     
        this.latch = new CountDownLatch(data.length);     
    }     
         
    public void start() {     
        //     
        for (int i = 0; i < data.length; ++i)  {     
            new Thread(new Worker("worker" + i, this.data[i])).start();     
        }     
             
        //     
        try {     
            latch.await();     
        } catch (InterruptedException e) {     
            e.printStackTrace();     
        }     
    }     
         
    public static void main(String args[]) {     
        String[][] data = new String[][]{{"a1", "a2", "a3"}, {"b1", "b2", "b3"}, {"c1", "c2", "c3"}};     
        Solver solver = new Solver(data);     
        solver.start();     
    }     
         
    private class BarrierAction implements Runnable {     
        public void run() {     
            System.out.println(Thread.currentThread().getName() + " is processing barrier action");     
        }     
    }     
         
    private class Worker implements Runnable {     
        //     
        private String name;     
        private String[] row;     
             
        Worker(String name, String[] row) {     
            this.name = name;     
            this.row = row;      
       }     
             
        public void run() {     
            for(int i = 0; i < row.length; i++) {     
                System.out.println(name + " is processing row[" + i +"]" + row[i]);     
                     
                try {     
                    barrier.await();      
                } catch (InterruptedException ex) {      
                    break;      
                } catch (BrokenBarrierException ex) {      
                    break;      
                }     
            }     
                 
            //     
            latch.countDown();     
        }     
    }     
}     