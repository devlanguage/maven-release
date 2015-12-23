package org.basic.concurrent.locked.single_object_lock;

/**
 * 设置了一个�?知变量，每次在生产�?生产和消费�?消费之前，都测试通知变量，检查是否可以生产或消费。最�?��设置通知变量为true，表示还未生产，
 * 在这时�?，消费�?�?��消费，于时修改了通知变量，调用notify()发出通知。这时由于生产�?得到通知，生产出第一个产品，修改通知变量，向消费者发出�?知�?
 * 时如果生产�?想要继续生产，但因为�?��到�?知变量为false，得知消费�?还没有生产，�?��调用wait()进入等待状�?�? * 
 * 因此，最后的结果，是生产者每生产�?��，就通知消费者消费一个；消费者每消费�?��，就通知生产者生产一个，�?��不会出现未生产就消费或生产过剩的情况�? * 
 */

public class Tester {

    public static void main(String[] args) {

        final ResourceB1 rs = new ResourceB1();

        new Thread() {

            public void run() {

                rs.testMethod1();
            }
        }.start();

        new Thread() {

            public void run() {

                rs.testMethod2();
            }
        }.start();

        rs.testMethod3();
    }
}
