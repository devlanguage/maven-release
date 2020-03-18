package org.basic.jdk.core.pattern.structural.proxy.dynamic.simple;

import java.lang.reflect.Method;

interface CglibProxyPersonService {
    public Object createUser(String user);

    public Object getUser();
}

class CglibProxyPersonServiceBean implements CglibProxyPersonService {

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

public class CGLIBProxy implements net.sf.cglib.proxy.MethodInterceptor {
    private Object targetObject;

    public Object createProxyInstance(Object targetObject) {
        this.targetObject = targetObject;
        net.sf.cglib.proxy.Enhancer enhancer = new net.sf.cglib.proxy.Enhancer();// 使用cglib的Enhancer
        // 将目标对象的类设置为enhancer的父类，
        // 那么enhancer会覆盖目标类中的所有非final的方法，在覆盖方法中加入一些自身的代码
        // //设置回调的对象
        enhancer.setSuperclass(this.targetObject.getClass());
        enhancer.setCallback(this);// 回调自身，前提是必须实现MethodInterceptor接口, 会回调intercept方法
        return enhancer.create();// 返回代理对象, 通过字节码技术动态创建子类实例,
    }

    /**
     * @param proxy
     *            代理对象本身
     * @param method
     *            拦截到的方法
     * @param args
     *            方法的所有参数
     * @param methodProxy
     *            方法的代理对象
     */
    @Override
    public Object intercept(Object proxy, Method method, Object[] args, net.sf.cglib.proxy.MethodProxy methodProxy)
            throws Throwable {

        System.out.println("// 调用前处理------------advice()------前置通知");
        CglibProxyPersonService ps = (CglibProxyPersonService) this.targetObject;
        Object result = null;
        if (ps.getUser() != null) {

            try {
                result = methodProxy.invoke(targetObject, args);
                System.out.println("// 调用后也可以处理------------afteradvice()--------后置通知");

            } catch (Exception e) {
                System.out.println("// 出错时处理------------exceptionadvice()---------例外通知");
                e.printStackTrace();
            } finally {
                System.out.println("// 出不出错都处理------------finallyadvice()--------最终通知");
            }
        }

        return result;
    }

    public static void main(String[] args) {
      //ProxyGenerate generate a new ProxyClass(extends Proxy implements BusinessInterface) and cache it in weak reference
      //JDK动态代理的原理是根据定义好的规则，用传入的接口创建一个新类，这就是为什么采用动态代理时为什么只能用接口引用指向代理，而不能用传入的类引用执行动态类。
      //cglib采用的是用创建一个继承实现类的子类，用asm库动态修改子类的代码来实现的，所以可以用传入的类引用执行代理类
        CglibProxyPersonService ps = new CglibProxyPersonServiceBean();
        CGLIBProxy cglibProxy = new CGLIBProxy();
        CglibProxyPersonService psProxy = (CglibProxyPersonService) cglibProxy.createProxyInstance(ps);
        psProxy.createUser("Test");
        // Result follows as:
        // 调用前处理------------advice()------前置通知
        // getUser
        // CreateUser
        // 调用后也可以处理------------afteradvice()--------后置通知
        // 出不出错都处理------------finallyadvice()--------最终通知
    }
}
