package org.third.concurrent.conversant_disruptor;

public class ValueEvent {
    private long value;

    public long getValue() {
        return value;
    }

    public void setValue(final long value) {
        this.value = value;
    }

}
