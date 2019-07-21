package org.basic.db.paging.sybase.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 2013-4-19
 * 
 * @author wangmeng
 * 
 */
public class JDBCUtil {
  static Properties config;
  private static int countConn;

  private static Properties getConfig() {
    if (config != null) {
      return config;
    }
    config = new Properties();
    try {
      config.load(JDBCUtil.class.getResourceAsStream("database-config.properties"));
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return config;
  }

  private static String getUrl() {
    return getConfig().getProperty("jdbc.url");
  }

  private static String getDriver() {
    return getConfig().getProperty("jdbc.driver");
  }

  private static String getUsername() {
    return getConfig().getProperty("jdbc.username");
  }

  private static String getPassword() {
    return getConfig().getProperty("jdbc.password");
  }

  public synchronized static Connection getConnection() throws Exception {
    Connection conn = null;
    Class.forName(getDriver());
    conn = DriverManager.getConnection(getUrl(), getUsername(), getPassword());
    countConn++;
    System.out.println("当前JDBC连接数：" + countConn);
    return conn;
  }

  public synchronized static void closeConnection(Connection conn) throws SQLException {
    if (conn == null || conn.isClosed()) {
      return;
    }
    conn.close();
    countConn--;
    System.out.println("当前JDBC连接数：" + countConn);
    System.gc();
  }

  public synchronized static void closeConnectionByThread(final Connection conn) {
    new Thread() {
      public void run() {
        try {
          if (conn == null || conn.isClosed()) {
            return;
          }
          conn.close();
          countConn--;
          System.out.println("当前JDBC连接数：" + countConn);
          System.gc();
        } catch (SQLException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      };
    }.start();
  }

  public static void main(String[] args) {
  }
}
