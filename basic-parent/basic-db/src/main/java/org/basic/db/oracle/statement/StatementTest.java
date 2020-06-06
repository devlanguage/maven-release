package org.basic.db.oracle.statement;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.basic.db.oracle.OracleConnectionPool;

/***************************************************************************************************
 * <pre>
 * DROP TABLE DB_USER;
 * DROP SEQUENCE DB_USER_ID_SEQ;
 * DROP TYPE DB_TYPE_USER_TABLE;
 * DROP TYPE DB_TYPE_USER;
 * CREATE TABLE DB_USER (
 *     user_id NUMBER PRIMARY KEY,
 *     user_name VARCHAR2(200),
 *     creator VARCHAR2(200),
 *     deleted VARCHAR2(5)
 * );
 * CREATE SEQUENCE DB_USER_ID_SEQ;
 * CREATE BITMAP INDEX DB_IDX_TEST_USER_001 ON DB_USER(DELETED);
 * INSERT INTO DB_USER VALUES(DB_USER_ID_SEQ.nextval, 'Zhangsan', 'HR', 0);
 * INSERT INTO DB_USER VALUES(DB_USER_ID_SEQ.nextval, '王学还', '研发', 0);
 * INSERT INTO DB_USER VALUES(DB_USER_ID_SEQ.nextval, '秦武夫', 'HR', 1);
 * 
 * CREATE OR REPLACE TYPE DB_TYPE_USER
 * IS OBJECT(
 *         user_id NUMBER,
 *         user_name VARCHAR2(200),
 *         creator VARCHAR2(200)
 * );
 * /
 * CREATE OR REPLACE TYPE
 *  DB_TYPE_USER_TABLE IS TABLE OF DB_TYPE_USER;
 * /
 * 
 * create or replace
 * PACKAGE DB_USER_PKG
 * AS
 *     
 *     TYPE TYPE_REF_CURSOR IS REF CURSOR;
 *     
 *     FUNCTION GET_TODAY RETURN DATE;
 *     PROCEDURE ADD_USER(p_name VARCHAR2);
 *     PROCEDURE ADD_USER(p_user DB_TYPE_USER);
 *     PROCEDURE ADD_USERS(p_user_list DB_TYPE_USER_TABLE);
 *     PROCEDURE QUERY_DEPARTMENTS(
 *         p_user_list OUT DB_TYPE_USER_TABLE
 *     );
 *     
 *     FUNCTION GET_USER_NAME(p_user_id NUMBER) RETURN VARCHAR2;
 *     FUNCTION GET_USER(p_user_id NUMBER) RETURN DB_TYPE_USER;
 *     FUNCTION LIST_USERS RETURN TYPE_REF_CURSOR;    
 * END;
 * /
 * CREATE OR REPLACE
 * PACKAGE BODY DB_USER_PKG AS
 *     FUNCTION GET_TODAY RETURN DATE AS
 *     BEGIN
 *       RETURN SYSDATE;
 *     END GET_TODAY;
 *     
 *     PROCEDURE ADD_USER(p_name VARCHAR2) AS
 *     BEGIN
 *         INSERT INTO DB_USER VALUES(DB_USER_ID_SEQ.nextval, p_name, 'HR', 0);
 *     END ADD_USER;
 *     
 *     PROCEDURE ADD_USER(p_user DB_TYPE_USER) AS
 *     BEGIN
 *        INSERT INTO DB_USER VALUES(DB_USER_ID_SEQ.nextval, p_user.user_name, p_user.creator, 0);
 *     END ADD_USER;
 *     
 *     PROCEDURE ADD_USERS(p_user_list DB_TYPE_USER_TABLE) AS
 *     BEGIN
 *         FOR idx IN p_user_list.FIRST..p_user_list.LAST
 *         LOOP
 *             INSERT INTO DB_USER VALUES(DB_USER_ID_SEQ.nextval, p_user_list(idx).user_name, p_user_list(idx).creator, 0);
 *         END LOOP;    
 *     END ADD_USERS;
 *     
 *     PROCEDURE QUERY_DEPARTMENTS(
 *           p_user_list OUT DB_TYPE_USER_TABLE
 *       ) AS
 *         v_user DB_TYPE_USER := DB_TYPE_USER(0,'BLANK','N/A');
 *         CURSOR v_user_list IS SELECT * FROM DB_USER;
 *     BEGIN
 *         NULL;
 *     END QUERY_DEPARTMENTS;
 *     
 *     FUNCTION GET_USER_NAME(p_user_id NUMBER) RETURN VARCHAR2 AS
 *     BEGIN
 *       
 *       RETURN NULL;
 *     END GET_USER_NAME;
 *     
 *     FUNCTION GET_USER(p_user_id NUMBER) RETURN DB_TYPE_USER AS
 *         v_user DB_TYPE_USER := DB_TYPE_USER(0,'BLANK','N/A');
 *     BEGIN      
 *         SELECT user_id,user_name,creator INTO v_user.user_id, v_user.user_name,v_user.creator FROM DB_USER WHERE user_id = p_user_id;      
 *         RETURN v_user;  
 *     EXCEPTION
 *         WHEN OTHERS THEN      
 *           dbms_output.put_line('Cannot find the department by the dep_id='||p_user_id);
 *         RETURN v_user;
 *     END GET_USER;
 *     
 *     FUNCTION LIST_USERS RETURN TYPE_REF_CURSOR AS
 *         v_user_list TYPE_REF_CURSOR;
 *     BEGIN
 *         OPEN v_user_list FOR SELECT * FROM DB_USER;
 *         RETURN v_user_list;
 *     END LIST_USERS;
 * END DB_USER_PKG;
 * /
 * </pre>
 */
public class StatementTest {

    public final static int COUNT = 100;
    public final static Map<Integer, String> userNames = new HashMap<Integer, String>();
    static {
        for (int i = 0; i < 10; i++) {
            userNames.put(i, "TEST_USER_" + i);
        }
    }

    public static void main(String[] args) {

        Connection conn = null;
        try {
            conn = OracleConnectionPool.getInstance().getConnection();
            conn.createStatement().execute("truncate table DB_USER");
            testStatement(conn);
            // testRate(conn);
            // testConcurrency_OneConnection(conn);
            // Thread.sleep(100000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            OracleConnectionPool.closeConnectionSliently(conn);
        }
        // testConcurrency_MultipleConnection();
    }

    private static void testStatement(Connection conn) throws SQLException, UnsupportedEncodingException {

        Statement stmt1 = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        // Statement stmt2 = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY,
        // ResultSet.HOLD_CURSORS_OVER_COMMIT);
        if (conn.getMetaData().supportsBatchUpdates()) {
            stmt1.addBatch("INSERT INTO DB_USER VALUES(DB_USER_ID_SEQ.nextval, 'Zhangsan', 'HR', 0)");
            stmt1.addBatch("INSERT INTO DB_USER VALUES(DB_USER_ID_SEQ.nextval, '王学还', '研发', 0)");
            stmt1.addBatch("INSERT INTO DB_USER VALUES(DB_USER_ID_SEQ.nextval, '秦武夫', 'HR', 1)");
            stmt1.addBatch("update db_user set deleted = 0");
            int[] changeRowsForEachSql = stmt1.executeBatch(); // [1,1,1,3]
        }
        // true if the first result is a ResultSet object; false if it is an update count or there are no results
        int result1 = stmt1.executeUpdate("update db_user set deleted = 1");
        boolean result2 = stmt1.execute("SELECT * FROM DB_USER");
        assert result1 == 3 && result2;
        ResultSet rs2 = stmt1.getResultSet();
        while (rs2.next()) {
            System.out.println(rs2.getString(2));
        }
    }

    static class CreateUserProcess implements Runnable {

        private Connection conn;

        public CreateUserProcess(Connection c) {

            this.conn = c;
        }

        public void run() {

            try {
                Statement stmt = conn.createStatement();
                for (int i = 0; i < 100; i++) {
                    stmt.addBatch("{call CREATE_USER('TEST_USER_" + i + "', '" + Thread.currentThread().getName()
                            + "')}");
                    // System.out.println("TRUE Thread");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    private static void testConcurrency_OneConnection(final Connection conn) throws SQLException {

        conn.createStatement().execute("truncate table test_user");
        for (int i = 0; i < 10; ++i) {
            new Thread(new CreateUserProcess(conn), "Thread_" + i).start();
        }

    }

    private static void testConcurrency_MultipleConnection() {

        try {
            OracleConnectionPool.getInstance().getConnection().createStatement().execute("truncate table test_user");
            for (int i = 0; i < 10; ++i) {
                new Thread(new CreateUserProcess(OracleConnectionPool.getInstance().getConnection()), "Thread_" + i)
                        .start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // ConnectionFromOracleConnectionCacheImpl.close();
        }

    }

    private static void testRate(Connection conn) throws SQLException {

        long start = System.currentTimeMillis();
        Statement stmt = conn.createStatement();
        for (int i = 0; i < COUNT; ++i) {
            // stmt.executeUpdate("INSERT INTO DB_USER(user_id, user_name)
            // VALUES(test_user_seq.nextval, 'DB_USER'||test_user_seq.currval)");
            stmt
                    .addBatch("INSERT INTO DB_USER(user_id, user_name) VALUES(test_user_seq.nextval, 'TEST_USER_'||test_user_seq.currval)");
        }
        stmt.executeBatch();

        ResultSet rs1 = stmt.getGeneratedKeys();
        while (rs1.next()) {
            System.out.println(rs1.getString(1));
        }

        long end = System.currentTimeMillis();
        System.out.println("Time: " + (end - start));
    }
}
