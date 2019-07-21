package org.basic.net.c01_socket.mutipleclients;

import java.util.concurrent.Callable;

/**
 * 
 */
public class TimeConsumingTask implements Callable {

    public String call() throws Exception {

        System.out
                .println("It's a time-consuming task, you'd better retrieve your result in the furture");

        return "ok, here's the result: It takes me lots of time to produce this result";

    }

}
