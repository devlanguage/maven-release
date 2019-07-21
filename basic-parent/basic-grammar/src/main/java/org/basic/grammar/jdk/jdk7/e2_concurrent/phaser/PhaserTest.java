package org.basic.grammar.jdk.jdk7.e2_concurrent.phaser;

/**
 * <pre>
 * phaser 类似cyclebarrier和countdownlatch，不过可以动态添加资源减少资源
 * 
 *      void runTasks(List<Runnable> tasks) {
 *     final Phaser phaser = new Phaser(1); // "1" to register self
 *     // create and start threads
 *     for (final Runnable task : tasks) {
 *     phaser.register();
 *     new Thread() {
 *     public void run() {
 *     phaser.arriveAndAwaitAdvance(); // await all creation
 *     task.run();
 *     }
 *     }.start();
 *     }
 *     // allow threads to start and deregister self
 *     phaser.arriveAndDeregister();
 *     }
 * </pre>
 */
public class PhaserTest {

}
