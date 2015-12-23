/**
 * 
 */
package org.basic.db.oracle;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author ygong
 * 
 */
class ProducerThread implements Runnable {

    private boolean running = false;

    public ProducerThread(boolean running) {

        this.running = running;
    }

    public void resume() {

        this.running = true;
    }

    public void stop() {

        this.running = false;
    }

    public void run() {

        while (running) {

            try {
                System.out.println("Start to sleep");
                Thread.sleep(DbSessionTest.HAVE_REST_IN_SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread() + " awoke up");
            DbSessionTest.dbQueryDemo();
        }

    }
}

class ConsumerThread implements Runnable {

    private boolean running = false;

    public String getName() {

        return this.getClass().getName();
    }

    public ConsumerThread(boolean running) {

        this.running = running;
    }

    public void resume() {

        this.running = true;
    }

    public void stop() {

        this.running = false;
    }

    public void run() {

        while (running) {

            try {
                System.out.println("Start to sleep");
                Thread.sleep(DbSessionTest.HAVE_REST_IN_SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread() + " awoke up");
            DbSessionTest.dbQueryDemo();
        }

    }

}

public class DbSessionTest {

    static final long HAVE_REST_IN_SECONDS = 10;
    static OracleConnectionPool ORACLE_CACHE;
    static Connection conn;

    public static void main(String[] args) {

        ORACLE_CACHE = OracleConnectionPool.getInstance();
        int threadCount = 500;
        int i = 0;
        try {
            conn = DbSessionTest.ORACLE_CACHE.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        while (++i < threadCount) {
            ProducerThread producer = new ProducerThread(true);
            new Thread(producer, producer.getClass().getName()).start();

            ConsumerThread consumer = new ConsumerThread(true);
            new Thread(consumer, consumer.getClass().getName()).start();
        }

    }

    public final static void dbQueryDemo() {

        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select * from user_objects");
            while (rs.next()) {
                System.out.print(",object_id=" + rs.getString("object_id"));
                System.out.print(",object_name=" + rs.getString("object_name"));
                System.out.print(",object_type=" + rs.getString("object_type"));
                System.out.print(",status=" + rs.getString("status"));
                queryDetail(stmt, 1, rs.getRow());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            OracleConnectionPool.closeResultSetSliently(rs);
            OracleConnectionPool.closeStatmentSliently(stmt);
        }

    }

    private final static void queryDetail(Statement stmt, int startLine, int endLine) {

        // Statement stmt = null;
        ResultSet rs = null;
        try {
            // stmt = conn.createStatement();
            rs = stmt.executeQuery("select * from user_tables");
            while (rs.next()) {
                System.out.print(",tablespace_name=" + rs.getString("tablespace_name"));
                System.out.print(",table_name=" + rs.getString("table_name"));
                System.out.print(",cluster_name=" + rs.getString("cluster_name"));
                System.out.println(",max_extents=" + rs.getString("max_extents"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            OracleConnectionPool.closeResultSetSliently(rs);
            // ConnectionFromOracleConnectionCacheImpl.closeStatmentSliently(stmt);
        }
    }

}
