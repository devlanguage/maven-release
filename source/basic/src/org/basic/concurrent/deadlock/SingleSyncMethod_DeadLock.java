package org.basic.concurrent.deadlock;

public class SingleSyncMethod_DeadLock {

    private static class ClassA {

        public int i = 0;

        public synchronized void setI(int i) {

            this.i = i;
        }

        public synchronized int gestI() {

            return this.i;
        }
    }

    public static void main(String[] args) {

        final ClassA a1 = new ClassA();
        Thread t1 = new Thread(new Runnable() {

            public void run() {

                while (true) {
//                    try {
//                        Thread.sleep(1);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                    System.out.println("Print the ClassA.i=" + a1.gestI());
                }
            }
        });
        Thread t2 = new Thread(new Runnable() {

            public void run() {

                while (true) {
//                    try {
//                        Thread.sleep(1);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                    System.out.println("Update the ClassA.i");
                    a1.setI(2);
                }
            }
        });
        t1.start();
        t2.start();

    }
}
