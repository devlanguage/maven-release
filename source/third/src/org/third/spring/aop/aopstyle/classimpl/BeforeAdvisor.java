package org.third.spring.aop.aopstyle.classimpl;

import java.lang.reflect.Method;

import org.springframework.aop.MethodBeforeAdvice;

/**
 * ��1��ǰ��֪ͨ
 * 
 * ������ǰ��֪ͨ��bean,��ִ��ҵ�񷽷�ǰ������ִ��ǰ����������before����
 * 
 * 
 */
public class BeforeAdvisor implements MethodBeforeAdvice {

    public void before(Method method, Object[] args, Object obj) throws Throwable {
        String type = (String) args[0];
        System.out.println("Hello welcome to bye " + type);
    }

}
