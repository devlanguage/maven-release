package org.basic.aop.cglib.log;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class LogProxy {
    private String methodNameExpression;// Ҫ��ӡ��־�ķ�����������ʽ
    private boolean logInvokeParams = false;// Ĭ�ϲ���ӡ���
    private boolean logInvokeResult = true;// Ĭ�ϴ�ӡ���ý��
    // ������
    public LogProxy(String methodNameExpression) {
        this.methodNameExpression = methodNameExpression;
    }
    // builder��ʽ�����Ƿ���Ҫ��ӡ���
    public LogProxy logInvokeParams(boolean needed) {
        logInvokeParams = needed;
        return this;
    }

    // builder��ʽ�����Ƿ���Ҫ��ӡ���
    public LogProxy logInvokeResult(boolean needed) {

        logInvokeResult = needed;
        return this;
    }

    // ��������ʵ��
    public Object createProxy(Class targetClass, Object... arguments) {

        Class[] argumentTypes = new Class[arguments.length];
        for (int i = 0; i < arguments.length; i++) {
            argumentTypes[i] = arguments[i].getClass();
        }
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(targetClass);
        enhancer.setCallback(new LogMethodInterceptor(methodNameExpression, logInvokeParams,
                logInvokeResult));
        return enhancer.create(argumentTypes, arguments);
    }

    // �ص�ʵ��
    private class LogMethodInterceptor implements MethodInterceptor {
        public LogMethodInterceptor(String methodNameExpression, boolean logInvokeParams,
                boolean logInvokeResult) {
        }
        public Object intercept(Object object, Method method, Object[] args, MethodProxy methodProxy)
                throws Throwable {
            return null;
        }
    }
}
