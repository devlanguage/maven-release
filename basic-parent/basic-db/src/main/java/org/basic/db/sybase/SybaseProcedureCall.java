package org.basic.db.sybase;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.basic.common.util.SystemUtils;

/**
 * Created on Feb 11, 2014, 4:45:20 PM
 */
public class SybaseProcedureCall {
  public static void main(String[] args) {
    Connection conn = null;
    try {
      conn = SybaseDbManager.getInstance().getConnection();
      // testTempTable(conn);
      testPaginating(conn);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } finally {
      SystemUtils.closeQuitely(conn);
    }
  }

  /**
   * <pre>
   * if exists (select 1 from sysobjects where name='js_tnbi_sp_query_by_page' and type='P')
   *   drop procedure js_tnbi_sp_query_by_page
   * go
   * create procedure js_tnbi_sp_query_by_page
   *   @v_query_sql varchar(16384), @v_start int, @v_end int, @v_pk  varchar(32)
   * WITH RECOMPILE
   * AS
   * BEGIN
   *   declare @v_exec_sql varchar(16384)  
   *   declare @v_exec_sqltmp varchar(16384)  
   *   declare @dt varchar(10) --生成临时表的随机数  
   *   set @dt=substring(convert(varchar, rand()), 3, 10)   --一个字符型的随机数  
   *   set rowcount @v_end     
   *  
   *   if(@v_pk is null)  
   *   begin      
   *     set @v_exec_sql = stuff(@v_query_sql,charindex('select',@v_query_sql),6,'select number(*) as sybid,')             
   *     set @v_exec_sqltmp = ' select * from #temptable' + @dt + ' where sybid>' || convert(varchar,@v_start) || ' and sybid <= ' || convert(varchar,(@v_start/@v_end+1)*@v_end)    
   *   end  
   *   else  
   *   begin  
   *     set @v_exec_sql = stuff(@v_query_sql,charindex('select',@v_query_sql),6,'select number(*) as sybid,' || @v_pk || ' ,@' )            
   *     set @v_exec_sql =  stuff(@v_exec_sql,charindex(',@',@v_exec_sql),charindex('from',@v_exec_sql)-charindex(',@',@v_exec_sql),'' )            
   *     set @v_exec_sqltmp = ' select '|| @v_pk ||' from #temptable' + @dt + ' where sybid>' || convert(varchar,@v_start) || ' and sybid <= ' || convert(varchar,(@v_start/@v_end+1)*@v_end)    
   *     set @v_exec_sqltmp = stuff(@v_query_sql,charindex('where',@v_query_sql),5,' where '|| @v_pk || ' in ('|| @v_exec_sqltmp ||') and ')     
   *   end  
   *   set @v_exec_sql = stuff(@v_exec_sql, charindex('from',@v_exec_sql),4,'into #temptable' + @dt + ' from')  
   *   select (@v_exec_sql) as sql, @v_exec_sqltmp as sqlTmp  
   *   set rowcount 0  
   * END
   * go
   * js_tnbi_sp_query_by_page 'select * from fault', 1, 10, 'fltid'
   * go
   * </pre>
   * 
   * @param conn
   * @throws SQLException
   * 
   * 
   * 
   * 
   */
  private static void testPaginating(Connection conn) throws SQLException {
    CallableStatement stmt = conn.prepareCall("{ call js_tnbi_sp_query_by_page ?, 1, 10, 'fltid'}");
    stmt.setString(1, "select * from fault");
    boolean result = stmt.execute();
    ResultSet rs = stmt.getResultSet();
    while (rs.next()) {
      String paginatingSQL = rs.getString(1);
      System.out.println(paginatingSQL);
      // select number(*) as sybid,fltid into #temptable8076691831 from fault
      ResultSet rs2 = stmt.executeQuery(paginatingSQL);
      stmt.close();
      
      printResultSet(rs2);
    }

  }

  /**
   * <pre>
   * if exists (select 1 from sysobjects where name='js_tnbi_sp_query_snc' and type='P')
   *   drop procedure js_tnbi_sp_query_snc
   * go
   * create procedure js_tnbi_sp_query_snc
   *   @p_snc_id INT
   * WITH RECOMPILE
   * as
   * SET NOCOUNT ON
   *   declare @indextable table(id int identity(1,1),nid int)  
   * BEGIN  
   *   SELECT 123 id, 'zhangsan' name INTO #t1
   *   SELECT * FROM #t1
   *   SELECT 456 id, 'lisan' name, 4344 age FROM #t1
   *   SELECT 789 id, 'wangwu' name, 5344 age FROM #t1
   * END
   * SET NOCOUNT OFF
   * go
   * execute js_tnbi_sp_query_snc 1
   * go
   * </pre>
   * 
   * @param conn
   * @throws SQLException
   */
  private static void testTempTable(Connection conn) throws SQLException {
    CallableStatement stmt = conn.prepareCall("{ call js_tnbi_sp_query_snc ?}");
    stmt.setInt(1, 12);
    boolean result = stmt.execute();
    boolean first = true;
    while (first || stmt.getMoreResults()) {
      if (!first) {
        System.out.println();
      }
      first = false;
      ResultSet rs = stmt.getResultSet();
      printResultSet(rs);
    }
  }

  public final static void printResultSet(ResultSet rs) throws SQLException {
    if (null != rs) {
      ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
      for (int i = 1, length = rsmd.getColumnCount(); i <= length; ++i) {
        System.out.print(rsmd.getColumnName(i));
        System.out.print("\t");
      }
      System.out.println("\n---------------------------");
      while (rs.next()) {
        for (int i = 1, length = rsmd.getColumnCount(); i <= length; ++i) {
          System.out.print(rs.getObject(i));
          System.out.print("\t");
        }
        System.out.println();
      }
    }
  }
}
