package org.third.orm.hibernate3.common.bean;

import static org.third.orm.hibernate3.common.bean.CommonConstants.DB_ORACLE_CONFIG_FILE;
import static org.third.orm.hibernate3.common.bean.CommonConstants.DB_ORACLE_HOST;
import static org.third.orm.hibernate3.common.bean.CommonConstants.DB_ORACLE_PASSWORD;
import static org.third.orm.hibernate3.common.bean.CommonConstants.DB_ORACLE_PORT;
import static org.third.orm.hibernate3.common.bean.CommonConstants.DB_ORACLE_SID;
import static org.third.orm.hibernate3.common.bean.CommonConstants.DB_ORACLE_URL;
import static org.third.orm.hibernate3.common.bean.CommonConstants.DB_ORACLE_USER;

import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

/**
 * 
 */
public class OracleDatabase implements Database {

    private static String host;
    private static String port;
    private static String sid;
    private static String user;
    private static String password;
    private static String url = "jdbc:oracle:thin:@192.168.120.10:1521:ems7100s";

    static {
        Properties properties = new Properties();
        try {
            properties.loadFromXML(OracleDatabase.class.getResourceAsStream(DB_ORACLE_CONFIG_FILE));
            host = properties.getProperty(DB_ORACLE_HOST);
            port = properties.getProperty(DB_ORACLE_PORT);
            sid = properties.getProperty(DB_ORACLE_SID);
            user = properties.getProperty(DB_ORACLE_USER);
            password = properties.getProperty(DB_ORACLE_PASSWORD);
            url = properties.getProperty(DB_ORACLE_URL);
        } catch (InvalidPropertiesFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getHost() {

        return OracleDatabase.host;
    }

    public void setHost(String host) {

        OracleDatabase.host = host;
    }

    public String getPassword() {

        return OracleDatabase.password;
    }

    public void setPassword(String password) {

        OracleDatabase.password = password;
    }

    public String getPort() {

        return OracleDatabase.port;
    }

    public void setPort(String port) {

        OracleDatabase.port = port;
    }

    public String getSid() {

        return OracleDatabase.sid;
    }

    public void setSid(String sid) {

        OracleDatabase.sid = sid;
    }

    public String getUser() {

        return OracleDatabase.user;
    }

    public void setUser(String user) {

        OracleDatabase.user = user;
    }

    /**
     * @return get method for the field url
     */
    public String getUrl() {

        return OracleDatabase.url;
    }

    /**
     * @param url
     *            the url to set
     */
    public static void setUrl(String url) {

        OracleDatabase.url = url;
    }

}
