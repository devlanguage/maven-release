package org.third.spring.integration.dao.hibernate;

import org.third.spring.integration.dao.UserDaoIntf;
import org.third.testdata.user.domain.UserDM;

public interface UserDao_Hibernate extends UserDaoIntf {

    // this.getHibernateTemplate();
    // this.getSessionFactory();
    // this.getSession();

    public final static String HQL_getAllUsers = "getAllUsers";
    public final static String HQL_getUserById = "getUserById";
    public final static String HQL_getUserByName = "getUserByName";

    public final static String HQL_getUserNameById = "getUserNameById";

    public void testTransaction(UserDM user);
}
