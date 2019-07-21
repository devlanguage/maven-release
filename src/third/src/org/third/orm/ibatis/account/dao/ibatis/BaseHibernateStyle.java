package org.third.orm.ibatis.account.dao.ibatis;

import com.ibatis.dao.client.DaoManager;
import com.ibatis.dao.client.template.HibernateDaoTemplate;

public class BaseHibernateStyle extends HibernateDaoTemplate implements BaseIbatisDao {

    public BaseHibernateStyle(DaoManager daoManager) {

        super(daoManager);
    }

}