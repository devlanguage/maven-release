package org.third.concurrent.conversant_disruptor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

class UnicastProducer implements Runnable {
	BlockingQueue<ValueEvent> disruptorBlockingQueue;
	AtomicLong counter;

	public UnicastProducer(BlockingQueue<ValueEvent> disruptorBlockingQueue, AtomicLong counter) {
		this.disruptorBlockingQueue = disruptorBlockingQueue;
		this.counter = counter;
	}

	@Override
	public void run() {
		long start = System.currentTimeMillis();

		boolean running = true;
		while (running) {
			// Publishers claim events in sequence
			ValueEvent event = new ValueEvent();
			event.setValue(counter.getAndIncrement()); // this could be more complex with multiple fields

			// make the event available to EventProcessors
			try {
				disruptorBlockingQueue.put(event);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (event.getValue() % DisruptorBlockingQueueTest.buffer == 0) {
				System.out.println("Produce: " + event.getValue());
			}
			if (counter.get() == DisruptorBlockingQueueTest.limit) {
				long end = System.currentTimeMillis();
				System.out.println(String.format("Producer take %d", (end - start)));
				running = false;
			}
		}
	}
}

class UnicastConsumer implements Runnable {
	BlockingQueue<ValueEvent> disruptorBlockingQueue;
	AtomicLong counter;

	public UnicastConsumer(BlockingQueue<ValueEvent> disruptorBlockingQueue, AtomicLong counter) {
		this.disruptorBlockingQueue = disruptorBlockingQueue;
		this.counter = counter;

	}

	@Override
	public void run() {
		long start = System.currentTimeMillis();
		boolean running = true;
		while (running) {

			// Publishers claim events in sequence
			try {
				ValueEvent event = disruptorBlockingQueue.take();
				counter.getAndIncrement();
				if (counter.get() % DisruptorBlockingQueueTest.buffer == 0) {
					System.out.println("Consume:" + event.getValue());
				}
				if (counter.get() == DisruptorBlockingQueueTest.limit) {
					long end = System.currentTimeMillis();
					System.out.println(String.format("Producer take %d", (end - start)));
					running = false;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

public class DisruptorBlockingQueueTest {

	public static final int buffer = 1_0000_000;
	public static final int limit = 30_0000_000;
	private static final int concurency = 10;
	private static final int poolsize = concurency << 1;

	private static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(poolsize);

	public DisruptorBlockingQueueTest() {
	}

	public void consume(BlockingQueue<ValueEvent> disruptorBlockingQueue) {
		AtomicLong counter = new AtomicLong(1);
		for (int i = 0; i < concurency; ++i) {
			EXECUTOR.submit(new UnicastConsumer(disruptorBlockingQueue, counter));
		}
	}

	public void produce(BlockingQueue<ValueEvent> disruptorBlockingQueue) {
		AtomicLong counter = new AtomicLong(1);

		for (int i = 0; i < concurency; ++i) {
			EXECUTOR.submit(new UnicastProducer(disruptorBlockingQueue, counter));
		}
	}

	public static void main(String[] args) {

		DisruptorBlockingQueueTest test = new DisruptorBlockingQueueTest();
//		BlockingQueue<ValueEvent> disruptorBlockingQueue = new DisruptorBlockingQueue<ValueEvent>(buffer);
		BlockingQueue<ValueEvent> disruptorBlockingQueue = new ArrayBlockingQueue<ValueEvent>(buffer);

		test.produce(disruptorBlockingQueue);
		test.consume(disruptorBlockingQueue);
		EXECUTOR.shutdown();
		
		
//		Consume:299064754
//		Producer take 124407

	}
}
