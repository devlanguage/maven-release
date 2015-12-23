package org.basic.aop.staticaop;
public class Hello implements IHello {
    public void sayHello(String name) {
        System.out.println("Hello " + name);
    }
}