package org.third.spring.aop.aopstyle.classimpl;

import java.lang.reflect.Method;
import java.rmi.RemoteException;

import javax.servlet.ServletException;

import org.springframework.aop.ThrowsAdvice;

/**
 * ��4���쳣֪ͨ
 * 
 * ThrowsAdvice��һ����ʾ�ӿڣ����ǿ��������ж���һ�����������������쳣֪ͨ��bean�׳����쳣�������׳��쳣ǰִ����Ӧ�ķ���
 * 
 * <pre>
 *  
 * public void afterThrowing(Exception ex)
 * public void afterThrowing(RemoteException)
 * public void afterThrowing(Method method, Object[] args, Object target, Exception ex) 
 * public void afterThrowing(Method method, Object[] args, Object target, ServletException ex)
 * 
 * </pre>
 */
public class ExceptionAdvisor implements ThrowsAdvice {

    public void afterThrowing(Exception ex) {

    }

    public void afterThrowing(RemoteException ex) {
    }

    public void afterThrowing(Method method, Object[] args, Object target, Exception ex) {

    }

    public void afterThrowing(Method method, Object[] args, Object target, ServletException ex) {

    }
}
