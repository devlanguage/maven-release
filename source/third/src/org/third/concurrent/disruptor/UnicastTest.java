package org.third.concurrent.disruptor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

import com.lmax.disruptor.BatchEventProcessor;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SequenceBarrier;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.lmax.disruptor.YieldingWaitStrategy;

class UnicastProducer implements Runnable {

    private com.lmax.disruptor.RingBuffer<ValueEvent> ringBuffer = null;

    public UnicastProducer(com.lmax.disruptor.RingBuffer<ValueEvent> rb) {

        ringBuffer = rb;
    }

    @Override
    public void run() {
        while (true) {

            // Publishers claim events in sequence
            long sequence = ringBuffer.next();
            ValueEvent event = ringBuffer.get(sequence);
            event.setValue(1234); // this could be more complex with multiple fields

            // make the event available to EventProcessors
            ringBuffer.publish(sequence);
        }
    }
}

class UnicastConsumeEventHandler implements com.lmax.disruptor.EventHandler<ValueEvent> {
    java.util.concurrent.atomic.AtomicLong counter = new AtomicLong(0);

    @Override
    public void onEvent(ValueEvent event, long sequence, boolean endOfBatch) throws Exception {
        System.out.println("==========" + counter.getAndIncrement() + "========" + event.getValue());
    }

}

public class UnicastTest {
    private static final int BUFFER_SIZE = 512;

    private final RingBuffer<ValueEvent> ringBuffer = RingBuffer.createSingleProducer(ValueEvent.EVENT_FACTORY, BUFFER_SIZE,
            new YieldingWaitStrategy());
    private final SequenceBarrier sequenceBarrier = ringBuffer.newBarrier();

    private final UnicastConsumeEventHandler handler = new UnicastConsumeEventHandler();

    private final ExecutorService EXECUTOR = Executors.newSingleThreadExecutor();

    private final BatchEventProcessor<ValueEvent> batchEventProcessor = new BatchEventProcessor<ValueEvent>(ringBuffer, sequenceBarrier,
            handler);

    public UnicastTest() {
        ringBuffer.addGatingSequences(batchEventProcessor.getSequence());
    }

    public void consume() {
        EXECUTOR.submit(batchEventProcessor);
    }

    public void produce() {
        new Thread(new UnicastProducer(ringBuffer)).start();
    }

    public static void main(String[] args) {

        UnicastTest test = new UnicastTest();
        test.produce();
        test.consume();
    }
}
