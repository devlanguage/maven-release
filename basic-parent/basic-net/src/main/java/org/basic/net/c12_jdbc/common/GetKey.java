package org.basic.net.c12_jdbc.common;
import java.sql.*;
public class GetKey{
  public static void main(String args[])throws Exception{
    Connection con=new ConnectionProvider().getConnection();
    Statement stmt = con.createStatement();
    
    //增加新记录
    stmt.executeUpdate("insert into CUSTOMERS (NAME,AGE,ADDRESS) " 
       +"VALUES ('小王',20,'上海')", Statement.RETURN_GENERATED_KEYS);
    ResultSet rs=stmt.getGeneratedKeys();
    //输出查询结果
    if (rs.next()){
      System.out.println("id="+rs.getInt(1));
    }

    //释放相关资源
    rs.close();
    stmt.close();
    con.close();
  }
}


