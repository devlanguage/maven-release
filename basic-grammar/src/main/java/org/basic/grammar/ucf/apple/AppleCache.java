package org.basic.grammar.ucf.apple;


import java.util.ArrayList;
import java.util.List;

/**
 * 
 */
public class AppleCache {

    private static final AppleCache instance = new AppleCache();
    public final static List<String> VALID_COLORS = new ArrayList<String>();

    public final static String COLOR_GREEN = "Green";
    public final static String COLOR_YELLOW = "Yellow";
    public final static String COLOR_RED = "Red";

    // static {
    // Collections.synchronizedList(APPLE_rCACHE);
    // VALID_COLORS.add(COLOR_YELLOW);
    // VALID_COLORS.add(COLOR_RED);
    // }

    public final static AppleCache getInstance() {

        return instance;
    }

    private int MAX_CAPACITY = 1;
    private List<Apple> APPLE_REPOSITORY = new ArrayList<Apple>();

    public synchronized Apple remove(int i) {

        String currentThread = Thread.currentThread().getName();

        // ʹ��while������if���������Է�ֹ�̱߳���ٻ���
        while (APPLE_REPOSITORY.size() <= i) {
            System.out.println(currentThread + " Remove: before Wait");
            try {
                // System.out.println(currentThread + ", Remove but No Apple: wait");
                // wait(milliSecond)������wait(), �ɱ��⻽���̱߳��̵߳��߳���notify���߳�֮ǰ��������ʱ�����߳�//��Ȼ�л����˳�
                wait(ThreadTester.WAIT);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(currentThread + " Remove: after Wait");
        }

        // System.out.println(currentThread + ", Remove " + APPLE_REPOSITORY.get(i)
        // + " and Has Apple: Return to Runnable");
        Apple apple = APPLE_REPOSITORY.remove(i);
        notify();
        return apple;
    }

    public synchronized boolean put(Apple apple) {

        String currentThread = Thread.currentThread().getName();

        // ʹ��while������if���������Է�ֹ�̱߳���ٻ���
        while (APPLE_REPOSITORY.size() >= MAX_CAPACITY) {
            System.out.println(currentThread + " Put: before Wait");
            try {
                // System.out.println(currentThread + ", put but Full: wait");
                // wait(milliSecond)������wait(), �ɱ��⻽���̱߳��̵߳��߳���notify���߳�֮ǰ��������ʱ�����߳�//��Ȼ�л����˳�
                wait(ThreadTester.WAIT);
                System.out.println("");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(currentThread + " Put: after Wait");
        }

        // System.out.println(currentThread + ", put " + apple + "and has Vacancy: wait");
        boolean boolean1 = APPLE_REPOSITORY.add(apple);
        // /
        // ���ĳ��exception�׳�,
        // ��ǰ�߳�û�л���ȥnotify�����̡߳���ô���blocked���߳��ǽ���wait()����//������wait()����blocked״̬����ôblocked״̬���߳̽���Զû�л����˳�
        // /
        notify();
        return boolean1;
    }

    public synchronized int size() {

        return APPLE_REPOSITORY.size();
    }
}
