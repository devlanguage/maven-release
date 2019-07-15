package org.basic.db.oracle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author ygong
 * 
 */
public class OracleConnectionPool {

    // private Connection _dataSource;
    private final static String DB_DRIVER = "oracle.jdbc.OracleDriver";
    private final static String DB_URL = "jdbc:oracle:thin:@localhost:1521:NGX5500A";
    private final static String DB_USER = "ygong1";
    private final static String DB_PASSWORD = "oracle";

    private final static int DB_MAX_STATMENTS = 10;
    private final static int DB_MAX_LIMIT = 10;
    private final static int DB_MIN_LIMIT = 5;

    private static OracleConnectionPool instance;
//    private static OracleConnectionCacheImpl _dataSource;

    private OracleConnectionPool() {

        try {
            init();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void init() throws SQLException {

        /**
         * Used to get connection in Java Source
         * 
         * conn = new oracle.jdbc.OracleDriver().defaultConnection();
         * DriverManager.getConnection("jdbc:default:connection:");
         */
        DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
//        OracleConnectionCacheImpl     _dataSource = new OracleConnectionCacheImpl();
//        _dataSource.setURL(DB_URL);
//        _dataSource.setUser(DB_USER);
//        _dataSource.setPassword(DB_PASSWORD);
//        _dataSource.setDescription("JDBC DataSource Connection");
//        _dataSource.setMaxStatements(DB_MAX_STATMENTS);
//        _dataSource.setMaxLimit(DB_MAX_LIMIT);
//        _dataSource.setMinLimit(DB_MIN_LIMIT);
//        _dataSource.setCacheScheme(OracleConnectionCacheImpl.DYNAMIC_SCHEME);

    }

    public synchronized final static OracleConnectionPool getInstance() {

        if (instance == null) {
            instance = new OracleConnectionPool();
        }
        return instance;
    }

    public final Connection getConnection() throws SQLException {

         return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    public final static void close() {
        try {
//            _dataSource.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final static void closeConnectionSliently(Connection conn) {

        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void closeStatmentSliently(Statement stmt) {

        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void closeResultSetSliently(ResultSet rs) {

        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
