package org.basic.db.derby.embed;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.basic.common.util.DbUtil;

/**
 * 
 */
public class DerbyEmbedTester {

    public static final String CSdriver = new String("org.apache.derby.jdbc.EmbeddedDriver");
    public static final String dbURLCS = new String("jdbc:derby:toursDB;create=true");

    public static void main(String[] args) {

        DerbyEmbedTester tester = new DerbyEmbedTester();
        Connection conn = null;
        try {

            System.out.println("Loading the Cloudscape jdbc driver...");
            Class.forName(CSdriver).newInstance();

            System.out.println("Getting Cloudscape database connection...");
            conn = DriverManager.getConnection(dbURLCS);
            System.out.println("Successfully got the Cloudscape database connection...");

//             tester.dropTables(conn);
             tester.createTables(conn);
            // tester.insertData(conn);
            tester.queryData(conn);
            // tester.createDerby();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            boolean gotSQLExc = false;
            try {
               DriverManager.getConnection("jdbc:derby:;shutdown=true");
            } catch (SQLException se)  {
               if ( se.getSQLState().equals("XJ015") ) {
                  gotSQLExc = true;
               }
            }
            if (gotSQLExc) {
                System.out.println("Database shut down normally");
            }  else  {
                System.out.println("Database did not shut down normally");
            }
         
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void insertData(Connection conn) throws SQLException, FileNotFoundException {

        PreparedStatement ps = null;

        ps = conn
                .prepareStatement("insert into maps (map_name, region, area, photo_format, picture) values (?,?,?,?,?)");

        ps.setString(1, "BART");
        ps.setString(2, "Bay Area");
        ps.setBigDecimal(3, new BigDecimal("1776.11"));
        ps.setString(4, "gif");
        File file = new File("nbi_config.conf");
        InputStream fileIn = new FileInputStream(file);
        ps.setBinaryStream(5, fileIn, (int) file.length());
        int numrows = ps.executeUpdate();

        ps.setString(1, "Caltrain");
        ps.setString(2, "West Bay");
        ps.setBigDecimal(3, new BigDecimal("1166.77"));
        ps.setString(4, "gif");
        file = new File("test.txt");
        fileIn = new FileInputStream(file);
        ps.setBinaryStream(5, fileIn, (int) file.length());
        numrows = numrows + ps.executeUpdate();

        ps.setString(1, "Light Rail");
        ps.setString(2, "Santa Clara Valley");
        ps.setBigDecimal(3, new BigDecimal("9117.90"));
        ps.setString(4, "gif");
        file = new File("plsql.sql");
        fileIn = new FileInputStream(file);
        ps.setBinaryStream(5, fileIn, (int) file.length());
        numrows = numrows + ps.executeUpdate();

        System.out.println("Inserted " + numrows + " rows into the ToursDB");

        ps.close();
    }

    public void createTables(Connection conn) throws SQLException {

        try {
            conn.setAutoCommit(false);

            Statement stmt = conn.createStatement();
            String init_table = "CREATE TABLE MAPS ("
                    + " MAP_ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                    + " MAP_NAME VARCHAR(24) NOT NULL," + " REGION VARCHAR(26),"
                    + " AREA DECIMAL(8,4) NOT NULL," + " PHOTO_FORMAT VARCHAR(26) NOT NULL,"
                    + " PICTURE BLOB(102400)," + " UNIQUE (MAP_ID, MAP_NAME) )";
            stmt.execute(init_table);
            stmt.execute("create table derbyDB(num int, addr varchar(40))");
            System.out.println("Created table derbyDB");
            stmt.execute("insert into derbyDB values (1956,'Webster St.')");
            System.out.println("Inserted 1956 Webster");
            stmt.execute("insert into derbyDB values (1910,'Union St.')");
            System.out.println("Inserted 1910 Union");
            stmt.execute("update derbyDB set num=180, addr='Grand Ave.' where num=1956");
            System.out.println("Updated 1956 Webster to 180 Grand");

            conn.commit();

        } catch (SQLException e) {
            System.out.println("FAIL -- unexpected exception: " + e.toString());
            conn.rollback();
            e.printStackTrace();
        }
    }

    private void queryData(Connection conn) throws Exception {

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("select * from MAPS");
        System.out.println(DbUtil.printResultSet(rs));
    }
}
