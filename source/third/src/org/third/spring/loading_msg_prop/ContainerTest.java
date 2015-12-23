package org.third.spring.loading_msg_prop;

import java.util.Locale;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.third.testdata.db.bean.DbObject;

public class ContainerTest {

    public static void main(String[] args) {

        new ContainerTest().test();
    }

    public void test() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext(
                new String[] { "/org/springtest/loading_msg_prop/spring_container.xml" });
        BeanFactory bf = ctx;

        DbObject dbObject = (DbObject) ctx.getBean("dbObject");
        System.out.println(dbObject);

        System.out.println();
        // resource_exception.properties
        // resource.exception=hello, param1={0}, param2={1}
        String msg = ctx.getMessage("resource.exception", new Object[] { "param1", "param2" }, "default_msg_value",
                Locale.getDefault());
        System.out.println(msg);
    }
}
