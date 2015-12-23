package org.third.orm.ibatis.account.dao.hibernate;

import org.hibernate.Session;

public class SequenceDaoImpl extends BaseHibernateDao{

    public SequenceDaoImpl(Session session) {

        super(session);
    }

    public Integer getSequenceUserId() {
//        return (Integer) queryForObject("getSequenceUserId", new Object());
        return (Integer) session.get(Integer.class, "df");
        
    }
}