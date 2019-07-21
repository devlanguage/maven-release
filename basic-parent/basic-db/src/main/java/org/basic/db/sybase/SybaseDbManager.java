package org.basic.db.sybase;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SybaseDbManager {

  private static SybaseDbManager instance;

  private SybaseDbManager() {
    //
    initConnectionPool();
  }

  private void initConnectionPool() {
    try {
    	String driverClass="com.sybase.jdbcx.SybDriver";
//      driver = new com.sybase.jdbc.SybDriver();//.For jConnect 4.1:
//      driver = new com.sybase.jdbc2.jdbc.SybDriver();//.For jConnect 5.0:
//      driver = new com.sybase.jdbc3.jdbc.SybDriver();//.For jConnect 6.0:
//      driver = new com.sybase.jdbc4.jdbc.SybDriver();//.For jConnect 7.0:
      
//      com.sybase.jdbcx.Debug debug = driver.getDebug();
//      
//      debug.debug(true, "MyClass");
//      debug.debug(true, "MyClass:YourClass");
//      debug.debug(true, "STATIC:MyClass");
//      debug.debug(true, "ALL", System.out); //DriverManager.setLogStream( )

      DriverManager.registerDriver((Driver) Class.forName(driverClass).newInstance());
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  public Connection getConnection() throws SQLException {
    // TODO, it will be replace by connection pool. take out connection from connectionPool
    // return DriverManager.getConnection("jdbc:sybase:Tds:CNSHLAB1252:2048/devdb7", "sa", "sa1234");
    // return DriverManager.getConnection("jdbc:sybase:Tds:CNSHLAB1252:2048/gpdb3", "sa", "sa1234");
    return DriverManager.getConnection("jdbc:sybase:Tds:VESNA3:2048/ygong", "sa", "sa1234");
    // return DriverManager.getConnection(
    // "jdbc:sybase:Tds:" + ConfigureHandler.getInstance().getDbHost() + ":"
    // + ConfigureHandler.getInstance().getDbServerPort() + "/"
    // + ConfigureHandler.getInstance().getDatabase(), ConfigureHandler.getInstance().getDbUser(),
    // ConfigureHandler.getInstance().getDbPswd());
  }

  public void releaseConnection(Connection conn) {
    // TOOD. it depends on db connection driver type, call related method to release resource.
    if (conn != null) {
      try {
        conn.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  public final static SybaseDbManager getInstance() {
    if (instance == null) {
      synchronized (SybaseDbManager.class) {
        if (instance == null) {
          instance = new SybaseDbManager();
        }
      }
    }
    return instance;
  }

}
