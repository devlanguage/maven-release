/**
 * Copyright reserved by Tellabs Communication Corp. LTD. The file
 * org.ibatis.account.access.impl.UserInfoDaoImpl.java is created on 2008-6-18
 */
package org.third.orm.ibatis.account.dao.ibatis.ibatis_style;

import org.third.orm.ibatis.account.dao.UserInfoDao;
import org.third.orm.ibatis.account.dao.ibatis.BaseSqlMapStyle;
import org.third.orm.ibatis.account.domain.persistence.UserInfo;

import com.ibatis.dao.client.DaoManager;

public class UserInfoDaoImpl extends BaseSqlMapStyle implements UserInfoDao {

    public UserInfoDaoImpl(DaoManager daoManager) {

        super(daoManager);
    }

    public UserInfo getUserInfo(Integer userId) {

        return (UserInfo) queryForObject("getUserInfoByUserId", userId);
    }

    public void insertUserInfo(UserInfo userInfo) {

        update("insertUserInfo", userInfo);
    }

    public void updateUserInfo(UserInfo userInfo) {

        update("insertUserInfo", userInfo);
    }
}