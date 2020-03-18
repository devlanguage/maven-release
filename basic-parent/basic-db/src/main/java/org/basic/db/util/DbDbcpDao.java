package org.basic.db.util;

import java.sql.Connection;
import java.sql.SQLException;

import org.basic.common.bean.DatabaseType;
import org.basic.common.util.BasicException;

public class DbDbcpDao extends DbDao {

    @Override
    public void init() throws BasicException {
        // TODO Auto-generated method stub

    }

    @Override
    public Connection getConnection(DatabaseType dbType) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void close() throws BasicException {
        // TODO Auto-generated method stub

    }

}
