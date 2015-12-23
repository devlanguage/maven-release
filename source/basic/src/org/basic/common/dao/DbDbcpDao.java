package org.basic.common.dao;

import java.sql.Connection;

import org.basic.common.bean.DatabaseType;
import org.basic.common.util.BasicException;

public class DbDbcpDao extends DbDao {

    @Override
    public Connection getConnection(DatabaseType dbType) {

        return null;
    }

    @Override
    public void init() {

        // TODO Auto-generated method stub

    }

    @Override
    public void close() throws BasicException {

        // TODO Auto-generated method stub

    }

}
