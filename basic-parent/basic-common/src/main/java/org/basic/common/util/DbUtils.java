package org.basic.common.util;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.basic.common.bean.CommonLogger;

public class DbUtils {

    final static CommonLogger logger = CommonLogger.getLogger(DbUtils.class);

    public final static String printResultSet(ResultSet rs) throws SQLException, UnsupportedEncodingException {

        StringBuilder buffer = new StringBuilder();
        ResultSetMetaData rsmd = rs.getMetaData();
        while (rs.next()) {
            buffer.append(rsmd.getTableName(1)).append(":[");
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                if (i == 1) {
                    buffer.append(rsmd.getColumnName(i) + "=" + rs.getString(i));
                } else {
                    if (rs.getString(i) == null) {
                        buffer.append("," + rsmd.getColumnName(i) + "=" + rs.getString(i));
                    } else {
                        buffer.append("," + rsmd.getColumnName(i) + "="
                                + new String(rs.getString(i).getBytes("ISO-8859-1"), "gb2312"));
                    }
                }
            }
            buffer.append("]\n");
        }

        logger.log(CommonLogger.INFO, "print",buffer.toString());
        return buffer.toString();
    }

    public final static void closeQuitely(Statement stmt) {

        if (null != stmt) {
            try {
                stmt.close();
            } catch (SQLException ex) {
                logger.log(CommonLogger.SEVERE, "closeStatement", "Failed to close the Statement-" + stmt, ex);
            }
        }
    }

    public final static void closeQuitely(Connection conn) {

        if (null != conn) {
            try {
                conn.close();
            } catch (SQLException ex) {
                logger.log(CommonLogger.SEVERE, "closeConnection", "Failed to close the Connection-" + conn, ex);
            }
        }
    }
    

    private static void withProxool() {

        Connection connection = null;
        try {
            // NOTE THIS LINE
            Class.forName("org.logicalcobwebs.proxool.ProxoolDriver");
            try {
                // NOTE THIS LINE
                connection = DriverManager.getConnection("proxool.example:org.hsqldb.jdbcDriver:jdbc:hsqldb:test");
            } catch (SQLException e) {
                logger.log(CommonLogger.SEVERE, "Problem getting connection", e);
            }

            if (connection != null) {
                logger.info("Got connection :)");
            } else {
                logger.error("Didn't get connection, which probably means that no Driver accepted the URL");
            }

        } catch (ClassNotFoundException e) {
            logger.log(CommonLogger.SEVERE, "Couldn't find driver", e);
        } finally {
            try {
                // Check to see we actually got a connection before we
                // attempt to close it.
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                logger.log(CommonLogger.SEVERE, "Problem closing connection", e);
            }
        }
    }
}
