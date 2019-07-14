package org.third.javaee.ejb2;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import javax.ejb.EJBHome;

public class Ejb2HomeProxy_InvocationHandler implements InvocationHandler {

    EJBHome ejbHome;

    public Ejb2HomeProxy_InvocationHandler(String jndiName) {

        ejbHome = new EjbTester(jndiName);
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        return method.invoke(ejbHome, args);
    }
}
