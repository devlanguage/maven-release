package org.basic.net.c12_jdbc.common;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import javax.sql.DataSource;

public class DataSourceImpl implements DataSource {
    private ConnectionPool pool = new ConnectionPoolImpl2();

    public Connection getConnection() throws SQLException {
        return pool.getConnection();
    }

    public Connection getConnection(String username, String password) throws SQLException {
        throw new UnsupportedOperationException();
    }

    public int getLoginTimeout() throws SQLException {
        return DriverManager.getLoginTimeout();
    }

    public PrintWriter getLogWriter() throws SQLException {
        return DriverManager.getLogWriter();
    }

    public void setLoginTimeout(int seconds) throws SQLException {
        DriverManager.setLoginTimeout(seconds);
    }

    public void setLogWriter(PrintWriter out) throws SQLException {
        DriverManager.setLogWriter(out);
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }
}
