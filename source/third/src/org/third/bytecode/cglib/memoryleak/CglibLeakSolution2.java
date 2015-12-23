package org.third.bytecode.cglib.memoryleak;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import net.sf.cglib.proxy.NoOp;

public class CglibLeakSolution2 {

    public <T> T newProxyInstance(Class<T> clazz) {
        return newProxyInstance(clazz, myInterceptor, new MyFilter());
    }

    private static MethodInterceptor myInterceptor = new MethodInterceptor() {
        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
            // do some things
            return null;
        }
    };

    class MyFilter implements CallbackFilter {// 实现CallbackFilter接口
        @Override
        public int accept(Method method) {
            // Do some thing
            return 1;
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof MyFilter) {
                return true;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return 10011; // 没什么原则，只为测试
        }
    }

    // 创建一个类动态代理.
    private static <T> T newProxyInstance(Class<T> superclass, Callback methodCb, CallbackFilter callbackFilter) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(superclass);
        enhancer.setCallbacks(new Callback[] { methodCb, NoOp.INSTANCE });
        enhancer.setCallbackFilter(callbackFilter);

        return (T) enhancer.create();
    }

    /**
     * 测试代码
     * 
     * @param args
     * @throws InterruptedException
     */
    public static void main(String args[]) throws InterruptedException {
        CglibLeakSolution2 l = new CglibLeakSolution2();
        int count = 0;
        while (true) {
            l.newProxyInstance(Object.class); // 为了测试缩写
            Thread.sleep(100);
            System.out.println(count++);
        }
    }
}