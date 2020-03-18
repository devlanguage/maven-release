package org.basic.jdk.core.jdk.jdk7.e2_concurrent.fork_join_pool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;

public class ForkJoinCaculateTest extends RecursiveTask<Integer> {

  private static final int THRESHOLD = 10;
  private int start;
  private int end;

  public ForkJoinCaculateTest(int start, int end) {
    this.start = start;
    this.end = end;
  }

  @Override
  protected Integer compute() {
    int sum = 0;
    if ((start - end) < THRESHOLD) {
      for (int i = start; i < end; i++) {
        sum += i;
      }
    } else {
      int middle = (start + end) / 2;
      ForkJoinCaculateTest left = new ForkJoinCaculateTest(start, middle);
      ForkJoinCaculateTest right = new ForkJoinCaculateTest(middle + 1, end);
      left.fork();
      right.fork();

      sum = left.join() + right.join();
    }
    return sum;
  }

  static class Fibonacci extends RecursiveTask<Integer> {
    final int n;

    Fibonacci(int n) {
      this.n = n;
    }

    public Integer compute() {
      System.out.println(Thread.currentThread().getName() + ", n=" + n);
      if (n <= 1)
        return n;
      Fibonacci f1 = new Fibonacci(n - 1);
      f1.fork();
      Fibonacci f2 = new Fibonacci(n - 2);
      f2.fork();
      Fibonacci f3 = new Fibonacci(n - 3);
      f3.fork();
      Fibonacci f4 = new Fibonacci(n - 4);
      f4.fork();
      return f4.join() + f3.join() + f2.join() + f1.join();
    }
  }

  public static void main(String[] args) {
    ForkJoinPool pool = new ForkJoinPool();
    try {
      ForkJoinCaculateTest f1 = new ForkJoinCaculateTest(0, 100);
       pool.submit(new ForkJoinCaculateTest(0, 100));
//      Fibonacci f1 = new Fibonacci(10);
      pool.submit(f1);
      System.out.println("result="+f1.get());
      pool.shutdown();
      pool.awaitTermination(100, TimeUnit.SECONDS);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}