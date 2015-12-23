package org.third.orm.ibatis.account.dao.hibernate;

import org.hibernate.Session;
import org.third.orm.ibatis.account.domain.persistence.UserInfo;

public class UserInfoDaoImpl extends BaseHibernateDao {

    public UserInfoDaoImpl(Session session) {

        super(session);
    }

    public UserInfo getUserInfo(Integer userId) {

        // return (UserInfo) queryForObject("getUserInfoByUserId", userId);
        return null;
    }

    public void insertUserInfo(UserInfo userInfo) {

        // update("insertUserInfo", userInfo);
    }

    public void updateUserInfo(UserInfo userInfo) {

        // update("insertUserInfo", userInfo);
    }
}