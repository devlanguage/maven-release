package org.third.orm.ibatis.account.dao.ibatis.hibernate_style;

import org.third.orm.ibatis.account.dao.UserInfoDao;
import org.third.orm.ibatis.account.dao.ibatis.BaseHibernateStyle;
import org.third.orm.ibatis.account.domain.persistence.UserInfo;

import com.ibatis.dao.client.DaoManager;

public class UserInfoDaoImpl extends BaseHibernateStyle implements UserInfoDao {

    public UserInfoDaoImpl(DaoManager daoManager) {

        super(daoManager);
    }

    public UserInfo getUserInfo(Integer userId) {

        // return (UserInfo) queryForObject("getUserInfoByUserId", userId);
        return null;
    }

    public void insertUserInfo(UserInfo userInfo) {

        update("insertUserInfo", userInfo);
    }

    public void updateUserInfo(UserInfo userInfo) {

        update("insertUserInfo", userInfo);
    }
}