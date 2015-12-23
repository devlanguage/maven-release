package org.third.spring.aop.aopstyle.config;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.DeclareParents;

@Aspect
public class IntroductionAdvice {

    // �������д���org.third.testdata.user.service�������ķ�������ʹ�����е�serviceӵ��һ������ʵ��DefaultUsageTracked
    @DeclareParents(value = "org.third.testdata.user.service.*+", defaultImpl = DefaultUsageTracked.class)
    public static UsageTracked mixin;

    @Before("org.third.testdata.user.SystemArchitecture_Aspect01.businessService() &&"
            + "this(usageTracked)")
    public void recordUsage(UsageTracked usageTracked) {

        usageTracked.incrementUseCount();
        System.out.println("==  " + usageTracked);
        // UsageTracked usageTracked1 = (UsageTracked) context.getBean("userService");
    }

    @Around("org.third.testdata.SystemArchitecture.businessService() && @annotation(org.third.testdata.service.Idempotent)")
    public Object doConcurrentOperation(ProceedingJoinPoint pjp) throws Throwable {

        // ...
        return null;
    }

}

@Retention(RetentionPolicy.RUNTIME)
@interface Idempotent {
    // marker annotation
}
