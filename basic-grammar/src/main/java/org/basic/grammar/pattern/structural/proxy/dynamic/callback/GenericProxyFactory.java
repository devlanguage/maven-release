/**
 * Copyright reserved by Tellabs Communication Corp. LTD. The file
 * org.basic.pattern.proxy.dynamic.callback.GenericProxyFactory.java is created on 2007-9-17
 * 下午03:31:44
 */
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
