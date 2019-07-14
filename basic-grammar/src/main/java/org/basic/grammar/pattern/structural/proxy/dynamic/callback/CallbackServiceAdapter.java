/**
 * Copyright reserved by Tellabs Communication Corp. LTD. The file
 * org.basic.pattern.proxy.dynamic.callback.CallbackServiceAdapter.java is created on 2007-9-17
 * 下午03:30:24
 */
package org.basic.grammar.pattern.structural.proxy.dynamic.callback;

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
