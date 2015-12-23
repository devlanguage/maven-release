package org.third.testdata.user.service;

import org.third.testdata.user.domain.UserDM;

public interface UserService {

    public final static String BEAN_NAME_USER_SERVICE = "userService";

    public UserDM createUser(String userName);

    public void updateUser(UserDM User);

    public UserDM getUser(String UserName);
}
