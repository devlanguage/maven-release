package org.third.testdata.user;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.Ordered;

@Aspect
/**
 * implements Advisor
 */
public class SystemArchitectureAdvice_01 implements Ordered {

    @Before("org.third.testdata.user.SystemArchitecture_Aspect01.businessService()")
    public void before_BusinessService_Method_businessService() {
        System.out.println("SystemArchitectureAdvice_01===");
        System.out.println("    before_BusinessService_Method_businessService: 01");
    }

    @Before(value = "org.third.testdata.user.SystemArchitecture_Aspect01.businessService()")
    public void beforeWithParam_BusinessService_Method_businessService() {
        System.out.println("SystemArchitectureAdvice_01===");
        System.out.println("    before_BusinessService_Method_businessService: 02");
    }

    @AfterReturning("org.third.testdata.user.SystemArchitecture_Aspect01.businessService()")
    public void afterReturning_BusinessService_Method_businessService() {
        System.out.println("SystemArchitectureAdvice_01===");
        // ���غ�֪ͨͨ����һ��ƥ��ķ������ص�ʱ��ִ�С�ʹ�� @AfterReturning ע����������
        System.out.println("    afterReturning_BusinessService_Method_businessService");
    }

    @AfterReturning(pointcut = "org.third.testdata.user.SystemArchitecture_Aspect01.businessService()", returning = "returnedValue")
    public void afterReturningWithParam_BusinessService_Method_businessService(Object returnedValue) {
        System.out.println("SystemArchitectureAdvice_01===");
        ; // ���غ�֪ͨͨ����һ��ƥ��ķ������ص�ʱ��ִ�С�ʹ�� @AfterReturning
          // ע����������
        System.out.println("    afterReturningWithParam_BusinessService_Method_businessService  ");
        System.out.println(returnedValue);
    }

    /*
     * ����֪ͨʹ��@Aroundע����������֪ͨ�ĵ�һ������������ ProceedingJoinPoint���͡���֪ͨ���ڣ�����
     * ProceedingJoinPoint��proceed()�����ᵼ�� ��̨�����ӵ㷽��ִ�С�proceed ����Ҳ���ܻᱻ���ò��Ҵ���һ��
     * Object[]����-�������е�ֵ������Ϊ����ִ��ʱ�Ĳ�����
     * 
     * ������һ��Object[]�����ʱ�򣬴���ķ�����ͨ��AspectJ������������֪ͨ���в�ͬ��
     * ����ʹ�ô�ͳAspectJ����д�Ļ���֪ͨ��˵�������������������ʹ��ݸ�����֪ͨ�Ĳ�������ƥ��
     * �����Ǻ�̨�����ӵ���ܵĲ����������������ض�˳��Ĵ�����������˽�Ҫ�󶨸����ӵ��ԭʼֵ
     * ������㿴�������õ��ģ���Spring���õķ������Ӽ򵥲����ܸ���ƥ�������ڴ���proxy-based����ִ���﷨��
     * �����ʹ��AspectJ�ı������ͱ�֯��������ΪSpring��д��@AspectJ����ʹ����������ֻ��Ҫ֪����һ���𼴿ɡ�
     * ��һ�ַ�����������д��100%����Spring AOP��AspectJ�ı��ʽ�����ǽ����ں�����֪ͨ�������½�����������
     */
    @Around("org.third.testdata.user.SystemArchitecture_Aspect01.businessService()")
    public Object around_BusinessService_Method(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("SystemArchitectureAdvice_01==="); // ���һ��֪ͨ�ǻ���֪ͨ������֪ͨ��һ������ִ��֮ǰ��֮��ִ�С���ʹ��֪ͨ�л���
        // ��һ������ִ��֮ǰ��ִ��֮�����С����������Ծ������������ʲôʱ��ִ�У����ִ�У������Ƿ�ִ�С�
        // ����֪ͨ������ĳ�̰߳�ȫ�Ļ����£�����Ҫ��һ������ִ��֮ǰ��֮����ĳ��״̬��ʱ��ʹ�á�
        // �뾡��ʹ����򵥵������������֪ͨ������������򵥵�ǰ��֪ͨҲ�������õ�����²�Ҫʹ�û���֪ͨ����

        System.out.println("    around_BusinessService_Method_businessService");
        return joinPoint.proceed();
    }

    @AfterThrowing("org.third.testdata.user.SystemArchitecture_Aspect01.businessService()")
    public void afterThrowing_BusinessService_Method_businessService() {
        System.out.println("SystemArchitectureAdvice_01==="); // �׳��쳣֪ͨ��һ�������׳��쳣��ִ�С�ʹ��@AfterThrowingע����������
        System.out.println("    afterThrowing_BusinessService_Method_businessService");
    }

    @After("org.third.testdata.user.SystemArchitecture_Aspect01.businessService()")
    public void after_BusinessService_Method_businessService() {
        System.out.print("==="); // ����һ����������ν����ģ�����֪ͨ�������С�ʹ��@After
        // ע��������������֪ͨ����׼�������������غ��쳣�������������ͨ���������ͷ���Դ��
        System.out.println("    after_BusinessService_Method_businessService");
    }

    public int getOrder() {
        return 1;
    }

}
