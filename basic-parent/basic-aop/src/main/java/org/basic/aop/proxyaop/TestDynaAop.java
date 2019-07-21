package org.basic.aop.proxyaop;

public class TestDynaAop {

    public static void main(String[] args) {

        IHello hello = (IHello) new DynaProxyHello().bind(new Hello());
        hello.sayGoogBye("Double J");
        hello.sayHello("Double J");

    }
}
