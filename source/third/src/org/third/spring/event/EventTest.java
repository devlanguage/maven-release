package org.third.spring.event;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.third.spring.aop.aopstyle.config.AopConfigStyleTester;
import org.third.testdata.user.service.UserService;

public class EventTest {

    public static void main(String[] args) {

        new EventTest().test();
    }

    public void test() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext(new String[] { "/org/springtest/event/spring_event.xml" });
        BeanFactory bf = ctx;

        UserService UserService = (UserService) ctx.getBean("userService");
        UserService.createUser("userName=test1");

    }
}
