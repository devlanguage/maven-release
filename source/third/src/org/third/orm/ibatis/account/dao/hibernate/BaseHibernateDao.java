package org.third.orm.ibatis.account.dao.hibernate;

import org.hibernate.Session;
import org.third.orm.ibatis.account.dao.BaseDao;

public abstract class BaseHibernateDao implements BaseDao {

    protected org.hibernate.Session session;

    public BaseHibernateDao(Session session) {

        this.session = session;
    }

}