package org.basic.aop.cglib.book.auth;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class UserAuthInterceptor implements MethodInterceptor {

    private String name; // ��Ա��¼��

    public UserAuthInterceptor(String name) {

        this.name = name;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public Object intercept(Object realObject, Method arg1, Object[] args, MethodProxy proxy)
            throws Throwable {

        // Ȩ��У�飬����Ա��Ϊ:maurice������Ȩ������������ʾû��Ȩ��
        if (BookCustomerTest.VALID_USER.equals(this.name)) {
            System.out.println("�û�"+this.name+"������BookManager�ϵ��÷�����"+arg1.getName());
            return proxy.invokeSuper(realObject, args);
        } else {
            System.out.println("�Ƿ��û�����������BookManager�ϵ��÷�����"+arg1.getName());
            return null;
        }

    }

}