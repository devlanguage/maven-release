package org.third.spring.integration.dao.transaction.declarative;

import org.third.spring.SpringTest;
import org.third.testdata.user.service.UserService;

public class DeclarativeTransactionTest extends SpringTest {

    public DeclarativeTransactionTest(String configFile) {
        super(configFile);
    }

    public static void main(String[] args) {

        new DeclarativeTransactionTest("spring_declarative_transaction.xml").test();
    }

    public void test() {
        UserService userService = (UserService) ctx.getBean(UserService.BEAN_NAME_USER_SERVICE);
        userService.createUser("ZhangSan");
    }

}
