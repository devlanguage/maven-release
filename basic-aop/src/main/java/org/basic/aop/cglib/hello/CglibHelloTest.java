package org.basic.aop.cglib.hello;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Enhancer;

public class CglibHelloTest {

    public void passed_method1() {

        System.out.println("MyClass.passed_method1()");
    }

    public void intercepted_method1() {

        System.out.println("MyClass.intercepted_method1()");
    }

    public static void main(String[] args) {

        net.sf.cglib.proxy.Enhancer enhancer = new Enhancer();

        // 在这代理了
        enhancer.setSuperclass(CglibHelloTest.class);
        enhancer.setCallbacks(new Callback[] { new net.sf.cglib.proxy.MethodInterceptor() {

            public Object intercept(Object obj, Method method, Object[] args,
                    net.sf.cglib.proxy.MethodProxy proxy) throws Throwable {
                System.out.println("\t 所有以intercepted_开头的方法，被CGlib拦截: ");
                return proxy.invokeSuper(obj, args);
            }
        }, net.sf.cglib.proxy.NoOp.INSTANCE });

        enhancer.setCallbackFilter(new CallbackFilter() {

            int Interceptor_MethodInterceptor = 0;
            int Interceptor_cglib_NoOp_INSTANCE = 1;

            public int accept(Method method) {

                if (method.getName().startsWith("passed_")) {
                    return Interceptor_cglib_NoOp_INSTANCE;
                } else if (method.getName().startsWith("intercepted_")) {
                    return Interceptor_MethodInterceptor;
                } else {
                    return 0;
                }
            }
            @Override
            public boolean equals(Object obj) {
            
                return this.getClass().equals(obj.getClass());
            }
            @Override
            public int hashCode() {
            
                return this.getClass().hashCode();
            }
        });

        // 创造 代理 （动态扩展了CglibHelloTest类）
        CglibHelloTest my = (CglibHelloTest) enhancer.create();

        System.out.println("===Passed Methods");
        my.passed_method1();

        System.out.println("===Intercepted Methods");
        my.intercepted_method1();
    }
}
