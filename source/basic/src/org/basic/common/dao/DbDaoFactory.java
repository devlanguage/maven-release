package org.basic.common.dao;

import java.sql.Connection;
import java.sql.SQLException;

import org.basic.common.bean.DatabaseType;
import org.basic.common.bean.PoolType;
import org.basic.common.util.BasicException;

public class DbDaoFactory {

    public final static DbDao createDbDao(PoolType poolType) throws BasicException {

        DbDao dbDao = null;
        switch (poolType) {
            case DBCP:
                dbDao = new DbDbcpDao();
                break;
            default:
                dbDao = new DbProxoolDao();
                break;

        }
        dbDao.init();
        return dbDao;
    }

    public static void main(String[] args) {

        try {
            Connection conn = DbDaoFactory.createDbDao(PoolType.PROXOOL).getConnection(DatabaseType.ORACLE);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (BasicException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
