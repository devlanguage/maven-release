package org.basic.grammar.pattern.creational.Singleton.test1;

/**
 * University of Maryland Computer Science researcher Bill Pugh has written about the code issues underlying the
 * Singleton pattern when implemented in Java.[8] Pugh's efforts on the "Double-checked locking" idiom led to changes in
 * the Java memory model in Java 5 and to what is generally regarded as the standard method to implement Singletons in
 * Java. The technique known as the initialization on demand holder idiom, is as lazy as possible, and works in all
 * known versions of Java. It takes advantage of language guarantees about class initialization, and will therefore work
 * correctly in all Java-compliant compilers and virtual machines. The nested class is referenced no earlier (and
 * therefore loaded no earlier by the class loader) than the moment that getInstance() is called. Thus, this solution is
 * thread-safe without requiring special language constructs (i.e. volatile or synchronized).
 */
public class Singleton1_Lazy {
    // Private constructor prevents instantiation from other classes
    private Singleton1_Lazy(){}
    
    private static Singleton1_Lazy instance_by_sync_method = null;
    public synchronized static Singleton1_Lazy getInstance_BySyncMethod() {
        if( instance_by_sync_method == null )    {
            instance_by_sync_method = new Singleton1_Lazy();
        }
        return instance_by_sync_method;    
    }

    private static Singleton1_Lazy instance_by_bill_pugh = null;

    /**
     * Singleton1_LazyHolder is loaded on the first execution of Singleton.getInstance() or the first access to
     * SingletonHolder.INSTANCE, not before. he nested class is referenced no earlier (and therefore loaded no earlier
     * by the class loader) than the moment that getInstance() is called. Thus, this solution is thread-safe without
     * requiring special language constructs (i.e. volatile or synchronized).
     */
    private static class Singleton1_LazyHolder {
        //不会在在加载Singleton1_Lazy的时候初始INSTANCE字段
      private static final Singleton1_Lazy INSTANCE = new Singleton1_Lazy();
      public Singleton1_LazyHolder(){
          System.out.println("d");
      }
    }
    public static Singleton1_Lazy getInstance() {
      return Singleton1_LazyHolder.INSTANCE;
    }
    
    //This solution is thread-safe in Java 5.0 and later without requiring special language constructs:
    private volatile static Singleton1_Lazy instance_by_volatile_and_double_check;//volatile is needed so that multiple thread can reconcile the instance
    private volatile static Object LOCK = new Object();
    public static Singleton1_Lazy getInstance_ByVolatileAndDoubleCheck() {
        //Double-Checked locking on Reference/long/double is unsafe before JDK5.
        //but it is ok on primitive type such as byte/char/short/int/boolean/float
        if (instance_by_volatile_and_double_check == null) {//synchronized keyword has been removed from here            
            synchronized (LOCK) {//needed because once there is singleton available no need to acquire monitor again & again as it is costly
                if (instance_by_volatile_and_double_check == null) {//this is needed if two threads are waiting at the monitor at the time when singleton was getting instantiated
                    instance_by_volatile_and_double_check = new Singleton1_Lazy();
                }
            }
        }
        return instance_by_volatile_and_double_check;
    }
    
    /**
     * If perThreadInstance.get() returns a non-null value, this thread has done synchronization needed to see
     * initialization of helper
     */
    private final ThreadLocal<Object> perThreadInstance = new ThreadLocal<Object>();
    private Singleton1_Lazy instance_by_thread_local = null;
    public Singleton1_Lazy getInstance_ByThreadLocal() {
        if (perThreadInstance.get() == null) {
            synchronized (Singleton1_Lazy.class) {
                if (instance_by_thread_local == null) {
                    instance_by_thread_local = new Singleton1_Lazy();
                }
            }
            // Any non-null value would do as the argument here
            perThreadInstance.set(perThreadInstance);
        }

        return instance_by_thread_local;
    }
    public static void main(String[] args) {
        System.out.println("sdfasdf");
        System.out.println("sdfasdf");
        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
}