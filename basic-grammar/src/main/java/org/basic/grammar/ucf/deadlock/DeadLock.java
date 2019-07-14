/****************************************************************************
 *                 TELLABS PROPRIETARY AND CONFIDENTIAL
 *              UNPUBLISHED WORK COPYRIGHT 2009 TELLABS
 *                          ALL RIGHTS RESERVED
 *      NO PART OF THIS DOCUMENT MAY BE USED OR REPRODUCED WITHOUT
 *                   THE WRITTEN PERMISSION OF TELLABS.
 *  Last modifed on 10:53:42 AM May 14, 2014
 *
 *****************************************************************************
 */
package org.basic.grammar.ucf.deadlock;

/**
 * <pre>
 * jps
 * jstatck 112> deadlock.txt
 * 
 * 2014-05-14 10:56:07
 * Full thread dump Java HotSpot(TM) 64-Bit Server VM (25.5-b02 mixed mode):
 * 
 * "DestroyJavaVM" #13 prio=5 os_prio=0 tid=0x0000000001d80000 nid=0x7268 waiting on condition [0x0000000000000000]
 *    java.lang.Thread.State: RUNNABLE
 * 
 * "t2" #12 prio=5 os_prio=0 tid=0x0000000018848000 nid=0x69a0 waiting for monitor entry [0x000000001ab5f000]
 *    java.lang.Thread.State: BLOCKED (on object monitor)
 *     at org.basic.concurrent.deadlock.DeadLock$2.run(DeadLock.java:47)
 *     - waiting to lock <0x00000000d5a079a0> (a java.lang.Object)
 *     - locked <0x00000000d5a079b0> (a java.lang.Object)
 * 
 * "t1" #11 prio=5 os_prio=0 tid=0x0000000018847000 nid=0x3660 waiting for monitor entry [0x000000001a51f000]
 *    java.lang.Thread.State: BLOCKED (on object monitor)
 *     at org.basic.concurrent.deadlock.DeadLock$1.run(DeadLock.java:31)
 *     - waiting to lock <0x00000000d5a079b0> (a java.lang.Object)
 *     - locked <0x00000000d5a079a0> (a java.lang.Object)
 * 
 * "Service Thread" #10 daemon prio=9 os_prio=0 tid=0x000000001880f000 nid=0x5f84 runnable [0x0000000000000000]
 *    java.lang.Thread.State: RUNNABLE
 * 
 * "C1 CompilerThread3" #9 daemon prio=9 os_prio=2 tid=0x0000000018793800 nid=0x5a18 waiting on condition [0x0000000000000000]
 *    java.lang.Thread.State: RUNNABLE
 * 
 * "C2 CompilerThread2" #8 daemon prio=9 os_prio=2 tid=0x0000000018793000 nid=0x6e40 waiting on condition [0x0000000000000000]
 *    java.lang.Thread.State: RUNNABLE
 * 
 * "C2 CompilerThread1" #7 daemon prio=9 os_prio=2 tid=0x000000001878c000 nid=0x6540 waiting on condition [0x0000000000000000]
 *    java.lang.Thread.State: RUNNABLE
 * 
 * "C2 CompilerThread0" #6 daemon prio=9 os_prio=2 tid=0x0000000018770800 nid=0x6d68 waiting on condition [0x0000000000000000]
 *    java.lang.Thread.State: RUNNABLE
 * 
 * "Attach Listener" #5 daemon prio=5 os_prio=2 tid=0x000000001876f800 nid=0x611c waiting on condition [0x0000000000000000]
 *    java.lang.Thread.State: RUNNABLE
 * 
 * "Signal Dispatcher" #4 daemon prio=9 os_prio=2 tid=0x000000001876e000 nid=0x1ddc runnable [0x0000000000000000]
 *    java.lang.Thread.State: RUNNABLE
 * 
 * "Finalizer" #3 daemon prio=8 os_prio=1 tid=0x00000000175a1000 nid=0x6d4c in Object.wait() [0x0000000019cef000]
 *    java.lang.Thread.State: WAITING (on object monitor)
 *     at java.lang.Object.wait(Native Method)
 *     - waiting on <0x00000000d5b887a8> (a java.lang.ref.ReferenceQueue$Lock)
 *     at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:142)
 *     - locked <0x00000000d5b887a8> (a java.lang.ref.ReferenceQueue$Lock)
 *     at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:158)
 *     at java.lang.ref.Finalizer$FinalizerThread.run(Finalizer.java:209)
 * 
 * "Reference Handler" #2 daemon prio=10 os_prio=2 tid=0x0000000017596000 nid=0x5be4 in Object.wait() [0x0000000019a0e000]
 *    java.lang.Thread.State: WAITING (on object monitor)
 *     at java.lang.Object.wait(Native Method)
 *     - waiting on <0x00000000d5b80b20> (a java.lang.ref.Reference$Lock)
 *     at java.lang.Object.wait(Object.java:502)
 *     at java.lang.ref.Reference$ReferenceHandler.run(Reference.java:157)
 *     - locked <0x00000000d5b80b20> (a java.lang.ref.Reference$Lock)
 * 
 * "VM Thread" os_prio=2 tid=0x0000000017591000 nid=0x1afc runnable 
 * 
 * "GC task thread#0 (ParallelGC)" os_prio=0 tid=0x0000000001d94800 nid=0x6578 runnable 
 * 
 * "GC task thread#1 (ParallelGC)" os_prio=0 tid=0x0000000001d96000 nid=0x70a8 runnable 
 * 
 * "GC task thread#2 (ParallelGC)" os_prio=0 tid=0x0000000001d97800 nid=0x1964 runnable 
 * 
 * "GC task thread#3 (ParallelGC)" os_prio=0 tid=0x0000000001d99000 nid=0x30d4 runnable 
 * 
 * "GC task thread#4 (ParallelGC)" os_prio=0 tid=0x0000000001d9c800 nid=0x73c8 runnable 
 * 
 * "GC task thread#5 (ParallelGC)" os_prio=0 tid=0x0000000001d9d800 nid=0x42c runnable 
 * 
 * "GC task thread#6 (ParallelGC)" os_prio=0 tid=0x0000000001d9e800 nid=0x6cbc runnable 
 * 
 * "GC task thread#7 (ParallelGC)" os_prio=0 tid=0x0000000001da0000 nid=0x3404 runnable 
 * 
 * "VM Periodic Task Thread" os_prio=2 tid=0x0000000018821800 nid=0x2118 waiting on condition 
 * 
 * JNI global references: 6
 * 
 * 
 * Found one Java-level deadlock:
 * =============================
 * "t2":
 *   waiting to lock monitor 0x00000000175a0c08 (object 0x00000000d5a079a0, a java.lang.Object),
 *   which is held by "t1"
 * "t1":
 *   waiting to lock monitor 0x000000001759f6b8 (object 0x00000000d5a079b0, a java.lang.Object),
 *   which is held by "t2"
 * 
 * Java stack information for the threads listed above:
 * ===================================================
 * "t2":
 *     at org.basic.concurrent.deadlock.DeadLock$2.run(DeadLock.java:47)
 *     - waiting to lock <0x00000000d5a079a0> (a java.lang.Object)
 *     - locked <0x00000000d5a079b0> (a java.lang.Object)
 * "t1":
 *     at org.basic.concurrent.deadlock.DeadLock$1.run(DeadLock.java:31)
 *     - waiting to lock <0x00000000d5a079b0> (a java.lang.Object)
 *     - locked <0x00000000d5a079a0> (a java.lang.Object)
 * 
 * Found 1 deadlock.
 * 
 * 
 * </pre>
 */
public class DeadLock {

    public static void main(String[] args) {
        System.setProperty("java.security.auth.debug", "true");
        final Object obj_1 = new Object(), obj_2 = new Object();

        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                synchronized (obj_1) {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                    }

                    synchronized (obj_2) {
                        System.out.println("thread t1 done.");
                    }
                }
            }
        };

        Thread t2 = new Thread("t2") {
            @Override
            public void run() {
                synchronized (obj_2) {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                    }

                    synchronized (obj_1) {
                        System.out.println("thread t2 done.");
                    }
                }
            }
        };

        t1.start();
        t2.start();
        
        
    }

}