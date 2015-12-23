/**
 * Copyright reserved by Tellabs Communication Corp. LTD. The file
 * org.basic.apache.dbcp.examples.BasicDataSource.java is created on Sep 26, 2007 11:05:37 AM
 */
package org.basic.db.dbcp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;

/**
 * 
 */
public class BasicDataSourceExample implements Example {

    static BasicDataSource _dataSource = null;
    static {
        try {
            _dataSource = setupDataSource();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // final static DatabaseManager DB_ORACLE = DatabaseManager.getInstance();
    public static BasicDataSource setupDataSource() throws SQLException {

        Properties properties = new Properties();

        // BasicDataSource bds = (BasicDataSource)
        // BasicDataSourceFactory.createDataSource(properties);

        DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
        BasicDataSource _dataSource = new BasicDataSource();

        _dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        _dataSource.setUsername("ygong1");
        _dataSource.setPassword("tellabs");
        _dataSource.setUrl("jdbc:oracle:thin:@sunshapp10:1521:EMS7100S");
        // _dataSource.setDescription("JDBC DataSource Connection");
        _dataSource.setMaxOpenPreparedStatements(100);
        _dataSource.setMaxActive(5);
        _dataSource.setMinIdle(3);
        _dataSource.setMaxWait(3000);
        // _dataSource.setCacheScheme(OracleConnectionCacheImpl.DYNAMIC_SCHEME);

        _dataSource.setAccessToUnderlyingConnectionAllowed(true);
        _dataSource.setInitialSize(5);
        return _dataSource;
    }

    public void test() {

    }

    /**
     * @param args
     */
    public static void main(String[] args) {

        // Map<Thread, StackTraceElement[]> stackTraces = Thread.getAllStackTraces();
        // for (Entry<Thread, StackTraceElement[]> entry : stackTraces.entrySet()) {
        // Thread thread = entry.getKey();
        //
        // System.out.println(thread.getUncaughtExceptionHandler());
        //
        // System.out.println(thread.getClass().getName()+": [name=\"" + thread.getName() + "\"" + "
        // Priority="
        // + thread.getPriority() + " ThreadID=" + thread.getId() + " ThreadGroup="
        // + thread.getThreadGroup().getName() + " State=" + thread.getState()
        // + " HashCode=" + thread.hashCode()+"]");
        // // thread.dumpStack();
        // System.err.flush();
        // for (StackTraceElement st : thread.getStackTrace()) {
        // System.out.println("\tat " + st);
        // }
        // }
        try {
            DbcpTest dt = new DbcpTest();
            new Thread(dt).start();
            Thread.sleep(15000);
            dt.stop();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                _dataSource.close();
                
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static class DbcpTest implements Runnable {

        private boolean running = true;

        public void stop() {

            running = false;
        }

        public void run() {

            System.out.println(_dataSource.getMaxActive());
            System.out.println(_dataSource.getInitialSize());

            //        
            // bds.setDriverClassName("DB_ORACLE.jdbc.driver.OracleDriver");//
            // DB_ORACLE.jdbc.OracleDriver
            // bds.setUsername("emsinstall");
            // bds.setPassword("tellabs");
            // bds.setUrl("jdbc:DB_ORACLE:thin:@192.168.120.10:1521:ems7100s");

            // -Djdbc.drivers=DB_ORACLE.jdbc.driver.OracleDriver

            //
            // Now, we can use JDBC DataSource as we normally would.
            //
            Connection conn = null;
            Statement stmt = null;
            ResultSet rset = null;
            while (running)

                try {
                    Thread.sleep(3000);
                    System.out.println("Creating connection.");
                    conn = _dataSource.getConnection();

                    System.out.println("Creating statement.");
                    stmt = conn.createStatement();
                    System.out.println("Executing statement.");
                    rset = stmt.executeQuery("select 23 from dual");
                    System.out.println("Results:");
                    int numcols = rset.getMetaData().getColumnCount();
                    while (rset.next()) {
                        for (int i = 1; i <= numcols; i++) {
                            System.out.print("\t" + rset.getString(i));
                        }
                        System.out.println("");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        conn.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

        }
    }

}
