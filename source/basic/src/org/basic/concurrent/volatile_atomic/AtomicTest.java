package org.basic.concurrent.volatile_atomic;

import java.util.concurrent.atomic.AtomicBoolean;

public class AtomicTest {

    private AtomicBoolean done = new AtomicBoolean(false);

    public void run() {

        while (done.get()) {
            // your code
        }
    }

    public void setDone(boolean done) {

        this.done.set(done);
    }

    /**
     * 代码还是以前的，只不过现在没有了synchronized关键字了，同时原来的volatile类型也没有用在这里了。换而改用Atomic类型了。这里我们使用了AtomicBoolean它提供了get() & set()
     * 提供了原子化(Atomic)的操作，同时也确保值来自与主存储器。
     * 简而言之：Atomic通过封装了Integer,Long,Boolean,Reference对象，使它们所做的一些操作变成了原子化操作，这样我们在程序里就不需要就一个变量的自增
     * ，或者读取一段代码都必须做同步块处理，做到了极简同步的目的
     * 。当然大部分情况，这种操作还是必须和同步块进行合作的，想想如果同时2个Atomic变量都要从进行改变，那么不用sychronized的话，没有办法保证在对1个Atomic变量操作完不会有其他Thread打断它。 Atomic
     * 目前只提供了4种类型的支持
     * ，当然可以通过自己封装其他的类，利用AtomicReference对象使封装的类的引用再改变时每次都重新生成一个新的引用，就是把封装的引用进行替换，而不是传统上的修改了。这也存在了性能问题。利用Atomic大部分场合
     * ，还是适合对单一对象的操作上，当然我们利用它可以实现更复杂的同步功能但是带来的是程序上的复杂，所以我认为在实际使用中并不会过多的依赖。这里就只对它做一个大概的介绍。
     * 附一些AtomicInteger的函数：getAndSet(int i)设置新值是i返回旧值的原子化操作。 incrementAndGet() 自增的原子操作。 addAndGet() 前置运算符的操作等。
     */

    /**
     * 相信绝大多数使用JAVA的人都没试出volatile变量的区别。献给那些一直想知道volatile是如何工作的而又试验不出区别的人。 成员变量boolValue使用volatile和不使用volatile会有明显区别的。
     * 本程序需要多试几次，就能知道两者之间的区别的。
     * 
     * @param args
     */
    // volatile boolean boolValue=true;// 加上volatile 修饰的是时候，程序会很快退出，因为volatile 保证各个线程工作内存的变量值和主存一致。所以boolValue ==
    // !boolValue就成为了可能。
    AtomicBoolean boolValue = new AtomicBoolean(true);

    public static void main(String[] args) {

        final VolatileObjectTest volObj = new VolatileObjectTest();
        Thread t1 = new Thread("waitToExit") {

            public void run() {

                System.out.println("t1 start");
                for (;;) {
                    volObj.waitToExit();
                }
            }
        };
        t1.start();
        Thread t2 = new Thread("swap") {

            public void run() {

                System.out.println("t2 start");
                for (;;) {
                    volObj.swap();
                }
            }
        };
        t2.start();
    }

    public void waitToExit() {
        System.out.println(Thread.currentThread() + ", booleanValue=" + boolValue);
        if (boolValue.get() != boolValue.get()) {
            System.out.println(Thread.currentThread() + ", booleanValue=" + boolValue);
            System.exit(0);// 非原子操作，理论上应该很快会被打断。实际不是，因为此时的boolValue在线程自己内部的工作内存的拷贝，因为它不会强制和主存区域同步，线程2修改了boolValue很少有机会传递到线程一的工作内存中。所以照成了假的“原子现象”。
        }
    }

    public void swap() {// 不断反复修改boolValue，以期打断线程1.
        System.out.println(Thread.currentThread() + ", booleanValue=" + boolValue);
        boolValue.set(!boolValue.get());
    }

}
