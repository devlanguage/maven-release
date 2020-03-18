package org.basic.jdk.core.pattern.structural.proxy.dynamic.callback;

public class TestServiceConsumer implements CallbackService {
    private TestService service;
    
    public void someMethod() {
//        ...
        service.serviceMethod(this);
    }

    public void doCallback() {

    }

}