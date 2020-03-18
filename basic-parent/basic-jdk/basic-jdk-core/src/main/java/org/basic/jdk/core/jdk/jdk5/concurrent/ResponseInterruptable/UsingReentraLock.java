package org.basic.jdk.core.jdk.jdk5.concurrent.ResponseInterruptable;

import java.util.concurrent.locks.ReentrantLock;

public class UsingReentraLock {

    static class BufferInterruptibly {

        private ReentrantLock lock = new ReentrantLock();

        public void write() {

            lock.lock();
            try {
                long startTime = System.currentTimeMillis();
                System.out.println("��ʼ�����buffд�����ݡ�");
                for (;;)// ģ��Ҫ����ܳ�ʱ��
                {
                    if (System.currentTimeMillis() - startTime > Integer.MAX_VALUE)
                        break;
                }
                System.out.println("����д����");
            } finally {
                lock.unlock();
            }
        }

public void read() throws InterruptedException {

    lock.lockInterruptibly();// ע�����������Ӧ�ж�
    try {
        System.out.println("�����buff������");
    } finally {
        lock.unlock();
    }
}

    }

    static class Reader extends Thread {

        private BufferInterruptibly buff;

        public Reader(BufferInterruptibly buff) {

            this.buff = buff;
        }

        @Override
        public void run() {

            try {
                buff.read();// �����յ��жϵ��쳣���Ӷ���Ч�˳�
            } catch (InterruptedException e) {
                System.out.println("�Ҳ�����");
            }

            System.out.println("������");

        }
    }

    /**
     * Writer��������ô�Ķ�
     */
    static class Writer extends Thread {

        private BufferInterruptibly buff;

        public Writer(BufferInterruptibly buff) {

            this.buff = buff;
        }

        @Override
        public void run() {

            buff.write();
        }

    }

    public static void main(String[] args) {

        BufferInterruptibly buff = new BufferInterruptibly();

        final Writer writer = new Writer(buff);
        final Reader reader = new Reader(buff);

        writer.start();
        reader.start();

        new Thread(new Runnable() {

            public void run() {

                long start = System.currentTimeMillis();
                for (;;) {
                    if (System.currentTimeMillis() - start > 5000) {
                        System.out.println("�����ˣ������ж�");
                        reader.interrupt();
                        break;
                    }

                }

            }
        }).start();

    }

}
