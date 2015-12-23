package org.basic.aop.cglib;

public class CglibHello implements ICglibHello {

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
