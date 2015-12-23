package org.basic.jdk.jdk5.concurrent.ResponseInterruptable;

public class UsingSynchronized {

    static class Buffer {

        private Object lock;

        public Buffer() {

            lock = this;
        }

        public void write() {

            synchronized (lock) {
                long startTime = System.currentTimeMillis();
                System.out.println("��ʼ�����buffд�����ݡ�");
                for (;;)// ģ��Ҫ����ܳ�ʱ��
                {
                    if (System.currentTimeMillis() - startTime > Integer.MAX_VALUE)
                        break;
                }
                System.out.println("����д����");
            }
        }

        public void read() {

            synchronized (lock) {
                System.out.println("�����buff������");
            }
        }
    }

    public static class WriterThread extends Thread {

        private Buffer buff;

        public WriterThread(Buffer buff) {

            this.buff = buff;
        }

        @Override
        public void run() {

            buff.write();
        }

    }

    static class ReaderThread extends Thread {

        private Buffer buff;

        public ReaderThread(Buffer buff) {

            this.buff = buff;
        }

        @Override
        public void run() {

            buff.read();// ������ƻ�һֱ����
            System.out.println("������");
        }

    }

    public static void main(String[] args) {

        Buffer buff = new Buffer();

        final WriterThread writer = new WriterThread(buff);
        final ReaderThread reader = new ReaderThread(buff);

        writer.start();
        reader.start();

        new Thread(new Runnable() {

            public void run() {

                long start = System.currentTimeMillis();
                for (;;) {
                    // ��5����ȥ�ж϶�
                    if (System.currentTimeMillis() - start > 3000) {
                        System.out.println("�����ˣ������ж�");
                        reader.interrupt();
                        break;
                    }
                }
            }
        }).start();

    }
}
