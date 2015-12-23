/**
 * Copyright reserved by Tellabs Communication Corp. LTD. The file
 * org.basic.pattern.proxy.dynamic.simple.SetProxyFactory.java is created on 2007-9-17 下午03:17:59
 */
package org.basic.pattern.structural.proxy.dynamic.simple;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

interface ProxyPersonService {
    public Object createUser(String user);
}

class ProxyPersonServiceBean implements ProxyPersonService {

    @Override
    public Object createUser(String user) {
        System.out.println("CreateUser");
        return "Test";
    }

    public Object getUser() {
        System.out.println("getUser");
        return "Test";
    }
}

public class JDKProxyFactory implements java.lang.reflect.InvocationHandler {
    private Object targetObject;

    public Object createProxyIntance(Object targetObject) {
        this.targetObject = targetObject;
        return Proxy.newProxyInstance(this.targetObject.getClass().getClassLoader(), this.targetObject.getClass()
                .getInterfaces(), this);
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {// 环绕通知
        ProxyPersonServiceBean bean = (ProxyPersonServiceBean) this.targetObject;
        Object result = null;
        if (bean.getUser() != null) {
            System.out.println("// ..... advice()-->前置通知");

            try {
                result = method.invoke(targetObject, args);
                System.out.println("// afteradvice() -->后置通知");

            } catch (RuntimeException e) {
                System.out.println("// exceptionadvice() --> 例外通知");
            } finally {
                System.out.println("// finallyadvice() -->最终通知");
            }
        }
        return result;
    }

    public static void main(String[] args) {
//ProxyGenerate generate a new ProxyClass(extends Proxy implements BusinessInterface) and cache it in weak reference
//JDK动态代理的原理是根据定义好的规则，用传入的接口创建一个新类，这就是为什么采用动态代理时为什么只能用接口引用指向代理，而不能用传入的类引用执行动态类。
//cglib采用的是用创建一个继承实现类的子类，用asm库动态修改子类的代码来实现的，所以可以用传入的类引用执行代理类
        JDKProxyFactory factory = new JDKProxyFactory();
        ProxyPersonService service = (ProxyPersonService) factory.createProxyIntance(new ProxyPersonServiceBean());
        service.createUser("888");

    }

}
