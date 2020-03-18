package org.basic.jdk.core.jdk.jdk7.e2_concurrent.fork_join_pool;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;

/**
 * <pre>
 * 最大的增强，充分利用多核特性，将大问题分解成各个子问题，由多个cpu可以同时解决多个子问题，最后合并结果，继承RecursiveTask，实现compute方法，然后调用fork计算，最后用join合并结果。
 * 
 *     class Fibonacci extends RecursiveTask<Integer> {
 *     final int n;
 *     Fibonacci(int n) {
 *     this.n = n;
 *     }
 *     private int compute(int small) {
 *     final int[] results = { 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89 };
 *     return results[small];
 *     }
 *     public Integer compute() {
 *     if (n <= 10) {
 *     return compute(n);
 *     }
 *     Fibonacci f1 = new Fibonacci(n - 1);
 *     Fibonacci f2 = new Fibonacci(n - 2);
 *     System.out.println("fork new thread for " + (n - 1));
 *     f1.fork();
 *     System.out.println("fork new thread for " + (n - 2));
 *     f2.fork();
 *     return f1.join() + f2.join();
 *     }
 *     }
 * </pre>
 * 
 * @author ygong
 *
 */
public class ForkJoinTest extends RecursiveAction {
    final long[] array;
    final int lo;
    final int hi;
    private int THRESHOLD = 0; // For demo only

    public ForkJoinTest(long[] array) {
        this.array = array;
        this.lo = 0; // 0
        this.hi = array.length - 1;
    }

    public ForkJoinTest(long[] array, int lo, int hi) {
        this.array = array;
        this.lo = lo;
        this.hi = hi;
    }

    protected void compute() {

        // 如果数据量比较少,就直接使用系统的排序方式
        if (hi - lo < THRESHOLD)
            sequentiallySort(array, lo, hi);
        else {
            int pivot = partition(array, lo, hi);
            // 把数组切分成两部分,然后在分别做排序
            this.invokeAll(new ForkJoinTest(array, pivot + 1, hi), new ForkJoinTest(array, lo, pivot - 1));

        }
    }

    private int partition(long[] array, int lo, int hi) {

        long x = array[hi];
        // -1
        int i = lo - 1;
        for (int j = lo; j < hi; j++) {
            if (array[j] <= x) {
                i++;
                swap(array, i, j);
            }
        }
        swap(array, i + 1, hi);
        return i + 1;
    }

    private void swap(long[] array, int i, int j) {
        if (i != j) {
            long temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }

    private static void sequentiallySort(long[] array, int lo, int hi) {
        Arrays.sort(array, lo, hi + 1);
    }

    public static void main(String agrs[]) throws InterruptedException {

        int length = 1024;
        Random r = new Random();
        long array[] = new long[length];

        for (int i = 0; i < length; i++) {
            array[i] = r.nextInt(19087);
        }

        long t1 = System.currentTimeMillis();

        sequentiallySort(array, 0, array.length - 1);

        System.out.println("自然排序消耗时间为：" + (System.currentTimeMillis() - t1));

        // 重新赋值

        for (int i = 0; i < length; i++) {
            array[i] = r.nextInt(19087);
        }

        long t2 = System.currentTimeMillis();

        ForkJoinTask sort = new ForkJoinTest(array);
        ForkJoinPool fjpool = new ForkJoinPool();
        fjpool.submit(sort);
        fjpool.shutdown();
        fjpool.awaitTermination(30, TimeUnit.SECONDS);
        System.out.print("fork/join并行消耗时间为：" + (System.currentTimeMillis() - t2));
    }
}