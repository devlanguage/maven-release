package org.basic.concurrent.unsynchronized;

/**
 * 在以上的程序中，模拟了生产�?和消费�?的关系，生产者在�?��循环中不断生产了从Ａ－Ｚ的共享数据，而消费�?则不断地消费生产者生产的Ａ－Ｚ的共享数据�? * 我们�?��已经说过，在这一对关系中，必须先有生产�?生产，才能有消费者消费�?但如果运行我们上面这个程序，结果却出现了在生产�?没有生产之前�? * 消费都就已经�?��消费了或者是生产者生产了却未能被消费者消费这种反常现�? * 
 */

public class Tester {

    public static void main(String argv[]) {

        SharedData s = new SharedData();

        new Thread(new ConsumerThread(s)).start();
        new Thread(new ProducerThread(s)).start();
    }
}
