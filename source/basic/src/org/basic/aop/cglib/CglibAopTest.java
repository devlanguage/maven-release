package org.basic.aop.cglib;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodProxy;


public class CglibAopTest {
    public static void main(String[] args) {
        CglibFacadeProxy factory = new CglibFacadeProxy();
        ICglibHello hello= (ICglibHello) factory.createProxy(new CglibHello());
        hello.sayHello("Zhangsn");
        hello.sayGoogBye("Zhangsn");
        hello.printHello("Zhangsn");
        
//        net.sf.cglib.proxy.Enhancer cglibEnhancer = new net.sf.cglib.proxy.Enhancer();
//        cglibEnhancer.setSuperclass(CglibHello.class);
//        cglibEnhancer.setCallbacks(new net.sf.cglib.proxy.Callback[] {// callback list
//                new net.sf.cglib.proxy.InvocationHandler() {
//
//                    @Override
//                    public Object invoke(Object obj, Method method, Object[] args) throws Throwable {
//                        System.out.println(this.getClass());
//                        return null;
//                    }
//                }, new net.sf.cglib.proxy.MethodInterceptor() {
//
//                    @Override
//                    public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodproxy)
//                            throws Throwable {
//                        System.out.println(this.getClass());
//                        Object returnedObj = methodproxy.invokeSuper(obj, args);
//                        return returnedObj;
//                    }
//                }, new net.sf.cglib.proxy.FixedValue() {
//
//                    @Override
//                    public Object loadObject() throws Exception {
//                        System.out.println(this.getClass());
//                        return null;
//                    }
//                }, net.sf.cglib.proxy.NoOp.INSTANCE });
//        
//        cglibEnhancer.setCallbackFilter(new net.sf.cglib.proxy.CallbackFilter() {
//
//            int Interceptor_InvocationHandler = 0;
//            int Interceptor_MethodInterceptor = 1;
//            int Interceptor_FixedValue = 2;
//            int Interceptor_cglib_NoOp_INSTANCE = 3;
//
//            public int accept(Method method) {
//
//                if (method.getName().startsWith("sayHello")) {
//                    return Interceptor_InvocationHandler;
//                } else if (method.getName().startsWith("sayGoogBye")) {
//                    return Interceptor_MethodInterceptor;
//                } else {//for finallize, toString
//                    return Interceptor_cglib_NoOp_INSTANCE;
//                }
//            }
//
//            @Override
//            public boolean equals(Object obj) {
//
//                return this.getClass().equals(obj.getClass());
//            }
//
//            @Override
//            public int hashCode() {
//
//                return this.getClass().hashCode();
//            }
//        });
//
//        // 创造 代理 （动态扩展了CglibHelloTest类)
//        ICglibHello my = (ICglibHello) cglibEnhancer.create();
//
//        my.sayHello("===Passed Methods");
//        my.sayGoogBye("===Intercepted Methods");

    }

}
