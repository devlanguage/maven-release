package org.third.orm.hibernate3.common.bean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import oracle.jdbc.pool.OracleConnectionPoolDataSource;
import oracle.jdbc.pool.OracleDataSource;

public class DatabaseFactory {

    private final static DatabaseFactory instance = new DatabaseFactory();
    private static OracleDataSource _dataSource = null;
    static {

        Database database = getDatabase(DatabaseType.ORACLE);

        try {
            DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
            _dataSource = new OracleConnectionPoolDataSource();
            _dataSource.setURL(database.getUrl());
            _dataSource.setUser(database.getUser());
            _dataSource.setPassword(database.getPassword());
            _dataSource.setDescription("JDBC DataSource Connection");
            // _dataSource.setMaxStatements(Integer.valueOf(config
            // .getValue(ConfigurationConstant.max_statement)));
            // _dataSource.setMaxLimit(Integer.valueOf(config
            // .getValue(ConfigurationConstant.max_connection)));
            // _dataSource.setMinLimit(Integer.valueOf(config
            // .getValue(ConfigurationConstant.min_connection)));
            _dataSource.setConnectionCacheName("test01");
            // _dataSource.setConnectionCachingEnabled(true);
        } catch (SQLException e) {
        }

    }

    public final static String getToday() {

        Connection conn = null;
        try {
            conn = new oracle.jdbc.OracleDriver().defaultConnection();
            // DriverManager.getConnection("jdbc:default:connection:");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT sysdate as today from dual");
            rs.next();
            return rs.getString("today");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return "2324";
    }

    public final static DatabaseFactory getInstance() {

        return instance;
    }

    public final static Database getDatabase(DatabaseType dbType) {

        Database database = null;
        switch (dbType) {
            case ORACLE:
                database = new OracleDatabase();
                break;

        }

        return database;
    }

    public final static Connection getConnection() throws SQLException {

        return _dataSource.getConnection();

    }

    public static void close(Connection conn) {

        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void close(Statement stmt) {

        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // public static void main(String[] args) {
    //
    // Connection conn =null;
    // try {
    // conn = getConnection();
    // System.err.println(conn);
    // } catch (SQLException e) {
    // e.printStackTrace();
    // } finally{
    // close(conn);
    // }
    // System.out.println();
    //
    // }

}
