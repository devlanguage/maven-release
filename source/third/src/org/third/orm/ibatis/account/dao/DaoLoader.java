package org.third.orm.ibatis.account.dao;

public abstract class DaoLoader {

    protected AccountDaoLocator accountDaoLocator;
    /**
     * @return get method for the field daoLocator
     */
    public abstract AccountDaoLocator getDaoLocator();

    public abstract void init(DaoConfiguration daoConfig, AccountDaoLocator locator);
}
