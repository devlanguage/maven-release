package org.basic.jdk.core.jdk.jdk6.executor.future;

import java.util.concurrent.Callable;

import org.basic.jdk.core.jdk.jdk6.executor.ResultType;

public class PrimeCallable<T extends ResultType> implements Callable<T> {

    T resultType;

    public PrimeCallable(T t) {
        this.resultType = t;
    }

    public T call() throws Exception {
        resultType.setStatus("init");
        System.out.println("FutureTask start!");
        Thread.sleep(5000);
        System.out.println("FutureTask end!");
        resultType.setStatus("complete");
        return resultType;
    }

    // public int[] call() throws Exception {
    // int[] prime = new int[max + 1];
    //
    // List<Integer> list = new ArrayList<Integer>();
    //
    // for (int i = 2; i <= max; i++)
    // prime[i] = 1;
    //
    // for (int i = 2; i * i <= max; i++) { // ������ԸĽ�
    // if (prime[i] == 1) {
    // for (int j = 2 * i; j <= max; j++) {
    // if (j % i == 0)
    // prime[j] = 0;
    // }
    // }
    // }
    //
    // for (int i = 2; i < max; i++) {
    // if (prime[i] == 1) {
    // list.add(i);
    // }
    // }
    //
    // int[] p = new int[list.size()];
    // for (int i = 0; i < p.length; i++) {
    // p[i] = list.get(i).intValue();
    // }
    //
    // return p;
    // };
};
