package org.basic.concurrent.interrupted;

/**
 * 在很多时候，我们�?��在一个线程中调控另一个线程，这时我们就要用到线程的中断�?
 * 用最�?��的话也许可以说它就相当于播放机中的暂停一样，当第�?��按下暂停时，播放器停止播放，再一次按下暂停时，继续从刚才暂停的地方开始重新播放�?
 * 
 * 而在java中，这个暂停按钮就是interrupt()方法�? * 
 * <pre>
 *  
 * 在第�?��调用interrupt()方法时，线程中断�?
 * 当再�?��调用interrupt()方法时，线程继续运行直到终止�? * </pre>
 * 
 * 
 */

public class Tester {

    public static void main(String argv[]) {

        ThreadB threadB = new ThreadB();
        threadB.setName("Thread-BBBB");

        ThreadA threadA = new ThreadA(threadB);
        threadA.setName("Thread-AAAAAA");

        threadB.start();
        threadA.start();
    }
}
