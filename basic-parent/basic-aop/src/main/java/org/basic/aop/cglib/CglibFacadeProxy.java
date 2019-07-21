package org.basic.aop.cglib;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;

public class CglibFacadeProxy implements net.sf.cglib.proxy.MethodInterceptor, net.sf.cglib.proxy.CallbackFilter {
    private final static net.sf.cglib.proxy.Enhancer enhancer = new Enhancer();
    private Object target;

    public Object createProxy(Object delegate) {
        this.target = delegate;
        enhancer.setSuperclass(delegate.getClass());// 代理类会生产一个被代理类的子类，final方法除外
        enhancer.setCallbacks(new net.sf.cglib.proxy.Callback[] { net.sf.cglib.proxy.NoOp.INSTANCE, this });// 将本身设为回调对象，必须实现接口MehtoInterceptor
        enhancer.setCallbackFilter(this);
        return enhancer.create();
    }

    @Override
    // MethodInterceptor
    public Object intercept(Object obj, java.lang.reflect.Method method, Object[] aobj, net.sf.cglib.proxy.MethodProxy methodproxy)
            throws Throwable {
        System.out.println("====Callback Intercepter start");
        Object returnedObj = methodproxy.invoke(target, aobj);
        return returnedObj;
    }

    final static int CallbackNoOp = 0;
    final static int CallbackFacade = 1;

    @Override
    // CallbackFilter
    public int accept(Method method) {
        return 1;
//
//        if (method.getName().startsWith("say")) {
//            return CallbackFacade;
//        } else if (method.getName().startsWith("print")) {
//            System.out.println("====Skip Callback Intercepter");
//        }
//        return 0;
    }

}
