package org.basic.concurrent.sync;

public final class FalseSharing implements Runnable {
    public final static int NUM_THREADS = 8; // change
//    public final static long ITERATIONS = 500L * 1000L * 1000L;
    public final static long ITERATIONS = 100L * 1000L * 1000L;
    private final int arrayIndex;
    private static VolatileLong[] longs = new VolatileLong[NUM_THREADS];
    static {
        for (int i = 0; i < longs.length; i++) {
            longs[i] = new VolatileLong();
        }
    }

    public FalseSharing(final int arrayIndex) {
        this.arrayIndex = arrayIndex;
    }

    public static void main(final String[] args) throws Exception {
        final long start = System.nanoTime();
        runTest();
        System.out.println("duration = " + (System.nanoTime() - start)/1000/1000/1000);
//        duration = 19148282392
//        duration = 26992819087

    }

    private static void runTest() throws InterruptedException {
        Thread[] threads = new Thread[NUM_THREADS];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new FalseSharing(i));
        }

        for (Thread t : threads) {
            t.start();
        }

        for (Thread t : threads) {
            t.join();
        }
    }

    public void run() {

        long i = ITERATIONS + 1;
        while (0 != --i) {
            longs[arrayIndex].value = i;
        }

    }

    public final static class VolatileLong {

        public volatile long value = 0L;
        public long pad1, pad2, pad3, pad4, pad5, pad6; // comment out

    }

}
