package org.third.spring.integration.dao.mybatis;

import org.third.testdata.user.domain.UserDM;

/**
 * <pre>
 *
 * </pre>
 */
public interface UserDao {
    public UserDM getUserById(UserDM user);
}