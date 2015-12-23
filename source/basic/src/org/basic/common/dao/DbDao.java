package org.basic.common.dao;

import java.sql.Connection;
import java.sql.SQLException;

import org.basic.common.bean.DatabaseType;
import org.basic.common.util.BasicException;

public abstract class DbDao {

    public abstract void init() throws BasicException;

    public abstract Connection getConnection(DatabaseType dbType) throws SQLException;

    public abstract void close() throws BasicException;

}
