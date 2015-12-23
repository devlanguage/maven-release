package org.third.spring.integration.dao.hibernate;

import org.third.testdata.user.domain.UserDM;

public interface UserService {

    public final static String BEAN_NAME_userService01 = "userService01";
    public final static String BEAN_NAME_userService02 = "userService02";

    void createUser(UserDM user);
}
