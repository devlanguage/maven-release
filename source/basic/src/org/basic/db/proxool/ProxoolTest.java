package org.basic.db.proxool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class ProxoolTest {
    private static final Log LOG = LogFactory.getLog(ProxoolTest.class);
    private static void withoutProxool() {

        Connection connection = null;
        try {
            Class.forName("org.hsqldb.jdbcDriver");
            try {
                connection = DriverManager.getConnection("jdbc:hsqldb:test");
            } catch (SQLException e) {
                LOG.error("Problem getting connection", e);
            }

            if (connection != null) {
                LOG.info("Got connection :)");
            } else {
                LOG.error("Didn't get connection, which probably means that no Driver accepted the URL");
            }

        } catch (ClassNotFoundException e) {
            LOG.error("Couldn't find driver", e);
        } finally {
            try {
                // Check to see we actually got a connection before we
                // attempt to close it.
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                LOG.error("Problem closing connection", e);
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
                LOG.error("Problem getting connection", e);
            }

            if (connection != null) {
                LOG.info("Got connection :)");
            } else {
                LOG.error("Didn't get connection, which probably means that no Driver accepted the URL");
            }

        } catch (ClassNotFoundException e) {
            LOG.error("Couldn't find driver", e);
        } finally {
            try {
                // Check to see we actually got a connection before we
                // attempt to close it.
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                LOG.error("Problem closing connection", e);
            }
        }
    }

    /**
     * Tests getting a connection with and without Proxool
     */
    public static void main(String[] args) {
        withoutProxool();
        withProxool();
    }

}
