/**
 * Copyright reserved by Tellabs Communication Corp. LTD. The file
 * org.ibatis.account.access.UserDao.java is created on 2008-6-18
 */
package org.third.orm.ibatis.account.dao.hibernate;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.third.orm.ibatis.account.domain.persistence.User;

public class UserDaoImpl extends BaseHibernateDao {

    static Logger logger = Logger.getLogger(UserDaoImpl.class);

    public UserDaoImpl(Session session) {

        super(session);
        logger.debug("In UserSqlMapDao daoManager " + (session));
    }

    public List getUser(String userAlias) {

        // return queryForList("getUserByUserAlias", userAlias);
        return null;
    }

    public List getUser(String userAlias, String password) {

        // User user = new User(userAlias, password);
        // return queryForList("getUserByUserAliasAndPassword", user);
        return null;
    }

    public void insertUser(User user) {

        // update("insertUser", user);
    }

    public void updateUser(User user) {

        // update("updateUser", user);
    }

    public List getUserByEmail(String email) {

        return null;
        // return queryForList("getUserByEmail", email);
    }

    public boolean existUserId(Integer userid) {

        // Integer cnt = (Integer) this.queryForObject("existUserId", userid);
        // return cnt.intValue() > 0;
        return true;
    }

    public List<User> listUsers() {

        // List queryForList = this.queryForList("listUsers");
        // return queryForList;
        return null;
    }
}