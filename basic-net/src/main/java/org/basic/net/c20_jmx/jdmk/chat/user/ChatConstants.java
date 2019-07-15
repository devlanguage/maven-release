package org.basic.net.c20_jmx.jdmk.chat.user;

/**
 * 
 */
public class ChatConstants {

    public final static String CHAT_SERVER_HOST = "localhost";

    public final static int HTML_ADAPTER_PORT = 8080;
    public final static int RMI_CONNECTOR_PORT = 8081;
    public final static int JMXMP_CONNECTOR_PORT = 8082;

    /**
     * service:jmx:rmi:///jndi/rmi://localhost:8081/server
     */
    public final static String JMX_SERVICE_URL_CONNECTOR_RMI_SERVER = "service:jmx:rmi:///jndi/rmi://"
            + CHAT_SERVER_HOST + ":" + ChatConstants.RMI_CONNECTOR_PORT + "/server";
    /**
     * service:jmx:jmxmp:///jndi/jmxmp://localhost:8082/server
     */
    public final static String JMX_SERVICE_URL_CONNECTOR_JMXMP_SERVER = "service:jmx:jmxmp:///jndi/jmxmp://"
            + CHAT_SERVER_HOST + ":" + ChatConstants.JMXMP_CONNECTOR_PORT + "/server";

    public final static String JMX_SERVICE_URL_HTML_ADAPTER_SERVER = "";
    public final static String JMX_SERVICE_URL_SOAP_SERVER = "";

    public final static String CHAT_ADAPTER_DOMAIN = "ChatAdapterServer";
    public final static String CHAT_CONNECTOR_DOMAIN = "ChatConnectorServer";
    public final static String CHAT_MBEANS_DOMAIN = "ChatServerMBeans";

    public static final int SERVER_SOCKET_PORT = 18080;

    public static final int SERVER_SOCKET_TIMEOUT = 10;

    public static final String TCP_MONITOR_PORT = "20000";

}
