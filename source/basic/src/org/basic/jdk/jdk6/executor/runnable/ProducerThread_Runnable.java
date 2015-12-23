package org.basic.jdk.jdk6.executor.runnable;

import java.util.Queue;
import java.util.concurrent.Callable;

import org.basic.jdk.jdk6.executor.ResultType;
import org.basic.jdk.jdk6.executor.VacuumFlask;

public class ProducerThread_Runnable<T extends ResultType> implements Callable<T> {

    private Queue<VacuumFlask> vacuumFlasks;
    T rt;
    private boolean running = true;
    

    public ProducerThread_Runnable(Queue<VacuumFlask> apples, T rt) {
        this.vacuumFlasks = apples;
    }

    public T call() throws Exception {
        System.out.println("call");
        int i = 0;
        while (running && i++ < 10) {
            Thread.sleep(1000);
            VacuumFlask vacuumFlask = new VacuumFlask(i, "Vacuum_Flask_" + i);
            System.out.println(Thread.currentThread().getName() + ": Produce the " + vacuumFlask);
            vacuumFlasks.add(vacuumFlask);
        }
        rt.setContent("ProducerThread_Runnable-resultType");
        return rt;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

}