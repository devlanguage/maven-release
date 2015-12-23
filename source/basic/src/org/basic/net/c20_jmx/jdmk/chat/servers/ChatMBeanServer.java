/**
 * Copyright reserved by Tellabs Communication Corp. LTD. The file
 * org.basic.jmx.examples.chat.ChatServer.java is created on 2008-1-7
 */
package org.basic.net.c20_jmx.jdmk.chat.servers;

import java.io.IOException;
import java.rmi.registry.LocateRegistry;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;

import org.basic.net.c20_jmx.jdmk.chat.user.ChatConstants;
import org.basic.net.c20_jmx.jdmk.chat.user.UserMessage;
import org.basic.net.c20_jmx.jdmk.chat.user.UserMessageMBean;

import com.sun.jdmk.comm.HtmlAdaptorServer;

/**
 * 
 */
public class ChatMBeanServer {

    private MBeanServer mbeanServer;
    private HtmlAdaptorServer htmlAdapterServer;
    private JMXConnectorServer rmiConnectorServer;
    private JMXConnectorServer jmxmpConnectorServer;

    private ChatMBeanServer() {

        mbeanServer = MBeanServerFactory.createMBeanServer(ChatConstants.CHAT_MBEANS_DOMAIN);

    }

    private boolean initMBeanInstrumenation() {

        // Standard MBean

        try {

            ObjectName userMessageMBeanName = new ObjectName(this.mbeanServer.getDefaultDomain()
                    + ":type=" + UserMessage.class.getName());
            UserMessageMBean userMessageMBean = new UserMessage(this.mbeanServer,
                    userMessageMBeanName);
            this.mbeanServer.registerMBean(userMessageMBean, userMessageMBeanName);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {

        ChatMBeanServer mbeanServer = new ChatMBeanServer();
        try {
            if (mbeanServer.startServer()) {
                mbeanServer.startAdapter();
                mbeanServer.startConnector();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to start the Chat Server");
            mbeanServer.close();
        }
    }

    private boolean startServer() {

        return initMBeanInstrumenation();
    }

    private void close() {

        htmlAdapterServer.stop();
        MBeanServerFactory.releaseMBeanServer(this.mbeanServer);
    }

    private void startConnector() throws IOException, InstanceAlreadyExistsException,
            MBeanRegistrationException, NotCompliantMBeanException, MalformedObjectNameException,
            NullPointerException {

        // RMI Server
        LocateRegistry.createRegistry(ChatConstants.RMI_CONNECTOR_PORT);// Start the rmi registry.
        // 也可以：[在命令提示行输入：rmiregistry 8081]

        JMXServiceURL rmiUrl = new JMXServiceURL(ChatConstants.JMX_SERVICE_URL_CONNECTOR_RMI_SERVER);
        rmiConnectorServer = JMXConnectorServerFactory.newJMXConnectorServer(rmiUrl, null,
                mbeanServer);

        this.mbeanServer.registerMBean(rmiConnectorServer, new ObjectName(
                ChatConstants.CHAT_CONNECTOR_DOMAIN + ":name=RmiConnectorServer" + ",port="
                        + ChatConstants.RMI_CONNECTOR_PORT));
        rmiConnectorServer.start();
        System.out.println("All the RMI Server Connector MBean[url=" + rmiUrl.toString()
                + ", port=" + ChatConstants.RMI_CONNECTOR_PORT + "] have startuped.");

        // Message Protocal JMX Server
        JMXServiceURL jmxmpUrl = new JMXServiceURL(
                ChatConstants.JMX_SERVICE_URL_CONNECTOR_JMXMP_SERVER);
        jmxmpConnectorServer = JMXConnectorServerFactory.newJMXConnectorServer(jmxmpUrl, null,
                mbeanServer);
        this.mbeanServer.registerMBean(jmxmpConnectorServer, new ObjectName(
                ChatConstants.CHAT_CONNECTOR_DOMAIN + ":name=JmxmpConnectorServer" + ",port="
                        + ChatConstants.JMXMP_CONNECTOR_PORT));
        jmxmpConnectorServer.start();
        System.out.println("All the JMX Message Protocol Server Connector MBean[url="
                + jmxmpUrl.toString() + ", port=" + ChatConstants.JMXMP_CONNECTOR_PORT
                + "] have startuped.");

        System.out.println("All the MBean Connectors have startuped.");
    }

    private void startAdapter() throws InstanceAlreadyExistsException, MBeanRegistrationException,
            NotCompliantMBeanException, MalformedObjectNameException, NullPointerException {

        // HTML Adapter:
        htmlAdapterServer = new HtmlAdaptorServer();
        htmlAdapterServer.setPort(ChatConstants.HTML_ADAPTER_PORT);
        this.mbeanServer.registerMBean(htmlAdapterServer, new ObjectName(
                ChatConstants.CHAT_ADAPTER_DOMAIN + ":name=" + htmlAdapterServer.toString()
                        + ",port=" + ChatConstants.HTML_ADAPTER_PORT));
        htmlAdapterServer.start();

        System.out.println("All the RMI Server Connector MBean[port="
                + ChatConstants.HTML_ADAPTER_PORT + "] have startuped.");

        System.out
                .println("MBean Server has startup normally and related MBean have been registered or created.");
        // RMI Connector
        // java.naming.factory.initial=org.jnp.interfaces.NamingContextFactory
        // java.naming.factory.url.pkgs=org.jboss.naming:org.jnp.interfaces
        // # Uncomment this line only if the JNDI server is to run in another JVM;
        // # otherwise, local JNDI requests will go over RMI
        // #java.naming.provider.url=localhost

        //        

    }
}
