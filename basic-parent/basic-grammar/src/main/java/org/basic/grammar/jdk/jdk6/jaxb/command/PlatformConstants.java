package org.basic.grammar.jdk.jdk6.jaxb.command;

/**
 * 
 */
public class PlatformConstants {

    
    public final static String INPUT_COMMAND = "tmf.nbi.platform.console.input.command";
    public final static String DB_CLOSED = "tmf.nbi.platform.console.db.closed";
    public final static String DB_ALIVE = "tmf.nbi.platform.console.db.alive";
    public final static String MESSAGE_WELCOME = "tmf.nbi.platform.console.message.welcome";
    public final static String MESSAGE_HELP_ALL = "tmf.nbi.platform.console.message.help.all";
    
    
    public final static String MESSAGE_ERROR_COMMAND = "tmf.nbi.platform.message.error.command";    
    public final static String MESSAGE_NBI_STATUS_PRMOPT = "tmf.nbi.platform.message.nbi.status.prompt";
    public final static String MESSAGE_NBI_STATUS_WELCOME = "tmf.nbi.platform.message.nbi.status.welcome";
    public final static String MESSAGE_BLANK_INPUT = "tmf.nbi.platform.message.blank.input";
    
    public final static String CONNECTION_SUCCESS= "successful";
    public final static String CONNECTION_FAILED= "failed";    
    
    public final static String MESSAGE_ADMIN_CONSOLE_WELCOME="tmf.nbi.platform.message.admin.console.welcome";
    public final static String MESSAGE_NOT_AVAILABLE_MESSAGE = "tmf.nbi.platform.message.not.available.message";

    public final static String DATE_FORMAT="tmf.nbi.platform.date.format";
    public final static String MESSAGE_NBI_SERVER_INTERNAL_ERROR = "tmf.nbi.platform.message.nbi.server.internal.error";
    
    public static String NBI_PLATFORM_ADMIN_COMMANDS = null;
    public static String NBI_PLATFORM_MESSAGES= null;
    static {
        StringBuffer sb = new StringBuffer();
        sb.append("<commands>")
        .append("<command valid=\"false\"><name>start</name><description>Start server</description><response_message><![CDATA[NBI Server current status: {0}]]></response_message></command>")
        .append("<command><name>stop</name><description>Stop server</description><response_message><![CDATA[NBI Server current status: {0}]]></response_message></command>")
        .append("<command><name>quit</name><description>Quit the Admin console</description><response_message><![CDATA[Bye!]]></response_message></command>")
        .append("<command><name>help</name><description>Display the command help information.</description><response_message><![CDATA[help : Display the command help information.]]></response_message></command>")
        .append("<command><name>desc dbconn</name><description>Display database adapter connection count</description><response_message><![CDATA[Database adapter connection count: {0}]]></response_message></command>")
        .append("<command><name>desc dbthread</name><description>Display the database adapter handle thread count</description><response_message><![CDATA[Database adapter handle thread count:  {0}]]></response_message></command>")
        .append("<command><name>desc report</name><description>Display reports count</description><response_message><![CDATA[Reports count: {0}]]></response_message></command>")
        .append("<command><name>desc reportpool active</name><description>Display reports thread pool active count</description><response_message><![CDATA[Avtive task count in report pool: {0}]]></response_message></command>")
        .append("<command><name>desc reportpool task</name><description>Display reports thread pool tasks count</description><response_message>Task count in report thread pool: {0}</response_message></command>")
        .append("<command><name>desc dbpool active</name><description>Display the active connection count in database cache pool</description><response_message><![CDATA[Active connection count in database cache pool: {0}]]></response_message></command>")
        .append("<command><name>desc dbpool cache</name><description>Display database pool cache count</description><response_message><![CDATA[Database pool cache count: {0}]]></response_message></command>")
        .append("<command valid=\"false\"><name>show nbi registion</name><description>Display the registion list</description><response_message><![CDATA[NBI Registions follows as: \n {0}]]></response_message></command>")
        .append("<command><name>show adapterqueue size</name><description>Display the adapter queue size</description><response_message><![CDATA[Adapter queue size: {0}]]></response_message></command>")
        .append("<command><name>show mtosiqueue size</name><description>Display the MTOSI Queue size</description><response_message><![CDATA[MTOSI queue size is: {0}]]></response_message></command> ")
        .append("<command><name>show mtnmqueue size</name><description>Display the MTNM Queue Size</description><response_message><![CDATA[MTNM queue size is: {0}]]></response_message></command>")
        .append("<command><name>show parameter</name><description>Display parameters from TMF NBI Server</description><response_message><![CDATA[        Database adapter handle thread count: {1}\n        Reports count is: {2}\n        Reports pool active count is: {3}\n        Task count in report thread pool: {4}\n        Database pool active count is: {5}\n        Database pool cache count is: {6}\n        Adapter queue size is: {7}\n        MTOSI queue size is: {8}\n        MTNM queue size is: {9}\n]]></response_message></command>")
        .append("<command><name>set loglevel</name><description><![CDATA[Set the log level. The command format is as follows:\n        set loglevel LevelValue\n        Notes:\n           LevelValue: it indicates which log level will be set as valid. valid value case-insensitive\n                     is as list: off, debug, info, warn ,error, fatal, all]]></description><response_message><![CDATA[Log level has been set as: {0}]]></response_message></command>")
        .append("<command><name>show status</name><description><![CDATA[Display all the current connection status, including connection to DB, JMS,\n Cobra Naming Service and Corba Notification Service]]></description><response_message><![CDATA[        Connection status to EMS database:               {0}\n        Connection status to JMS Server:                 {1}\n        Connection status to Corba Naming Service:       {2}\n        Connection status to Corba Notification Service: {3}]]></response_message></command>")
        .append("<command><name>show status db</name><description>     Display the current connection status on DB</description><response_message><![CDATA[Connection status to EMS database: {0}]]></response_message></command>")
        .append("<command><name>show status jms</name><description>    Display the current connection status on JMS Server</description><response_message><![CDATA[Connection status to JMS Server: {0}]]></response_message></command>")
        .append("<command><name>show status corba</name><description>    Display the current connection status to Corba Naming Service and Corba Notification Service</description><response_message><![CDATA[        Connection status to Corba Naming Service: {0}\n        Connection status to Corba Notification Service: {1}]]></response_message></command>")
        .append("<command><name>show session</name><description>      Display the session information: session id, session related      report, session connection information and session's last      active time</description><response_message><![CDATA[Session Information follows as:\n---------------------------------------------------------------------]]></response_message></command>")
        .append("<command><name>show subscription</name><description>      Display the web side notification subscription list</description><response_message><![CDATA[web side notification subscription list follow as :\n---------------------------------------------------------------------]]></response_message></command>")
        .append("<command><name>dump mq</name><description><![CDATA[Dump the message queue content into the console or files.\n This is the complete command format:\n   dump mq serviceName [destination]\n  Notes:\n  serviceName: Specify which messages of the service in local queue will be dump. valid values follow\n      as: messaging, adapter, mtosi, mtnm, reporting, provisioning, security, admin\n destination: Specify the path where dumpped content will be stored. Abosulte or relative paths are\n       all allowed.]]></description><response_message></response_message></command>")
        .append("</commands>");
        NBI_PLATFORM_ADMIN_COMMANDS = sb.toString();
        sb.delete(0, sb.length());
        
        sb.append("<!DOCTYPE properties SYSTEM \"http://java.sun.com/dtd/properties.dtd\">")
        .append("<properties>")
        .append("<entry key=\"tmf.nbi.platform.message.nbi.status.prompt\"><![CDATA[NbiStatusComamnd>]]></entry>")
        .append("<entry key=\"tmf.nbi.platform.message.nbi.status.welcome\"><![CDATA[          NBI Server Status Panel: Release FP 4.2 - Production on\n         Copyright (c) 2007, Tellabs Corporation.  All rights reserved.]]></entry>")
        .append("<entry key=\"tmf.nbi.platform.message.error.command\"><![CDATA[please input the valid nbi status command or type the command \"help\" for help]]></entry>")
        .append("<entry key=\"tmf.nbi.platform.message.blank.input\"><![CDATA[command value is not allowed to be blank, please input the valid nbi status command or type the command \"help\" for help]]></entry>")
        .append("<entry key=\"tmf.nbi.platform.message.admin.console.welcome\"><![CDATA[       welcome to admin server console\n        please type the command:]]></entry>")
        .append("<entry key=\"tmf.nbi.platform.message.not.available.message\"><![CDATA[There are not available message in service message queue]]></entry>")
        .append("<entry key=\"tmf.nbi.platform.date.format\"><![CDATA[yyyy-MM-dd HH:mm:ss]]></entry>")
        .append("<entry key=\"tmf.nbi.platform.message.nbi.server.internal.error\">NBI Server internal error!</entry>")
        .append("</properties>");  
        NBI_PLATFORM_MESSAGES = sb.toString();
        
    } 
}
