package org.basic.common.bean;

public class CommonConstants {
	

    public static final String DB_ORACLE_DRIVER_CLASS = "db.oracle.driver";
    public static final String DB_ORACLE_URL = "db.oracle.url";
    public static final String DB_ORACLE_USER = "db.oracle.user";
    public static final String DB_ORACLE_PASSWORD = "db.oracle.password";
    public static final String DB_ORACLE_FETCH_SIZE = "db.oracle.fetch_size";
    public static final String DB_ORACLE_MAX_STATEMENT = "db.oracle.max_statement";

    public static final String POOL_PROXOOL_throttle = "pool.proxool.simultaneous-build-throttle";
    public static final String POOL_PROXOOL_min_conn = "pool.proxool.minimum-connection-count";
    public static final String POOL_PROXOOL_max_count = "pool.proxool.maximum-connection-count";
    public static final String POOL_PROXOOL_test_sql = "pool.proxool.house-keeping-test-sql";
    public static final String POOL_PROXOOL_sleep_time = "pool.proxool.house-keeping-sleep-time";

    public static final String SERVER_REGISTER_TYPE = "server.register.type";
    public static final String SERVER_MTOSI_ENABLE = "server.MTOSI.enable";
    public static final String SERVER_MNTN_ENABLE = "server.MTNM.enable";
    public static final String SERVER_SNMP_ENABLE = "server.SNMP.enable";
    public static final String SERVER_MAX_REPORT_SIZE = "server.report.maxsize";
    public static final String SERVER_MAX_REPORT_LIFE_TIME = "server.report.maxlifetime";
    public static final String SERVER_MAX_CONNECTIONS = "server.connection.maxconnections";
    public static final String SERVER_FILE_MODIFY_CHECK_INTERVAL = "50";// milliseconds
    public static final String SERVER_SOCKET_HEART_BEAT_INTERVAL = "server.socket.heart.beat.interval";
    public static final String SERVER_JMX_REMOTE_SUPPORT = "server.jmxremotesupport";
    public static final String SERVER_JMX_REMOTE_PORT = "server.jmxremoteport";

    public static final String JMS_IMQ_HOSTS = "jms.imq.hosts";
    public static final String JMS_IMQ_PORT = "jms.imq.port";
    public static final String JMS_IMQ_USER = "jms.imq.user";
    public static final String JMS_IMQ_PWD = "jms.imq.pwd";

    public final static String STRING_DELIMITER_AID = "-";
    public final static String STRING_DELIMETER_LAYER_RATE = "/";
    public final static String STRING_DELIMITER_STSMAP = "&";
    public final static String STRING_DELIMITER_SPACE = " ";
    public final static String STRING_DELIMITER_THREE_BAR = "|||";
    public final static String STRING_DELIMITER_SHARP = "#@#";
    public final static String STRING_DELIMITER_AT = "@";
    public final static String STRING_DELIMITER_COLON = ":";
    public final static String STRING_DELIMITER_SEMICOLON = ";";
    public final static String STRING_DELIMITER_COMMA = ",";
    public final static String STRING_DELIMITER_EQUAL = "=";

    public final static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public final static String GMT_DATE_FORMAT = "yyyyMMddHHmmss.SSS";
    public final static String ITU_DATE_FORMAT_NO_TIME_ZONE = "yyyyMMddHHmmss.SSSSS";// yyyyMMddhhmmss.s[Z|{+|-}HHMm]
                                                                                     // //19851106210627.3+0500
    public final static String ITU_DATE_FORMAT_WITH_TIME_ZONE = "yyyyMMddHHmmss.SSSSSZ";
    public final static String DB_TIMESTAMP_FORMAT = "yy-MM-dd hh24:mi:ss";
    public final static String TIMEZONE_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSSSZ";
    public final static String ICON_FILE_PATH = "";

    public final static String CONFIG_FILE_COMMON_SIMBOL = "#";
    public static final String COMMON_MESSAGES = "CommonMessages.properties";
    // public final static String CONFIG_FILE_COMMON_CONFIG =
    // "C:/software/Java_Dev/eclipse_proj/JavaBasic/java/basic/src/config.properties";
    public final static String CONFIG_FILE_COMMON_CONFIG = "config.properties";
    public static final String DEFAULT_CONFIG_FILE_DIR = "D:/Cloud/Prj_files/JavaBasic/java/basic/src";

    public final static String MENU_DATA_FILE = "";
    public static final String CONFIG_FILE_Common_LOGGER = CommonConstants.DEFAULT_CONFIG_FILE_DIR + "/log4j.properties";
}
