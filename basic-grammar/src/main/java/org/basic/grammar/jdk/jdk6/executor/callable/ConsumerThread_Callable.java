package org.basic.grammar.jdk.jdk6.executor.callable;

import java.util.Queue;
import java.util.concurrent.Callable;

import org.basic.grammar.jdk.jdk6.executor.ResultType;
import org.basic.grammar.jdk.jdk6.executor.VacuumFlask;

public class ConsumerThread_Callable implements Callable<ResultType> {

    private Queue<VacuumFlask> vacuumFlasks;
    private boolean running = true;

    public ConsumerThread_Callable(Queue<VacuumFlask> apples) {
        this.vacuumFlasks = apples;

    }

    public ResultType call() throws Exception {
        System.out.println("call");
        ResultType rt = new ResultType("ConsumerThread_Callable-resultType");
        while (running) {
            Thread.sleep(5000);
            for (VacuumFlask apple : vacuumFlasks) {
                this.running = apple.getId() < 5;
                System.out.println(Thread.currentThread().getName() + ": Takeout the " + apple);
            }
        }
        System.out.println("ConsumerThread_Callable exit!");
        return rt;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
