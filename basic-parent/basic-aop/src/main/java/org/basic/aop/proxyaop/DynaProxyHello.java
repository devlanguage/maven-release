package org.basic.aop.proxyaop;
import org.basic.aop.AopLevel;
import org.basic.aop.AopLogger;

public class DynaProxyHello implements java.lang.reflect.InvocationHandler {

    /**
     * 要处理的对象(也就是我们要在方法的前后加上业务逻辑的对象,如例子中的Hello)
     */
    private Object delegate;

    /**
     * 动态生成方法被处理过后的对象 (写法固定)
     * 
     * @param delegate
     * @param proxy
     * @return
     */
    public Object bind(Object delegate) {

        this.delegate = delegate;
        return java.lang.reflect.Proxy.newProxyInstance(this.delegate.getClass().getClassLoader(), this.delegate
                .getClass().getInterfaces(), this);
    }

    /**
     * 要处理的对象中的每个方法会被此方法送去JVM调用,也就是说,要处理的对象的方法只能通过此方法调用 此方法是动态的,不是手动调用的
     */
    @Override
    public Object invoke(Object proxy, java.lang.reflect.Method method, Object[] args) throws Throwable {

        Object result = null;
        try {
            // 执行原来的方法之前记录日志
            AopLogger.logging(AopLevel.DEBUG, method.getName() + " Method end .");

            // JVM通过这条语句执行原来的方法(反射机制)
            result = method.invoke(this.delegate, args);
            // 执行原来的方法之后记录日志
            AopLogger.logging(AopLevel.INFO, method.getName() + " Method Start!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 返回方法返回值给调用者
        return result;
    }

}