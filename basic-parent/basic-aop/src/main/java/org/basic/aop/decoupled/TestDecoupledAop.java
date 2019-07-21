package org.basic.aop.decoupled;

import org.basic.aop.proxyaop.Hello;
import org.basic.aop.proxyaop.IHello;

public class TestDecoupledAop {
    public static void main(String[] args) {

        IHello hello = (IHello) new DecoupledDynaProxyHello().bind(new Hello(), new LoggerOperation());
        hello.sayGoogBye("Double J");
        hello.sayHello("Double J");

    }
}
