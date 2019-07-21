package org.basic.db.oracle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.basic.common.util.DbUtils;

public class RowSetTest {

    final static Connection getDerbyConnection() throws Exception {

        String nsURL = "jdbc:derby://localhost:1527/../test1;create=true";
        Properties props = new Properties();
        props.put("user", "test1");
        props.put("password", "test1");

        /*
         * If you are running on JDK 1.6 or higher, then you do not need to invoke Class.forName(). In that environment,
         * the EmbeddedDriver loads automatically.
         */
        Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
        Connection conn = DriverManager.getConnection(nsURL, props);
        return conn;
    }

    public static void main(String[] args) {

        Connection conn = null;
        try {
            conn = getDerbyConnection(); /* interact with Derby */
            initTestEnvironment(conn);

            Statement s = conn.createStatement();
            // s.execute("create table derbyDB(id int GENERATED ALWAYS AS IDENTITY(start with 1, increment by 1), name varchar(20))");
            // s.execute("delete from derbyDB");
            s.execute("insert into derbyDB(name) values('zhangsan')");
            ResultSet rs = s.executeQuery("SELECT * FROM derbyDB");
            System.out.println(DbUtils.printResultSet(rs));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    private static void initTestEnvironment(Connection conn) {

        try {
            Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
            stmt.execute("select ");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}