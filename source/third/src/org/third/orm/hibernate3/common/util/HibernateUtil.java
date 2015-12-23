/**
 * Copyright reserved by Tellabs Communication Corp. LTD. The file
 * org.hibernate3.core.connection.HibernateUtil.java is created on 2008-3-26
 */
package org.third.orm.hibernate3.common.util;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static SessionFactory sessionFactory;
    static {
        try {
            // Create the SessionFactory
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (HibernateException ex) {
            throw new RuntimeException("Configuration problem: " + ex.getMessage(), ex);
        }
    }
    public static final ThreadLocal<Session> session = new ThreadLocal<Session>();

    public static Session currentSession() throws HibernateException {

        Session s = session.get();
        // Open a new Session, if this Thread has none yet
        if (s == null) {
            s = sessionFactory.openSession();
            session.set(s);
        }
        return s;
    }

    public static void closeSession() throws HibernateException {

        Session s = session.get();
        session.set(null);
        if (s != null) {
            s.close();
        }
    }

    // private static final SessionFactory sessionFactory;
    //
    // static {
    // try {
    // // Create the SessionFactory from hibernate.cfg.xml
    // sessionFactory = new Configuration().configure().buildSessionFactory();
    // } catch (Throwable ex) {
    // // Make sure you log the exception, as it might be swallowed
    // System.err.println("Initial SessionFactory creation failed." + ex);
    // throw new ExceptionInInitializerError(ex);
    // }
    // }
    //
    // public static SessionFactory getSessionFactory() {
    //
    // return sessionFactory;
    // }

}