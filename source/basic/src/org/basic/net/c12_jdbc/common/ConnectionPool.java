package org.basic.net.c12_jdbc.common;
import java.sql.*;
public interface ConnectionPool{
  /** 从连接池中取出连接 */
  public Connection getConnection()throws SQLException; 
    
  /** 把连接返回连接池 */
  public void releaseConnection(Connection con)throws SQLException;

  /** 关闭连接池*/	
  public void close();
}









