package org.third.orm.ibatis.account.dao.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.third.orm.ibatis.account.dao.AccountDaoLocator;
import org.third.orm.ibatis.account.dao.DaoConfiguration;
import org.third.orm.ibatis.account.dao.DaoLoader;

public class HibernateDaoLoader extends DaoLoader {

    @Override
    public AccountDaoLocator getDaoLocator() {

        return null;
    }
    @Override
    public void init(DaoConfiguration daoConfig, AccountDaoLocator locator) {

        HibernateDaoLocator daoLocator = (HibernateDaoLocator) locator;
        System.out.println("Init the Hibernate DAO Implementation in ibatis style dao");
        System.out.println("Hibernate Dao Configuration: " + daoConfig.getConfigFile());
        try {
            org.hibernate.cfg.Configuration conf = Configuration.class.newInstance();
            conf.configure(daoConfig.getConfigFile());
            SessionFactory sf = conf.buildSessionFactory();
            daoLocator.setSession(sf.openSession());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
