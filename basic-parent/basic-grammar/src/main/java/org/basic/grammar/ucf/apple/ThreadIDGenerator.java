package org.basic.grammar.ucf.apple;

public class ThreadIDGenerator {

    private ThreadLocal<String> threadLocal;
    private int threadId;

    private static ThreadIDGenerator instance = null;

    private ThreadIDGenerator(ThreadLocal local) {
        this.threadLocal = local;
    }
    public synchronized final static ThreadIDGenerator getInstance() {
        if (instance == null) {
            instance = new ThreadIDGenerator(new ThreadLocal<String>());
        }
        return instance;
    }

    public String generateId() {

        
        if (threadLocal.get() == null) {
            threadLocal.set("W_"+(++threadId));
        }
        return  threadLocal.get();
    }
}
