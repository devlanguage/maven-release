package org.basic.net.c12_jdbc.common;
import java.sql.*;

public class ShowDB { 
  public static void main(String[] args)throws SQLException{  
    Connection con=new ConnectionProvider().getConnection();
    DatabaseMetaData metaData=con.getMetaData();
    System.out.println("允许的最大连接数为:"+metaData.getMaxConnections());
    System.out.println("一个连接允许同时打开的Statement对象的数目为:"+metaData.getMaxStatements());
  
    ResultSet tables=metaData.getTables(null,null,null,new String[]{"TABLE"});
    SQLExecutor.showResultSet(tables);
    con.close();
  }
}


