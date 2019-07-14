package org.basic.db.jdbc4.newType;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;

import javax.sql.RowSet;

import org.basic.db.PropertiesTest;

/**
 * <pre>
 * javax.sql.RowSet rs1; //默认情况下，所有 RowSet 对象都是可滚动的和可更新的,再调用execute的时候，RowSet才建立到DB的connection, PrepareedStatmenet, ResultSet
 *     javax.sql.rowset.JdbcRowSet jdbcRs1; //Always connected to Database
 *     javax.sql.rowset.CachedRowSet cachedRs1;
 *         javax.sql.rowset.WebRowSet webRs1;
 *             javax.sql.rowset.FilteredRowSet filteredRs1;
 *             javax.sql.rowset.JoinRowSet joinRs1;
 * </pre>
 * 
 * @author ygong
 * 
 */
public class RowsetTest {

    public final static String DB_URL = "jdbc:oracle:thin:@karewit:1521:nm";
    public final static String DB_USER = "ygong1";
    public final static String DB_PWD = "tellabs";
    public final static void listRegisteredDrivers(){
//        sun.jdbc.odbc.JdbcOdbcDriver@a59698
//        oracle.jdbc.driver.OracleDriver@dbe178
//        com.mysql.jdbc.Driver@1571886
//        org.apache.derby.jdbc.ClientDriver@89fbe3
//        org.apache.derby.jdbc.AutoloadedDriver@1404536
        for (Enumeration<Driver> registerdDrivers = DriverManager.getDrivers(); registerdDrivers.hasMoreElements();) {
            java.sql.Driver driver = registerdDrivers.nextElement();
            System.out.println(driver);
        }
    }
    public final static void testResultSet(){
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PWD);
            conn.setAutoCommit(false);
            
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery("select user_id, user_name, deleted,creator from TEST_USER");
            PropertiesTest.printResultSet(rs);
            
            rs.absolute(3);//RowIndex: 1, 2, 3...
            rs.updateString(2, "Test33"); //ColumnIndex: 1, 2,...
            rs.updateRow();
            

            rs.moveToInsertRow();
            rs.updateString(1, "1234");
            rs.updateString("user_name", "test2");
            rs.insertRow();//insert row located in first row
            rs.moveToCurrentRow();
            
            rs.afterLast();
            rs.beforeFirst();
            rs.first();
            rs.last();
            rs.next();
            rs.previous();
            rs.deleteRow();//insert row located in first row
            
            
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public final static void testJdbcRowSet(){
        javax.sql.RowSet rs1; //默认情况下，所有 RowSet 对象都是可滚动的和可更新的,再调用execute的时候，RowSet才建立到DB的connection, PrepareedStatmenet, ResultSet
        javax.sql.rowset.JdbcRowSet jdbcRowSet1=null; //Always connected to Database
        javax.sql.rowset.CachedRowSet cachedRowSet1=null;
            javax.sql.rowset.WebRowSet webRowSet1=null;
                javax.sql.rowset.FilteredRowSet filteredRowSet1=null;
                javax.sql.rowset.JoinRowSet joinRowSet1=null;
        try {
            jdbcRowSet1 = new BasicJdbcRowSet(){
                public void updateString(int i, String s)
                throws SQLException
            {
                setType(RowSet.TYPE_FORWARD_ONLY);//1003
                setType(RowSet.TYPE_SCROLL_INSENSITIVE);//1004
                setType(RowSet.TYPE_SCROLL_SENSITIVE);//1005
                setConcurrency(RowSet.CONCUR_READ_ONLY);//1007
                System.out.println(getConcurrency());
                setConcurrency(RowSet.CONCUR_UPDATABLE);  //1008              
                System.out.println(this.getType());
                System.out.println(this.getConcurrency());
                
//                this.getResultSet().updateString(i, s);
            }
            };
            //Placeholder从1开始            
            jdbcRowSet1.setUrl(DB_URL);// jdbcRowSet1.setDataSourceName("DB_JNDI_NAME");
            jdbcRowSet1.setUsername(DB_USER);
            jdbcRowSet1.setPassword(DB_PWD);
            
//            jdbcRowSet1.setCommand("select * from user_tables where logging = ?");
//            jdbcRowSet1.setString(1, "YES");
//            jdbcRowSet1.execute();
//            PropertiesTest.printResultSet(jdbcRowSet1);
            
            jdbcRowSet1.setCommand("select * from TEST_USER");            
            jdbcRowSet1.setEscapeProcessing(true);//process the escape character
            jdbcRowSet1.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
            jdbcRowSet1.setReadOnly(false);
            jdbcRowSet1.execute();            
            PropertiesTest.printResultSet(jdbcRowSet1);
            
            jdbcRowSet1.absolute(3);
            jdbcRowSet1.updateString(1,"Test1");
            jdbcRowSet1.updateRow();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (jdbcRowSet1 != null)
                    jdbcRowSet1.close();
                if (cachedRowSet1 != null)
                    cachedRowSet1.close();
                if (webRowSet1 != null)
                    webRowSet1.close();
                if (filteredRowSet1 != null)
                    filteredRowSet1.close();
                if (joinRowSet1 != null)
                    joinRowSet1.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
//        testResultSet();
        testJdbcRowSet();

    }
}
