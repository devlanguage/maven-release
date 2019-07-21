package org.basic.db.proxool.hello;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProxoolHelloTest {
    public final static Logger LOG = Logger.getLogger("Test");

    //Programmatically
    public final static Connection getConnectionByUrl() {
        Connection connection = null;
        try {
            Class.forName("org.logicalcobwebs.proxool.ProxoolDriver");
            try {
                // "example": alias for this pool. This is a unique label that we will use to reference the pool.
                // "org.hsqldb.jdbcDriver": JDBC driver Proxool should use to connect to the database.
                // "jdbc:hsqldb:test": JDBC URL Proxool should use to connect to the database.
                
                Properties info = new Properties();
                info.setProperty("proxool.maximum-connection-count", "10");
                info.setProperty("proxool.house-keeping-test-sql", "select CURRENT_DATE");
                info.setProperty("user", "sa");
                info.setProperty("password", "");
                String alias = "test";
                String driverClass = "org.hsqldb.jdbcDriver";
                String driverUrl = "jdbc:hsqldb:test";
                String url = "proxool." + alias + ":" + driverClass + ":" + driverUrl;
                
                connection = DriverManager.getConnection("proxool.test",info);
            } catch (SQLException e) {
                LOG.log(Level.SEVERE, "Problem getting connection", e);
            }
        } catch (ClassNotFoundException e) {
            LOG.log(Level.SEVERE, "Couldn't find driver", e);
        }
        return connection;
    }

    public final static Connection getConnectionByProperties() {
        Connection connection = null;
        try {
            Properties info = new Properties();
            info.setProperty("proxool.maximum-connection-count", "20");
            info.setProperty("proxool.house-keeping-test-sql", "select CURRENT_DATE");
            info.setProperty("user", "sa");
            info.setProperty("password", "");
            
            String alias = "test";
            String driverClass = "org.hsqldb.jdbcDriver";
            String driverUrl = "jdbc:hsqldb:test";
            String url = "proxool." + alias + ":" + driverClass + ":" + driverUrl;
            connection = DriverManager.getConnection(url, info);

        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Couldn't find driver", e);
        }
        return connection;
    }

    public final static Connection getConnectionByXmlFile() {
        Connection connection = null;
        try {
            String proxoolXmlFile = ProxoolHelloTest.class.getResource("proxool_config.xml").getPath();
            boolean validate = false;
            org.logicalcobwebs.proxool.configuration.JAXPConfigurator.configure(proxoolXmlFile, validate);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Couldn't find driver", e);
        }
        return connection;
    }

    public final static Connection getConnectionByPropertieFile() {
        Connection connection = null;
        try {
            String proxoolXmlFile = ProxoolHelloTest.class.getResource("proxool_config.properties").getPath();
            org.logicalcobwebs.proxool.configuration.PropertyConfigurator.configure(proxoolXmlFile);
            connection = DriverManager.getConnection("proxool.property-test");
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Couldn't find driver", e);
        }
        return connection;
    }

    public static void main(String[] args) {
        Connection connection = null;
        try {
            connection = getConnectionByUrl();
            if (connection != null) {
                LOG.info("Got connection :)");
            } else {
                LOG.log(Level.SEVERE, "Didn't get connection, which probably means that no Driver accepted the URL");
            }

        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Couldn't find driver", e);
        } finally {
            try {
                // Check to see we actually got a connection before we
                // attempt to close it.
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                LOG.log(Level.SEVERE, "Problem closing connection", e);
            }
        }
    }
}
