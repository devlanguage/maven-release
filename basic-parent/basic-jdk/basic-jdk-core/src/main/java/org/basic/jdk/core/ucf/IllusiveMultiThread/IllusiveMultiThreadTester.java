package org.basic.jdk.core.ucf.IllusiveMultiThread;

public class IllusiveMultiThreadTester {

    int i = 0, j = 0;

    public void go(boolean flag) {

        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("Interrupted");
            }
            if (flag) {
                System.out.println("i=" + (++i));
            } else {
                System.out.println("j=" + (++j));
            }
        }
    }

    public static void main(String[] args) {

        // �����һֱ��ӡ��i��ֵ�����ǵ���ͼ�ǵ���whileѭ���е���sleep()ʱ����һ���߳̾ͽ��𶯣���ӡ��j��ֵ��
        // �����ȴ��������������Ϊ�������ֻ��һ��main�߳�,ֻ��t1.go(true)ִ����ϣ�t2.go(false)���л���
        // ִ�С����ǣ�t1.go(true)��Զû�л����˳�������t2.go(false)��Զû�л���ִ��

        IllusiveMultiThreadTester t1 = new IllusiveMultiThreadTester();
        IllusiveMultiThreadTester t2 = new IllusiveMultiThreadTester();
        t1.go(true);
        t2.go(false);
    }
}
