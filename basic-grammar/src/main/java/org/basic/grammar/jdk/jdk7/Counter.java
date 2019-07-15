package org.basic.grammar.jdk.jdk7;


public class Counter {

    private long value = 0L;
    private int freqeuncy = 200;
    private long start = 0, end = 0;
    private long total = 0L;

    public synchronized void waitUntilOver(long limit) {
        while(total<limit){
            try {
                wait(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * @return get method for the field total
     */
    public synchronized long getTotal() {

        return this.total;
    }

    /**
     * @param total
     *            the total to set
     */
    public synchronized void setTotal(long total) {

        this.total = total;
    }

    public Counter() {

    }

    public Counter(int freqeuncy) {

        this.freqeuncy = freqeuncy;
    }

    public long getStart() {

        return start;
    }

    public void setStart(long start) {

        this.start = start;
    }

    public long getEnd() {

        return end;
    }

    public void setEnd(long end) {

        this.end = end;
    }

    public synchronized long incrementAndGet() {

        total++;
        if (value == 0) {
            start = System.currentTimeMillis();
        }
        return ++value;
    }

    public synchronized long decrementAndGet() {

        if (value > 0L) {
            return --value;
        } else {
            return value;
        }
    }

    public synchronized void reset() {

        value = 0L;
    }

    public synchronized long get() {

        return value;
    }

    public final void printPerformance(String target) {

        if (value == 0) {
            incrementAndGet();
        } else {
            incrementAndGet();
            if (value % freqeuncy == 0) {
                end = System.currentTimeMillis();
                long interval = (end - start);
                if (interval != 0) {
                    System.err.println(target+"--Process Speed: " + (value * 1000.0 / interval)
                            + " times per seconds");
                } else {
                    System.err.println(target+"Process Speed is too high. JVM supported minimum time slot is milliseconds. please increase the Counter.frequence");
                }
                start = end;
                value = 0L;
            }
        }
    }

    public int getFreqeuncy() {

        return freqeuncy;
    }

    public void setFreqeuncy(int freqeuncy) {

        this.freqeuncy = freqeuncy;
    }
}
