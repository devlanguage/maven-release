/**
 * The file org.basic.db.jdbc.concurrent.ProcedureTest.java was created by yongjie.gong on 2008-11-10
 */
package org.basic.db.jdbc.concurrent;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.Executors;

import org.basic.db.oracle.OracleConnectionPool;


/**
 * @author feiye
 */
public class ProcedureTest {

    OracleConnectionPool dbCache = OracleConnectionPool.getInstance();

    public static void main(String[] args) {

        ProcedureTest t1 = new ProcedureTest();
        java.util.concurrent.ExecutorService threadPool = Executors.newCachedThreadPool();
        threadPool.execute(new CreateUserThread(t1));
        threadPool.execute(new CreateUserThread(t1));
        threadPool.execute(new CreateUserThread(t1));
        threadPool.execute(new CreateUserThread(t1));
        threadPool.execute(new CreateUserThread(t1));
        threadPool.execute(new CreateUserThread(t1));
        threadPool.execute(new CreateUserThread(t1));
        threadPool.execute(new CreateUserThread(t1));
        threadPool.execute(new CreateUserThread(t1));
        threadPool.shutdown();
    }

    public void createUser(String userName) {

        Connection conn = null;
        try {
            conn = dbCache.getConnection();
            CallableStatement callable = conn.prepareCall("{call pkg_db_basic.create_user2(?)}");
            callable.setString(1, userName);
            //callable.setString(2, "active");
            callable.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            OracleConnectionPool.closeConnectionSliently(conn);
        }

    }
}
