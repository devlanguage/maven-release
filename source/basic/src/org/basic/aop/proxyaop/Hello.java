package org.basic.aop.proxyaop;
public class Hello implements IHello {
    public void sayHello(String name) {
        System.out.println("Hello " + name);
    }
    public void sayGoogBye(String name) {
        System.out.println(name+" GoodBye!");
    }
    @Override
    public void printHello(String name) {
        System.out.println(name+" printHello!");
    }
}