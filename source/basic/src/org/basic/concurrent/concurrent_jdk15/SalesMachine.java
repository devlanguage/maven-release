package org.basic.concurrent.concurrent_jdk15;

import java.util.AbstractQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SalesMachine implements Callable<ResultType> {

    private AbstractQueue goods;
    private boolean running = true;

    public SalesMachine(ConcurrentLinkedQueue concurrentLinkedQueue) {

        goods = concurrentLinkedQueue;
    }

    public void stop() {

        running = false;
    }

    public ResultType call() throws Exception {

        int i = 0;
        while (running) {
            Thread.sleep(1000);
            String product = "Apple_" + i;
            System.out.println(Thread.currentThread().getName() + " Takeout: " + product);
            goods.add(product);
        }
        return null;
    }
}
