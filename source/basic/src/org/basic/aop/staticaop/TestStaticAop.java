package org.basic.aop.staticaop;

public class TestStaticAop {
    public static void main(String[] args) {
        IHello aopHello = new HelloProxy(new Hello());
        aopHello.sayHello("Doublej");
        
        //去掉aop日志功能
        IHello hello = new Hello();
        hello.sayHello("Doublej");
    }
}
