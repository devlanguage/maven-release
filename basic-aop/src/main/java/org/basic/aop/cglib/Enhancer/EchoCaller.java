package org.basic.aop.cglib.Enhancer;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class EchoCaller implements MethodInterceptor {

    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        String echoMsg = "调用的方法是" + method.getName();
        System.out.println(echoMsg);
        return echoMsg;
    }
}
