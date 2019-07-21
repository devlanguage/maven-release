package org.basic.grammar.pattern.structural.proxy.dynamic.callback;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class GenericProxyFactory {

    @SuppressWarnings("unchecked")
    public static <T> T getProxy(Class<T> intf, final T obj) {

        return (T) Proxy.newProxyInstance(obj.getClass().getClassLoader(), new Class[] { intf },
                new InvocationHandler() {

                    public Object invoke(final Object proxy, final Method method,
                            final Object[] args) throws Throwable {

                        return method.invoke(obj, args);
                    }
                });
    }
}
