/**
 * Copyright reserved by Tellabs Communication Corp. LTD. The file
 * org.ibatis.account.access.BaseSqlMapDao.java is created on 2008-6-18
 */
package org.third.orm.ibatis.account.dao.ibatis;

import com.ibatis.dao.client.DaoManager;
import com.ibatis.dao.client.template.SqlMapDaoTemplate;

public class BaseSqlMapStyle extends SqlMapDaoTemplate implements BaseIbatisDao{

    protected static final int PAGE_SIZE = 4;

    public BaseSqlMapStyle(DaoManager daoManager) {

        super(daoManager);
    }
}