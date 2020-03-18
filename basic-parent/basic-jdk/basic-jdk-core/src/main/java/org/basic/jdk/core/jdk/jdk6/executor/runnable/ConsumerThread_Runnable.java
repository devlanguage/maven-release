package org.basic.jdk.core.jdk.jdk6.executor.runnable;

import java.util.Queue;
import java.util.concurrent.Callable;

import org.basic.jdk.core.jdk.jdk6.executor.ResultType;
import org.basic.jdk.core.jdk.jdk6.executor.VacuumFlask;

public class ConsumerThread_Runnable<T extends ResultType> implements Callable<T> {

    private Queue<VacuumFlask> vacuumFlasks;
    private boolean running = true;
    T rt;

    public ConsumerThread_Runnable(Queue<VacuumFlask> vacuumFlasks, T t) {
        this.vacuumFlasks = vacuumFlasks;

    }

    public T call() throws Exception {
        System.out.println("call");
        while (running) {
            Thread.sleep(5000);
            for (VacuumFlask apple : vacuumFlasks) {
                this.running = apple.getId() < 10;
                System.out.println(Thread.currentThread().getName() + ": Takeout the " + apple);
            }
        }
        rt.setContent("ConsumerThread_Callable-resultType");
        return rt;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
