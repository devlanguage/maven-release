package org.basic.jdk.core.pattern.structural.proxy.dynamic.callback;

/**
 * 
 */
public class CallbackServiceAdapter implements CallbackService {

    private final CallbackService cb;

    public CallbackServiceAdapter(CallbackService cb) {

        this.cb = cb;
    }

    public void doCallback() {

        cb.doCallback();
    }
}
