package org.basic.aop.staticaop;
import org.basic.aop.AopLevel;
import org.basic.aop.AopLogger;
public class HelloProxy implements IHello {
    private IHello hello;
    public HelloProxy(IHello hello) {
        this.hello = hello;
    }
    public void sayHello(String name) {
        AopLogger.logging(AopLevel.DEBUG, "sayHello method start.");
        hello.sayHello(name);
        AopLogger.logging(AopLevel.INFO, "sayHello method end!");
    }
}