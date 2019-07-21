package org.basic.db.jdbc.concurrent;


/**
 * @author feiye
 */
public class CreateUserThread implements Runnable {

    private ProcedureTest userManager = null;

    public CreateUserThread(ProcedureTest t1) {

        this.userManager = t1;
    }

    public void run() {

        String user = null;
        int i = 0;
        while (i < 80) {
            user = "pool-1-thread-" + i;
            userManager.createUser(user);
            ++i;
        }
        System.out.println(Thread.currentThread().getName() + " exited");
    }
}
