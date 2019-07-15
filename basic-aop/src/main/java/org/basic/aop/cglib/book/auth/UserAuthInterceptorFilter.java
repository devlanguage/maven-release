package org.basic.aop.cglib.book.auth;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.CallbackFilter;

public class UserAuthInterceptorFilter implements CallbackFilter {

    private static final int USER_INTERCEPTOR = 0;
    private static final int NO_INTERCEPTOR = 1;

    public int accept(Method proxiedMethod) {

        // ��δ���ʲô��˼�����е�accept��������˼��˵��������ķ�����query()����ôʹ�õڶ���9����ȥ9�أ�������ķ�������query(),
        // ��ôʹ�õ�һ��9����ȥ9�ء���������ֻҪ��дһ��9�������Ȩ��У������ˡ�����ʵ��cglib�е�NoOp.INSTANCE����һ��յ�9����
        // ֱ�ӵ���ԭ��ʵ��ķ�����û���κζ���Ĵ�����á���

        // enhancer.setCallbacks(new Callback[] { userAuth, NoOp.INSTANCE });
        // enhancer.setCallbackFilter(new UserAuthInterceptorFilter());
        if (proxiedMethod.getName().equals("query")) {
            return NO_INTERCEPTOR;
        } else {
            return USER_INTERCEPTOR;
        }
    }
}
