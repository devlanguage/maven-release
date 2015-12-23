package org.basic.db.oracle.statement_preparedStatement;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.basic.db.oracle.OracleConnectionPool;

/***************************************************************************************************
 * <pre>
 * DROP TABLE TEST_USER;
 * DROP SEQUENCE TEST_USER_SEQ;
 *  CREATE TABLE TEST_USER (
 *      user_id NUMBER PRIMARY KEY,
 *      user_name VARCHAR2(200)
 *  );
 *  CREATE SEQUENCE TEST_USER_SEQ;
 *  CREATE OR REPLACE PROCEDURE add_user(p_id NUMBER, p_name VARCHAR2)
 *  AS
 *  BEGIN
 *      INSERT INTO TEST_USER VALUES(p_id, p_name);
 *  END;
 *  /
 * </pre>
 */
public class CallableStatementTest {

    public final static int COUNT = 10000;

    public static void main(String[] args) {

        Connection conn = null;
        try {
            conn = OracleConnectionPool.getInstance().getConnection();
//            testNonBatch(conn);
            testBatch(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            OracleConnectionPool.closeConnectionSliently(conn);
        }
    }

    private static void testNonBatch(Connection conn) throws SQLException {

        conn.createStatement().execute("truncate table test_user");
        long start = System.currentTimeMillis();
        PreparedStatement stmt = conn
                .prepareStatement("INSERT INTO TEST_USER(user_id, user_name) VALUES(?, ?)");
        for (int i = 0; i < COUNT; ++i) {
            stmt.setInt(1, i);
            stmt.setString(2, "TEST_USER_" + i);
            stmt.execute();
        }

        long end = System.currentTimeMillis();
        System.out.println("Non Batch Time: " + (end - start));
    }

    private static void testBatch(Connection conn) throws SQLException {

        conn.createStatement().execute("truncate table test_user");
        long start = System.currentTimeMillis();
CallableStatement pst = conn.prepareCall("{call add_user(?,?)}");
for (int i = 0; i < COUNT; ++i) {
    pst.setInt(1, i);
    pst.setString(2, "TEST_USER_" + i);
        pst.addBatch();
}
int[] updateCount = pst.executeBatch();
 // (1) >=0 (2)Statement.SUCCESS_NO_INFO (3) Statement.EXECUTE_FAILED

        long end = System.currentTimeMillis();
        System.out.println("Batch Time: " + (end - start));
    }
}
