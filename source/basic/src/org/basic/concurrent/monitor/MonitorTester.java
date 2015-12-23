package org.basic.concurrent.monitor;

class MonitorClass extends Thread {
    public MonitorClass(String name) {
        super(name);
    }
    public synchronized static void staticMethod(String caller) {
        for (int i = 5; i < 10; i++) {
            System.out.println(Thread.currentThread().getName() + "-" + caller + " : " + i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("Interrupted");
            }
        }
    }
    public synchronized void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName() + " : " + i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("Interrupted");
            }
        }
    }
}
public class MonitorTester {
    @SuppressWarnings("static-access")
    public static void main(String[] args) {
        MonitorClass t1 = new MonitorClass("t1");
        MonitorClass t2 = new MonitorClass("t2");
        // �ڴ��루1���У���Ȼ��ͨ������t1������staticMethod()�����ģ�������staticMethod()�Ǿ�̬�ģ�
        // ���Ե�����ʱ���þ����κζ������������߳�Ϊmain�̡߳�
        //
        // ���ڵ���run()����ȡ�ߵ��Ƕ�������������staticMethod()����ȡ�ߵ���class����
        // ����ͬһ���߳�t1���������֪ʵ�����ǲ�ͬ�̣߳�����run()�����һ�û���run()����ʱ�������ܵ���staticMethod()������
        // ��staticMethod()����ֻ�ܱ�һ���̵߳��ã�����루1���ʹ��루2������ʹ��������ͬ�Ķ���Ҳ����ͬʱ����staticMethod()��
        t1.start();
        t1.staticMethod("t1"); // ��1��
        t2.staticMethod("t2"); // ��2��
    }
}
