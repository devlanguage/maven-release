/**
 * Copyright reserved by Tellabs Communication Corp. LTD. The file
 * org.ibatis.account.access.UserInfoDao.java is created on 2008-6-18
 */
package org.third.orm.ibatis.account.dao;

import org.third.orm.ibatis.account.domain.persistence.UserInfo;

public interface UserInfoDao extends BaseDao {

    public abstract UserInfo getUserInfo(Integer userId);

    public abstract void insertUserInfo(UserInfo userInfo);

    public abstract void updateUserInfo(UserInfo userInfo);
}