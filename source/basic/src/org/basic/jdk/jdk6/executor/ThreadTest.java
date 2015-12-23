package org.basic.jdk.jdk6.executor;

import java.lang.reflect.Method;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.basic.jdk.jdk6.executor.callable.ConsumerThread_Callable;
import org.basic.jdk.jdk6.executor.callable.ProducerThread_Callable;
import org.basic.jdk.jdk6.executor.future.PrimeCallable;
import org.basic.jdk.jdk6.executor.runnable.ConsumerThread_Runnable;
import org.basic.jdk.jdk6.executor.runnable.ProducerThread_Runnable;

public class ThreadTest {

    public final static void sleepAndStop(long milliSeconds, Object obj) {
        try {
            Thread.sleep(milliSeconds);
            Method setRunning = obj.getClass().getMethod("setRunning", new Class[] { boolean.class });
            setRunning.invoke(obj, new Object[] { false });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            // testCallable();
            // testRunnable();
            testFuture();
            // testExecutors();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @throws Exception
     */
    private static void testFuture() throws Exception {
        ResultType rt = new ResultType();
        Callable<ResultType> primeCallable = new PrimeCallable<ResultType>(rt);
        FutureTask<ResultType> futureTask = new FutureTask<ResultType>(primeCallable);
        Thread t = new Thread(futureTask);
        t.start();

        // Check if futureTask managed thread is done
        while (true) {
            if (futureTask.isDone()) {
                ResultType rt1 = futureTask.get();
                System.out.println("Delegated Thread is done: " + rt);
                break;
            }else{
             Thread.sleep(1000);
             System.out.println("sleep..");;
            }
        }
    }

    private static void testCallable() throws Exception {
        Queue<VacuumFlask> vacuumFlask01 = new ConcurrentLinkedQueue<VacuumFlask>();
        Callable<ResultType> producer01 = new ProducerThread_Callable(vacuumFlask01);
        Callable<ResultType> consumer01 = new ConsumerThread_Callable(vacuumFlask01);

        ExecutorService executor = Executors.newCachedThreadPool();
        Future<ResultType> futureProducer = executor.submit(producer01);
        Future<ResultType> futureConsumer = executor.submit(consumer01);
        // Block the current Thread and wait for futureConsumer returning
        System.out.println(futureConsumer.get());
        // Block the current Thread and wait for futureProducer returning
        System.out.println(futureProducer.get());

    }

    private final static void testExecutors() {
        ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor();
        long initialDelay = 5, delay = 5, period = 2;
        Callable<ResultType> t = new Callable<ResultType>() {
            public ResultType call() {
                ResultType rt = new ResultType("scheduledExecutor");
                System.out.println("ScheduledExecutor callable!");
                return rt;
            }
        };
        scheduledExecutor.schedule(t, initialDelay, TimeUnit.SECONDS);
        final long start = System.currentTimeMillis();
        Runnable r = new Runnable() {

            public void run() {
                System.out.println("ScheduledExector runnable:" + (System.currentTimeMillis() - start));
            }
        };
        scheduledExecutor.schedule(r, initialDelay, TimeUnit.SECONDS);
        // scheduledExecutor.scheduleAtFixedRate(r, initialDelay, period, TimeUnit.SECONDS);
        scheduledExecutor.scheduleWithFixedDelay(r, initialDelay, period, TimeUnit.SECONDS);

    }

    private static void testRunnable() throws Exception {
        ResultType rt = new ResultType();
        Queue<VacuumFlask> vacuumFlask02 = new ConcurrentLinkedQueue<VacuumFlask>();
        Callable<ResultType> consumer02 = new ConsumerThread_Runnable<ResultType>(vacuumFlask02, rt);
        Callable<ResultType> producer02 = new ProducerThread_Runnable<ResultType>(vacuumFlask02, rt);

        ExecutorService executor = Executors.newCachedThreadPool();
        String result = "result";
        Future<ResultType> futureConsumer = executor.submit(consumer02);
        Future<ResultType> futureProducer = executor.submit(producer02);

        System.out.println(futureConsumer.get());
        System.out.println(futureProducer.get());
    }

}
