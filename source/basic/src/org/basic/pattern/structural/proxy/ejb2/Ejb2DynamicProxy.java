package org.basic.pattern.structural.proxy.ejb2;

import java.lang.reflect.Proxy;
import javax.ejb.*;
public class Ejb2DynamicProxy {

    public static javax.ejb.EJBHome getEjbHome(String jndiName) {

        return (EJBHome) Proxy.newProxyInstance(EJBHome.class.getClassLoader(),
                new Class[] { EJBHome.class },
                new Ejb2HomeProxy_InvocationHandler(jndiName));
    }
}