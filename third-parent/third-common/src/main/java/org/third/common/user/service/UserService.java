package org.third.common.user.service;

import org.third.common.user.domain.UserDM;

public interface UserService {

    public final static String BEAN_NAME_USER_SERVICE = "userService";

    public UserDM createUser(String userName);

    public void updateUser(UserDM User);

    public UserDM getUser(String UserName);
}
