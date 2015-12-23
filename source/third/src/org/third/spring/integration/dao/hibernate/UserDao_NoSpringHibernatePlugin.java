package org.third.spring.integration.dao.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.third.testdata.user.domain.UserDM;

public class UserDao_NoSpringHibernatePlugin implements UserDao_Hibernate {

    private org.hibernate.SessionFactory sessionFactory;

    public org.hibernate.SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(org.hibernate.SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void testTransaction(UserDM user) {

    }

    public List<UserDM> getAllUsers() {
        List<UserDM> userList = null;
        Session s = sessionFactory.openSession();
        org.hibernate.Query query = s.getNamedQuery("getAllUsers");
        userList = query.list();
        s.close();

        return userList;
    }

    public UserDM getUserById(int id) {
        UserDM u = null;
        Session s = sessionFactory.openSession();
        u = (UserDM) s.get(UserDM.class, id);

        Query getUserNameById = s.getNamedQuery(HQL_getUserById);
        getUserNameById.setParameter("id", 1);
        UserDM u1 = (UserDM) getUserNameById.uniqueResult();
        System.err.println(u1);

        s.close();

        return u;
    }

}
