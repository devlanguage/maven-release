package org.basic.db.oracle;

import org.basic.common.bean.PoolType;
import org.basic.common.dao.DbDao;
import org.basic.common.dao.DbDaoFactory;
import org.basic.common.util.BasicException;

public abstract class OracleDbTest {

    static DbDao dbDao = null;
    {
        try {
            dbDao = DbDaoFactory.createDbDao(PoolType.PROXOOL);
        } catch (BasicException e) {
            e.printStackTrace();
        }
    }
    public abstract void startTest();
}
