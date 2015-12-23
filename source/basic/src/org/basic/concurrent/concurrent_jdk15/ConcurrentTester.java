package org.basic.concurrent.concurrent_jdk15;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ConcurrentTester {

    /**
     * @param args
     */
    public static void main(String[] args) {

        java.util.concurrent.ConcurrentLinkedQueue concurrentLinkedQueue = new ConcurrentLinkedQueue();
        ExecutorService executor = Executors.newCachedThreadPool();
        // Callable<ResultType> customer = new Customer(concurrentLinkedQueue);
        // Callable<ResultType> sales = new SalesMachine(concurrentLinkedQueue);
        // executor.submit(customer);
        // executor.submit(sales);

        // sales.stop();
        // customer.stop();

        Callable<ResultType> searcherTask = new Callable<ResultType>() {

            public ResultType call() throws Exception {

                ResultType rt = new ResultType();
                Thread.sleep(3000); // /Search the text
                rt.setContent("Searched what you want");
                return rt;
            }
        };
        Future<ResultType> future = executor.submit(searcherTask);
        try {
            // Block the current Thread until searcherTask returned
            ResultType futureRt = future.get();
            System.out.println(futureRt);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        executor.shutdown();
    }
}
