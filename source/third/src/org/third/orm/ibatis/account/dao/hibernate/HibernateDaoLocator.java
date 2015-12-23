package org.third.orm.ibatis.account.dao.hibernate;

import org.hibernate.Session;
import org.third.orm.ibatis.account.dao.AccountDao;
import org.third.orm.ibatis.account.dao.AccountDaoLocator;
import org.third.orm.ibatis.account.dao.SequenceDao;
import org.third.orm.ibatis.account.dao.UserDao;
import org.third.orm.ibatis.account.dao.UserInfoDao;

public class HibernateDaoLocator extends AccountDaoLocator {

    @Override
    public AccountDao getAccountDao() {

        return null;
    }

    @Override
    public SequenceDao getSequenceDao() {

        return null;
    }

    @Override
    public UserDao getUserDao() {

        return null;
    }

    @Override
    public UserInfoDao getUserInfoDao() {

        return null;
    }

    public void setSession(Session session) {

    }
}
