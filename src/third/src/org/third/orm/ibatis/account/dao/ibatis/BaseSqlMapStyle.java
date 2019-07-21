package org.third.orm.ibatis.account.dao.ibatis;

import com.ibatis.dao.client.DaoManager;
import com.ibatis.dao.client.template.SqlMapDaoTemplate;

public class BaseSqlMapStyle extends SqlMapDaoTemplate implements BaseIbatisDao{

    protected static final int PAGE_SIZE = 4;

    public BaseSqlMapStyle(DaoManager daoManager) {

        super(daoManager);
    }
}