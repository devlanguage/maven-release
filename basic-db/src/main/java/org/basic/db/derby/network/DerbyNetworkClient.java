package org.basic.db.derby.network;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.basic.common.util.DbUtil;

/**
 * 
 */
public class DerbyNetworkClient {

    ExecutorService threadService = Executors.newFixedThreadPool(2);

    public static void main(String[] args) {

        DerbyNetworkClient tester = new DerbyNetworkClient();
        try {
            tester.testClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void printSQLError(SQLException e) {

        while (e != null) {
            System.out.println(e.toString());
            e = e.getNextException();
        }
    }

    private void testClient() throws Exception {

        String nsURL = "jdbc:derby://localhost:1527/../test1;create=true";
        Properties props = new Properties();
        props.put("user", "test1");
        props.put("password", "test1");

        /*
         * If you are running on JDK 1.6 or higher, then you do not need to invoke Class.forName().
         * In that environment, the EmbeddedDriver loads automatically.
         */
        Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
        Connection conn = DriverManager.getConnection(nsURL, props);

        /* interact with Derby */
        Statement s = conn.createStatement();
        // s.execute("create table derbyDB(id int GENERATED ALWAYS AS IDENTITY(start with 1,
        // increment by 1), name varchar(20))");
        // s.execute("delete from derbyDB");
        s.execute("insert into derbyDB(name) values('zhangsan')");
        ResultSet rs = s.executeQuery("SELECT * FROM derbyDB");
        System.out.println(DbUtil.printResultSet(rs));
        conn.close();
    }
}
