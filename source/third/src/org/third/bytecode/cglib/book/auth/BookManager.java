package org.third.bytecode.cglib.book.auth;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

public class BookManager {

    // private static BookManager manger = new BookManager();
    // 
    // private BookManager() {
    //
    // }

    public static BookManager getInstance(UserAuthInterceptor userAuth) {

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(BookManager.class);
        enhancer.setCallback(userAuth);
        return (BookManager) enhancer.create();
    }

    public static BookManager getInstanceWithFilter(UserAuthInterceptor userAuth) {

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(BookManager.class);
        // ��δ���ʲô��˼�����е�accept��������˼��˵��������ķ�����query()����ôʹ�õڶ���9����ȥ9�أ�������ķ�������query(),
        // ��ôʹ�õ�һ��9����ȥ9�ء���������ֻҪ��дһ��9�������Ȩ��У������ˡ�����ʵ��cglib�е�NoOp.INSTANCE����һ��յ�9����ֻҪ���������Ϳ����ˡ���
        enhancer.setCallbacks(new Callback[] { userAuth, NoOp.INSTANCE });
        enhancer.setCallbackFilter(new UserAuthInterceptorFilter());
        // ��ס��setCallbacks�е�9����(interceptor)��˳��һ��Ҫ��CallbackFilter����ָ����˳��һ�£����мɡ�

        return (BookManager) enhancer.create();
    }

    // ģ���ѯ����
    public void query() {

        System.out.println("\tBusiness Method: query() is called");
    }

    // ģ�ⴴ������
    public void create() {

        System.out.println("\tBusiness Method: create() is called");
    }

    // ģ����²���
    public void update() {

        System.out.println("\tBusiness Method: update() is called");
    }

    // ģ��ɾ�����
    public void delete() {

        System.out.println("\tBusiness Method: delete() is called");
    }

}
