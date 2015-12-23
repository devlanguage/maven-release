package org.third.orm.ibatis.account.dao.ibatis.ibatis_style;

import org.third.orm.ibatis.account.dao.SequenceDao;
import org.third.orm.ibatis.account.dao.ibatis.BaseSqlMapStyle;

import com.ibatis.dao.client.DaoManager;

public class SequenceDaoImpl extends BaseSqlMapStyle implements SequenceDao {

    public SequenceDaoImpl(DaoManager daoManager) {

        super(daoManager);
    }

    public Integer getSequenceUserId() {

        return (Integer) queryForObject("getSequenceUserId", new Object());
    }
}