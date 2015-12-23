package org.third.spring.config;

import java.util.Locale;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.third.spring.config.bean_wire.Student;
import org.third.spring.config.bean_wire.StudentManager;

public class ApplicationContextTest {

    public static void main(String[] args) {

        new ApplicationContextTest().test();
    }

    public void test() {

        ApplicationContext ctx = new ClassPathXmlApplicationContext(ApplicationContextTest.class.getPackage().getName()
                .replaceAll("\\.", "/")
                + "/spring_beans.xml");
        BeanFactory bf = ctx;
        PersonManager pm = (PersonManager) ctx.getBean("personManager");
        System.out.println(pm);

        Person p1 = (Person) ctx.getBean("p1");
        System.out.println(p1);

        Person p3 = (Person) ctx.getBean("p1");
        System.out.println(p3);

        Person p2 = (Person) ctx.getBean("p4");
        System.out.println(p2);

        Person stu = (Person) ctx.getBean("student");
        System.out.println(stu);

        // System.out.println(ctx.getBean("mysql"));

        String msg = ctx.getMessage("resource.exception", new Object[] { "param1", "param2" }, "default_msg_value", Locale.getDefault());
        System.out.println(msg);

        Resource template = ctx.getResource("org/springtest/config/myTemplate.txt");
        System.out.println(template);
        System.out.println(template.getFilename());
        Resource template1 = ctx.getResource("classpath:some/resource/path/myTemplate.txt");

        Resource template2 = ctx.getResource("file:/some/resource/path/myTemplate.txt");
        Resource template3 = ctx.getResource("http://myhost.com/resource/path/myTemplate.txt");
        
        //////
        

    }
}
