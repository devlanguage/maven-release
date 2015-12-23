package org.third.spring.integration.dao.mybatis;

import org.third.testdata.user.domain.UserDM;

/**
 * <pre>
 *
 * </pre>
 */
public class UserDaoImpl2 implements UserDao {
    private UserMapper userMapper;

    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public UserDM getUserById(UserDM user) {
        return userMapper.getUser(user.getId());
    }
}
