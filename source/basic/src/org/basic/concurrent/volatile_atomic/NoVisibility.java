package org.basic.concurrent.volatile_atomic;

public class NoVisibility {

    private static boolean ready;
    private static int number;

    private static class ReaderThread extends Thread {

        public void run() {

            while (!ready)
                Thread.yield();
            System.out.println(number);
        }
    }

    public static void main(String[] args) {

        new ReaderThread().start();
        number = 42;
        ready = true;
    }

}/*
  * NoVisibility可能会一直循环，因为对于读线程来说，ready的值可能永远不可见。甚至更奇怪的现象是，NoVisibility可能会打印0，因为早在对number赋值之前，主线程就已经写入ready并使之对读线程可见，这是一种
  * “重排序”现象。
  * 
  * 注意：在没有同步的情况下，编译器，处理器，远行时安排操作的执行顺序可能完全出人意料。在没有进行适当同步的多线程程序中，尝试推断那些“必然”发生在内存中的动作时，你总是会判断错误。
  */