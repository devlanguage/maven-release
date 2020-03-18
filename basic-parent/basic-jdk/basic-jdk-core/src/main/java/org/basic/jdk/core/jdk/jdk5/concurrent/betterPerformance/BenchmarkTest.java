package org.basic.jdk.core.jdk.jdk5.concurrent.betterPerformance;

import java.util.concurrent.CyclicBarrier;

/**
 * <pre>
 * �ֱ����һ�£�
 * 
 *  ���������߳����޶�Ϊ500�����Ϊ��
 *  ��ƽReentrantLock:      210 ����
 *  �ǹ�ƽReentrantLock :   39  ����
 *  �ڲ���:                          39 ����
 * 
 *  ���������߳����޶�Ϊ1000�����Ϊ��
 *  ��ƽReentrantLock:      640 ����
 *  �ǹ�ƽReentrantLock :   81 ����
 *  �ڲ���:                           60 ����
 * 
 *  �߳������䣬test�����е�ѭ�����ӵ�1000�Σ����Ϊ��
 *  ��ƽReentrantLock:      16715 ����
 *  �ǹ�ƽReentrantLock :   168 ����
 *  �ڲ���:                           639  ����
 * 
 *  ���������߳������ӵ�2000,���Ϊ��
 *  ��ƽReentrantLock:      1100 ����
 *  �ǹ�ƽReentrantLock:   125 ����
 *  �ڲ���:                           130 ����
 * 
 *  ���������߳������ӵ�3000,���Ϊ��
 *  ��ƽReentrantLock:      2461 ����
 *  �ǹ�ƽReentrantLock:   254 ����
 *  �ڲ���:                           307 ����
 * 
 *  ����5000���̣߳�������£�
 *  ��ƽReentrantLock:      6154  ����
 *  �ǹ�ƽReentrantLock:   623   ����
 *  �ڲ���:                           720 ����
 * 
 *  �ǹ�ƽReentrantLock���ڲ����Ĳ�࣬��jdk6��Ӧ����С�ˣ���˵jdk6���ڲ������ƽ����˵�����
 * 
 * </pre>
 * 
 */
public class BenchmarkTest {

    private CounterIntf counter;

    private CyclicBarrier barrier;

    private int threadNum;

    public BenchmarkTest(CounterIntf counter, int threadNum) {

        this.counter = counter;
        barrier = new CyclicBarrier(threadNum + 1); // �ؿ�����=�߳���+1
        this.threadNum = threadNum;
    }

    public static void main(String args[]) {

        new BenchmarkTest(new SynchronizeBenchmark(), 5000).test();
        // new BenchmarkTest(new ReentrantLockBeanchmark(), 5000).test();
        // new BenchmarkTest(new ReentrantLockBeanchmark(), 5000).test();
    }

    public void test() {

        try {
            for (int i = 0; i < threadNum; i++) {
                new TestThread(counter).start();
            }
            long start = System.currentTimeMillis();
            barrier.await(); // �ȴ����������̴߳���
            barrier.await(); // �ȴ���������������
            long end = System.currentTimeMillis();
            System.out.println("count value:" + counter.getValue());
            System.out.println("����ʱ��:" + (end - start) + "����");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    class TestThread extends Thread {

        private CounterIntf counter;

        public TestThread(final CounterIntf counter) {

            this.counter = counter;
        }

        public void run() {

            try {
                barrier.await();
                for (int i = 0; i < 100; i++)
                    counter.increment();
                barrier.await();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}