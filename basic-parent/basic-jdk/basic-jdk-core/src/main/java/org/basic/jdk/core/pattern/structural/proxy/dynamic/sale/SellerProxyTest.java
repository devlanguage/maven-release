package org.basic.jdk.core.pattern.structural.proxy.dynamic.sale;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.basic.jdk.core.pattern.structural.proxy.dynamic.callback.CallbackService;

/**
 * 
 */
public class SellerProxyTest {

    public Object getExecutor(String realSubject) {

        Object realSubjectObject = null;
        if (realSubject.equals("Retailer")) {
            realSubjectObject = new Retailer();
        } else if (realSubject.equals("Wholesale")) {
            realSubjectObject = new Wholesale();
        }

        return realSubjectObject;
    }

    public Seller getSellerProxy(final String realObject) {

        return (Seller) Proxy.newProxyInstance(Seller.class.getClassLoader(), new Class[] { Seller.class, CallbackService.class },
                new InvocationHandler() {

                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                        return method.invoke(getExecutor(realObject), args);
                    }

                });

    }

    public static void main(String[] args) {

        SellerProxyTest proxy = new SellerProxyTest();

        Seller seller = proxy.getSellerProxy(Retailer.class.getSimpleName());
        seller.sellSingle();

        seller = proxy.getSellerProxy(Wholesale.class.getSimpleName());
        seller.sellBatch();
    }
}
