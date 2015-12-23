package org.third.spring.integration.dao;

import java.util.List;

import org.third.testdata.user.domain.UserDM;

public interface UserDaoIntf {

    public final static String BEAN_NAME_userDao01 = "userDao01";
    public final static String BEAN_NAME_userDao02 = "userDao02";
    public final static String BEAN_NAME_userDao03 = "userDao03";
    public final static String BEAN_NAME_userDao04 = "userDao04";

    public List<UserDM> getAllUsers();

    public UserDM getUserById(int id);
}
