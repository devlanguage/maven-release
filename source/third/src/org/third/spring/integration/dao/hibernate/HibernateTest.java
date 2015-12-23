package org.third.spring.integration.dao.hibernate;

import java.util.List;

import org.third.spring.SpringTest;
import org.third.spring.integration.dao.UserDaoIntf;
import org.third.testdata.user.domain.UserDM;

public class HibernateTest extends SpringTest {

    public HibernateTest(String configFile) {
        super(configFile);
    }

    public static void main(String[] args) {

        new HibernateTest("spring_hibernate.xml").test();
    }

    /**
     * 
     */
    public void test() {

        UserDao_NoSpringHibernatePlugin userDao01 = (UserDao_NoSpringHibernatePlugin) ctx
                .getBean(UserDaoIntf.BEAN_NAME_userDao01);
        UserDao_HibernateTemplate userDao02 = (UserDao_HibernateTemplate) ctx.getBean(UserDaoIntf.BEAN_NAME_userDao02);
        UserDao_HibernateDaoSupport userDao03 = (UserDao_HibernateDaoSupport) ctx
                .getBean(UserDaoIntf.BEAN_NAME_userDao03);

        System.out.println("\n UserDao Implementation without spring hibernate plugin");
        UserDM u01 = userDao01.getUserById(1);
        System.out.println(u01);

        List<UserDM> users01 = userDao01.getAllUsers();
        System.out.println(users01);

        System.out.println("\n UserDao HibernateTemplate Implementation without spring hibernate plugin");
        UserDM u11 = userDao02.getUserById(1);
        System.out.println(u11);
        System.out.println(userDao02.getUsesByName("Test"));
        ;

        System.out.println("\n UserDao HibernateTemplate Implementation from HibernateDaoSupport");
        UserDM u21 = userDao03.getUserById(1);
        System.out.println(u21);

        UserDM testUser = new UserDM(10, "ZhangSan", "pwd01");
        System.out.println("\n UserServcie: test the Programmatic Transaction");
        UserService_ProgrammaticTransaction userService01 = (UserService_ProgrammaticTransaction) ctx
                .getBean(UserService.BEAN_NAME_userService01);
        userService01.createUser(testUser);

        System.out.println("\n UserServcie: test the Declarative Transaction");
        UserService_DeclarativeTransaction userService02 = (UserService_DeclarativeTransaction) ctx
                .getBean(UserService.BEAN_NAME_userService02);
        userService02.createUser(testUser);
    }
}