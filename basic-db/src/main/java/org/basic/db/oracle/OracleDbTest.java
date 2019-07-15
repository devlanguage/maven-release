package org.basic.db.oracle;

import org.basic.common.bean.PoolType;
import org.basic.common.util.BasicException;
import org.basic.db.util.DbDao;
import org.basic.db.util.DbDaoFactory;

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
