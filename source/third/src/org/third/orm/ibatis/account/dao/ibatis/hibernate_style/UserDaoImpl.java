package org.third.orm.ibatis.account.dao.ibatis.hibernate_style;

import java.util.List;

import org.apache.log4j.Logger;
import org.third.orm.ibatis.account.dao.UserDao;
import org.third.orm.ibatis.account.dao.ibatis.BaseSqlMapStyle;
import org.third.orm.ibatis.account.domain.persistence.User;

import com.ibatis.dao.client.DaoManager;

public class UserDaoImpl extends BaseSqlMapStyle implements UserDao {

    static Logger logger = Logger.getLogger(UserDaoImpl.class);

    public UserDaoImpl(DaoManager daoManager) {

        super(daoManager);
        logger.debug("In UserSqlMapDao daoManager " + (daoManager));
    }

    public List getUser(String userAlias) {

        return queryForList("getUserByUserAlias", userAlias);
    }

    public List getUser(String userAlias, String password) {

        User user = new User(userAlias, password);
        return queryForList("getUserByUserAliasAndPassword", user);
    }

    public void insertUser(User user) {

        update("insertUser", user);
    }

    public void updateUser(User user) {

        update("updateUser", user);
    }

    public List getUserByEmail(String email) {

        return queryForList("getUserByEmail", email);
    }

    public boolean existUserId(Integer userid) {

        Integer cnt = (Integer) this.queryForObject("existUserId", userid);
        return cnt.intValue() > 0;
    }

    public List<User> listUsers() {

        List queryForList = this.queryForList("listUsers");
        return queryForList;
    }
}