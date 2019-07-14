package org.basic.grammar.ucf.concurrent_jdk15;

import java.util.AbstractQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Customer implements Callable<ResultType> {

    private AbstractQueue goods;
    private boolean running = true;

    public Customer(ConcurrentLinkedQueue concurrentLinkedQueue) {

        goods = concurrentLinkedQueue;
    }

    public void stop() {

        running = false;
    }

    public ResultType call() throws Exception {

        int i = 0;
        while (running) {
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName() + " buy: " + goods.poll());

        }
        return new ResultType();
    }

}
