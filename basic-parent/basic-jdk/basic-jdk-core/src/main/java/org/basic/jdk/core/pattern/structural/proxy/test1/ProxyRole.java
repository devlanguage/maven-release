package org.basic.jdk.core.pattern.structural.proxy.test1;

public class ProxyRole extends AbstractRole {

    private RealRole realSubject; // 以真实角色作为代理角色的属性

    public ProxyRole() {

    }

    // 该方法封装了真实对象的request方法
    public void request() {

        preRequest();
        if (realSubject == null) {
            realSubject = new RealRole();
        }

        realSubject.request(); // 此处执行真实对象的request方法
        postRequest();

    }

    private void preRequest() {

        // something you want to do before requesting

    }

    private void postRequest() {

        // something you want to do after requesting

    }

}