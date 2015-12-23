package org.basic.jdk.jdk6.executor.callable;

import java.util.Queue;
import java.util.concurrent.Callable;

import org.basic.jdk.jdk6.executor.ResultType;
import org.basic.jdk.jdk6.executor.VacuumFlask;

public class ProducerThread_Callable implements Callable<ResultType> {

    private Queue<VacuumFlask> vacuumFlasks;
    private boolean running = true;

    public ProducerThread_Callable(Queue<VacuumFlask> vacuumFlasks) {
        this.vacuumFlasks = vacuumFlasks;
    }

    public ResultType call() throws Exception {
        System.out.println("call");
        int i = 0;
        while (running && i++ < 5) {
            Thread.sleep(1000);
            VacuumFlask vacuumFlask = new VacuumFlask(i, "Vacuum_Flask_" + i);
            System.out.println(Thread.currentThread().getName() + ": Produce the " + vacuumFlask);
            vacuumFlasks.add(vacuumFlask);
        }
        System.out.println("ProducerThread_Callable exit!");
        return new ResultType("ProducerThread_Callable-resultType");
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}