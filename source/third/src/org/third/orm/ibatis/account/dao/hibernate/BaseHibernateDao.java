/**
 * Copyright reserved by Tellabs Communication Corp. LTD. The file
 * org.ibatis.account.access.BaseHibernateDao.java is created on 2008-6-20
 */
package org.third.orm.ibatis.account.dao.hibernate;

import org.hibernate.Session;
import org.third.orm.ibatis.account.dao.BaseDao;

public abstract class BaseHibernateDao implements BaseDao {

    protected org.hibernate.Session session;

    public BaseHibernateDao(Session session) {

        this.session = session;
    }

}