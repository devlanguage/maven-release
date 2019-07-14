1.一个线程通过以下方式获得Monitor
    通过执行此对象的同步实例方法。 
    通过执行在此对象上进行同步的 synchronized 语句的正文。 
    对于 Class 类型的对象，可以通过执行该类的同步静态方法。 
2.线程状态
   New：             当使用new创建一个线程时
   Runnable: 调用start或是从blocked状态出来时
   Blocked：    sleep, block on input/output, try to acquire lock, suspend, wait.
   Dead:     运行完成或有exception产生
3. 创建线程
   1） 直接继承类java.lang.Thread
   2) 实现接口 java.lang.Runnable (Recommend: 业务放于这里，Thread多用于控制线程运行)   
4. 终止线程
   1） 直接调用stop，已经不被推荐
   2） 调用thread的interrupt方法。
    try {. . .
        while (!Thread.currentThread().isInterrupted() && more work to do) {
            //do more work
        }
    } catch(InterruptedException e){
        // thread was interrupted during sleep or wait
    } finally {
        cleanup, if required
    }
    // exiting the run method terminates the thread
5. 线程退出
    1) 在securityManager允许的情况下，调用了System.exit
    2) 从run方法中返回
    3) 抛出一个传播到run方法之外的异常

与线程相关的一些常用方法：
--------------------------------------------------------------------------------------------------------------
sleep():         强迫一个线程睡眠Ｎ毫秒。  线程{不会放弃任何已经拿到的Monitors}.
wait(timeout):   (1) 当前线程必须拥有对该对象的Monitor--此方法将导致该线程放弃monitor。 
                 (2) 放置当前线程(T)到对象(O)等待列表中，释放在该对象上的monitor, 进入blocked状态。
                 (3) 下面条件之一发生时重新进入runnable状态
                    a) 其他线程调用该对象notify, 并且T别选中
                    b) 其他线程调用该对象的notifyAll.
                    c) 其他线程interrupt线程T
                    d) 大约已到指定的timeout。如果timeout=0，将忽略时间因素。在notify或interrupt前一直blocked
                 (4) 被notify/interrupte/timeout, 从等待队列中移除. 线程重新进入runnable并与其他线程竞争monitor.
                 (5) 线程重新获得在(O)上的monitor，(T)和(O)同步状态回到调用wait时的状态
                 (6) 从wait方法返回，(T),(O)的所有同步状态恢复如初
                 (7)如果当前线程在等待之前或在等待时被任何线程中断，则会抛出 InterruptedException。在按(2-5)上述形式恢复此对象的锁定状态时才会抛出此异常
                 (8) 在没有被通知、中断或超时的情况下，线程还可以唤醒一个所谓的虚假唤醒 (spurious wakeup)。虽然这种情况在实践中很少发生，但是应用程序
                      必须通过以下方式防止其发生，即对应该导致该线程被提醒的条件进行测试，如果不满足该条件，则继续等待。换句话说，等待应总是发生在循环中，如下面的示例： 
                    synchronized (obj) {
                      while (<condition does not hold>) {
                        obj.wait(timeout);
                        ... // Perform action appropriate to condition
                      }
                   }
notify():        (1) 当前线程必须拥有对该对象的Monitor--此方法不会导致该线程放弃monitor。 
                 (2) 随机挑选一个正在当前Object上等待的线程并唤醒他。 
                 (3) 除非当前线程释放在该对象上的monitor，否则新释放的线程依然无法获得该对象上的monitor
------------------------------------------------------------------------------------------------------------- 
--------------------------------------------------------------------------------------------------------------          
interrupt():     中断一个线程，除非这个线程就是当前线程，他总是被允许的。否则，这个线程的checkAccess方法被调用（可能引起SecurityException）异常
interrupted()：    static 方法。返回是否《当前线程》已被中断，然后清除《当前线程》已中断状态。如果连续调用两次，第二次将返回false。
isInterrupted()： 测试是否《一个线程》已被中断，但是该线程的中断状态不受任何影响。 如果已被中断，返回true；否则false.
--------------------------------------------------------------------------------------------------------------
isAlive():       测试线程是否处于活动状态。如果线程已经启动且尚未终止，则为活动状态
isDaemon():      一个线程是否为守护线程
--------------------------------------------------------------------------------------------------------------
join():          等待该线程终止。 InterruptedException - 如果任何线程中断了当前线程。当抛出该异常时，当前线程的中断状态 被清除
activeCount():   程序中活跃的线程数。
enumerate():     枚举程序中的线程。
currentThread(): 得到当前线程。
--------------------------------------------------------------------------------------------------------------
setDaemon():     设置一个线程为守护线程。(用户线程和守护线程的区别在于，是否等待主线程依赖于主线程结束而结束)
setName():       为线程设置一个名称。
setPriority():   设置一个线程的优先级
--------------------------------------------------------------------------------------------------------
-------Static Method
--------------------------------------------------------------------------------------------------------
sleep(long):     指定的毫秒数内让当前正在执行的线程休眠（暂停执行），此操作受到系统计时器和调度程序精度和准确性的影响。该线程不丢失任何监视器的所属权
holdsLock(Object): 如果当前线程在指定的对象上保持监视器锁，则返回 true
dumpStack():        将当前线程的堆栈跟踪打印至标准错误流。该方法仅用于调试
yield():         
--------------------------------------------------------------------------------------------------------------
----- deprecated methods ------------
--------------------------------------------------------------------------------------------------------------
suspend()挂起线程
resume()恢复线程执行
不推荐使用suspend()的原因：
1、  当线程占有某些共享资源时，将其挂起，很容易造成死锁，
2、  当执行某些不能被部分完成的大操作时，线程可能会被挂起
不推荐使用resume()的原因是因为不推荐使用suspend()
--------------------------------------------------------------------------------------------------------------
Stop() 调用stop()方法时，线程会立刻抛出ThreadDeath error(java.lang.Error的子类，java.lang.Error本身是java.lang.Throwable的子类)，
    线程会释放所有锁，run()立刻返回，线程死亡
 stop被取消的原因：
1、  当一个线程被粗暴中止，可能还没有进行清除工作
2、  锁机制就是防止访问共同资源其他线程处于不连续状态，线程被stop之后，会释放其拥有所有锁，造成其它线程不连续

public class AlternateStop implements Runnable {
 
    private Thread runThread;
    private volatile boolean stopRequest;

    public void run() {
        this.runThread = Thread.currentThread();
        stopRequest = false;
        
        int counter = 0;
        while(!stopRequest){
            System.out.println("running ... ... count = " + counter);
            counter++;
            
            try{
                Thread.sleep(300);
            }catch(InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
    }
    
    public void stopThread(){
        this.stopRequest = true;
        if(this.runThread != null){
            this.runThread.interrupt();
        }
    }
     public static void main(String[] args) {
        AlternateStop at = new AlternateStop();
        Thread newThread = new Thread(at);
        newThread.start();
        try{
            Thread.sleep(2000);
        }catch(InterruptedException e){
            System.out.println(e.getMessage());
        }
        at.stopThread();
    }
    
}
--------------------------------------------------------------------------------------------------------------
Lock:
   所有 Lock 实现都必须 实施与内置监视器锁提供的相同内存同步语义
  Lock 实现提供了比使用 synchronized 方法和语句可获得的更广泛的锁定操作。 
  支持那些语义不同（重入、公平等）的锁规则，可以在非阻塞式结构的上下文（包括 hand-over-hand 和锁重排算法）中使用这些规则
  锁是控制多个线程对共享资源进行访问的工具。通常，锁提供了对共享资源的独占访问。一次只能有一个线程获得锁，对共享资源的所有访问都需要首先获得锁。不过，某些锁可能允许对共享资源并发访问，如 ReadWriteLock 的读取锁。 
    void lock()                                 获取锁。 
    void lockInterruptibly()                    如果当前线程未被中断，则获取锁。 
    Condition newCondition()                    返回绑定到此 Lock 实例的新 Condition 实例。 
    boolean tryLock()                           仅在调用时锁为空闲状态才获取该锁。 
    boolean tryLock(long time, TimeUnit unit)   如果锁在给定的等待时间内空闲，并且当前线程未被中断，则获取锁。 
    void unlock()                               释放锁。 
    
Condition:
  Condition 将 Object 监视器方法（wait、notify 和 notifyAll）分解成截然不同的对象，以便通过将这些对象与任意 Lock 实现组合使用，为每个对象提供多个等待 set（wait-set）。 
  Condition 替代了 Object 监视器方法的使用
    void await()                                造成当前线程在接到信号或被中断之前一直处于等待状态。 
    boolean await(long time, TimeUnit unit)     造成当前线程在接到信号、被中断或到达指定等待时间之前一直处于等待状态。 
    long awaitNanos(long nanosTimeout)          造成当前线程在接到信号、被中断或到达指定等待时间之前一直处于等待状态。 
    void awaitUninterruptibly()                 造成当前线程在接到信号之前一直处于等待状态。 
    boolean awaitUntil(Date deadline)           造成当前线程在接到信号、被中断或到达指定最后期限之前一直处于等待状态。 
    void signal()                               唤醒一个等待线程。 
    void signalAll()                            唤醒所有等待线程。 
    
ReadWriteLock:
    ReadWriteLock 维护了一对相关的锁，一个用于只读操作，另一个用于写入操作。 


----------------------------------------------------------------------------------------------------------------
------------------------------------ 锁类型， 对象锁和同步代码块------------------------------------------------
------------------------------------------------------------------------------------------------------------
------------ 对象锁
 1） 对象的锁所有对象都自动含有单一的锁。
 2）  JVM负责跟踪对象被加锁的次数。如果一个对象被解锁，其计数变为0. 在任务（线程）第一次给对象加锁的时候，计数变为1.每当这个相同的任务（线程）在此对象上获得锁时，计数会递增。
 3）    只有首先获得锁的任务（线程）才能继续获取该对象上的多个锁。
 4）    每当任务离开一个synchronized方法，计数递减，当计数为0的时候，锁被完全释放，此时别的任务就可以使用此资源。
------------ 同步代码块    
 1） 同步到单一对象锁: 当使用同步块时，如果方法下的同步块都同步到一个对象上的锁，则所有的任务（线程）只能互斥的进入这些同步块。
 2） 同步到不同对象锁：  当使用同步块时，对他们方法中的临界资源访问是独立的 
 
----------------------------------------------------------------------------------------------------------------
------------------------------------------         多个方法间的同步  ---------------------------------------------
----------------------------------------------------------------------------------------------------------------
synchronized仅仅能够对方法或者代码块进行同步，
如果我们一个应用需要跨越多个方法进行同步，synchroinzed就不能胜任了。在C++中有很多同步机制，比如信号量、互斥体、临届区等。
在Java中也可以在synchronized语言特性的基础上，在更高层次构建这样的同步工具，以方便我们的使用。
    当前，广为使用的是由Doug Lea编写的一个Java中同步的工具包，可以在这儿了解更多这个包的详细情况：
    http://gee.cs.oswego.edu/dl/classes/EDU/oswego/cs/dl/util/concurrent/intro.html
  该工具包已经作为JSR166正处于JCP的控制下，即将作为JDK1.5的正式组成部分
 