package org.third.spring.basic;

import org.basic.common.util.SystemUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.third.testdata.db.bean.DbObject;

/**
 * Created on Apr 9, 2014, 4:41:31 PM
 */
public class SpringBasicTester {
    private static final String PKG_NAME = SpringBasicTester.class.getPackage().getName();
    private static final String PKG_PATH = PKG_NAME.replaceAll("\\.", "/");

    public static void main(String[] args) {
        ApplicationContext ct = new ClassPathXmlApplicationContext(PKG_PATH + "/spring_basic_config.xml");
        SystemUtil.printMessage("===basic bean load====");
        org.third.testdata.user.domain.UserDM u = (org.third.testdata.user.domain.UserDM) ct.getBean("user");
        System.out.println(u);

        SystemUtil.printMessage("===Properties file load====");
        System.out.println((DbObject) ct.getBean("mysql"));
        System.out.println((DbObject) ct.getBean("mysql"));
        System.out.println((DbObject) ct.getBean("oracle"));
        System.out.println((DbObject) ct.getBean("oracle"));
    }
}
