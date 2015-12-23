package org.third.jms.util.helper;

public class ConfigurationConstant {

    public static final String CONFIG_FILE_NAME = "imq_config.conf";

    public static String serverName = "server.name";
    public static String serverIp = "server.ip";
    public static String port = "server.port";
    public static String servicesNames = "server.services";
    public static String ADMIN_PORT = "server.adminservicelistenport";
    public static String RESYNC_RETRY_COUNT = "server.resyncretrycount";
    public static String RESYNC_RETRY_TIMEGAP = "server.resynctimegap";
    public final static String SERVER_854_HANDLER = "server.mtosiintf.handler.threadSize";

    public static final String SERVER_REGISTER_TYPE = "server.register.type";
    public static final String SERVER_MTOSI_ENABLE = "server.MTOSI.enable";
    public static final String SERVER_MNTN_ENABLE = "server.MTNM.enable";
    public static final String SERVER_SNMP_ENABLE = "server.SNMP.enable";
    public static final String MAX_REPORT_SIZE = "server.report.maxsize";
    public static final String MAX_REPORT_LIFE_TIME = "server.report.maxlifetime";
    public static final String MAX_CONNECTIONS = "server.connection.maxconnections";

    public static String ORACLE_DRIVER_CLASS = "oracle.driver_class";
    public static String ORACLE_URL = "oracle.url";
    public static String ORACLE_USER_NAME = "oracle.user";
    public static String ORACLE_PASSWORD = "oracle.password";
    public static String ORACLE_FETCH_SIZE = "oracle.fetch_size";
    public static String ORACLE_MAX_STATEMENT = "oracle.max_statement";
    public static String ORACLE_MAX_CONNECTION_COUNT = "oracle.max_connection";
    public static String ORACLE_MIN_CONNECTION_COUNT = "oracle.min_connection";
    public static String ORACLE_RETRY_INTERVAL = "oracle.retry_interval";
    public static String ORACLE_MIN_RETRY_SQL = "oracle.retry_sql";

    public static final String MSG_RECONNECT_INTERVAL = "message.reconnectInterval";

    public final static String MESSAGE_IMQHOME = "message.imqhome";
    public final static String MESSAGE_IMQPORT = "message.imqport";
    public final static String MESSAGE_USERNAME = "message.username";

}
