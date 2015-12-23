/**
 * Copyright reserved by Tellabs Communication Corp. LTD. The file
 * org.basic.db.oracle.OracleSessionReport.java is created on 2008-6-20
 */
package org.basic.db.oracle;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 
 */
public class OracleSessionReport {

    /**
     * @param args
     */
    public static void main(String[] args) {

        Connection conn = null;
        try {
            conn = OracleConnectionPool.getInstance().getConnection();
            final Statement stmt = conn.createStatement();

            new Thread(new Runnable() {

                /* (non-Javadoc)
                 * @see java.lang.Runnable#run()
                 */
                public void run() {

                    while (true) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        try {
                            ResultSet rs = stmt.executeQuery("select count(*) from v$session");
                            while (rs.next())

                                System.err.println(rs.getString(1));

                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                    }

                }
            }).start();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            OracleConnectionPool.closeConnectionSliently(conn);
        }
    }

}
