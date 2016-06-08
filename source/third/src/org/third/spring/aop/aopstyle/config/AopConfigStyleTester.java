package org.third.spring.aop.aopstyle.config;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.third.testdata.user.service.UserService;


public class AopConfigStyleTester {

    public static void main(String[] args) {

        new AopConfigStyleTester().test();
    }

    public void test() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext(
                new String[] { "/org/third/spring/aop/aopstyle/config/spring_aop.xml" });
        BeanFactory bf = ctx;

        UserService UserService = (UserService) ctx.getBean("userService");
        UserService.createUser("userTest1");
    }
}
