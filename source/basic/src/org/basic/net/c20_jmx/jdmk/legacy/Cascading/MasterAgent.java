package org.basic.net.c20_jmx.jdmk.legacy.Cascading;

/*
 * @(#)file      MasterAgent.java
 * @(#)author    Sun Microsystems, Inc.
 * @(#)version   1.11
 * @(#)lastedit  04/01/19
 *
 * Copyright 2004 Sun Microsystems, Inc.  All rights reserved. Use is subject to license terms.
 */

// java imports
//
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.Set;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.MalformedObjectNameException;
import javax.management.NotificationListener;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.remote.JMXServiceURL;
import javax.management.timer.TimerNotification;

import com.sun.jdmk.ServiceName;
import com.sun.jdmk.comm.CommunicatorServer;
import com.sun.jdmk.comm.HtmlAdaptorServer;
import com.sun.jdmk.comm.RmiConnectorAddress;
import com.sun.jdmk.internal.ReconnectMBeanServerConnectionFactory;
import com.sun.jdmk.remote.cascading.CascadingAgent;
import com.sun.jdmk.remote.cascading.MBeanServerConnectionFactory;
import com.sun.jdmk.remote.cascading.proxy.ProxyCascadingAgent;

public class MasterAgent implements NotificationListener {

    /*
     * ------------------------------------------ CONSTRUCTORS ------------------------------------------
     */

    public MasterAgent() {

        // Parse system properties to check if LEVEL_TRACE and/or LEVEL_DEBUG are set
        // and enable the TRACE level accordingly
        // try {
        // TraceManager.parseTraceProperties();
        // }
        // catch (IOException e) {
        // e.printStackTrace();
        // }
        // CREATE the MBeanServer
        //
        server = MBeanServerFactory.createMBeanServer();
    }

    /*
     * ------------------------------------------ PUBLIC METHODS ------------------------------------------
     */

    public static void main(String[] args) {

        // START
        //
        echo("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        echo("Creating an instance of this  MasterAgent...");
        MasterAgent agent = new MasterAgent();

        // DO THE DEMO
        //
        agent.doDemo();

        // END
        //
        String localHost;
        try {
            localHost = java.net.InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            localHost = "localhost";
        }
        echo("\nNow, you can point your browser to http://" + localHost + ":8084/");
        echo("to view this agent through the HTML protocol adaptor.");
        echo("\nOr, in another window, start a client application that connects");
        echo("to this agent by using an HTTP or RMI connector client");
        sleep(1000);
        echo("\nPress <Enter> to stop the agent...");
        waitForEnterPressed();

        // remove all registered MBeans
        agent.removeMBeans();
        System.exit(0);
    }

    // Handle the notification received
    public void handleNotification(javax.management.Notification notification, java.lang.Object handback) {
        echo("\n>>> RECEIVED Notification");
        echo("\tSOURCE = " + notification.getSource());
        echo("\tTYPE = " + notification.getType());
        echo("\tTIME STAMP = " + ((TimerNotification) notification).getTimeStamp());
    }

    /*
     * ------------------------------------------ PRIVATE METHODS ------------------------------------------
     */

    private void doDemo() {

        // build the simple MBean ObjectName
        //
        ObjectName mbeanObjectName = null;
        String domain = server.getDefaultDomain();
        String mbeanName = "com.sun.jdmk.cascading.CascadingAgent";
        try {
            mbeanObjectName = new ObjectName(domain + ":type=CascadingAgent");
        } catch (MalformedObjectNameException e) {
            echo("\t!!! Could not create the MBean ObjectName !!!");
            e.printStackTrace();
            echo("\nEXITING...\n");
            System.exit(1);
        }
        addHtmlAdaptor();

        createCascadingAgent(mbeanObjectName, mbeanName);

        doCascadingOperations();

    }

    private void addHtmlAdaptor() {

        echo("Instantiating an HTML protocol adaptor with port 8084 ...");

        HtmlAdaptorServer htmlAdaptor = new HtmlAdaptorServer(8084);

        try {
            // We let the html protocol adaptor provides its default name
            ObjectInstance htmlAdaptorInstance = server.registerMBean(htmlAdaptor, null);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        // Now we explicitly start the html protocol adaptor as it is not started automatically
        //
        htmlAdaptor.start();

        // waiting to leave starting state...
        while (htmlAdaptor.getState() == CommunicatorServer.STARTING) {
            sleep(1000);
        }

        // Check that the HTML adaptor server is started
        if (htmlAdaptor.getState() != CommunicatorServer.ONLINE) {
            echo("Cannot start the HTML adaptor server");
            echo("Check that the port is not already used");
            System.exit(1);
        }
    }

    private void createCascadingAgent(ObjectName mbeanObjectName, String mbeanName) {

        echo("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        echo("Creating the " + mbeanName + " MBean within the MBeanServer:");
        try {
            RmiConnectorAddress address = new RmiConnectorAddress(java.net.InetAddress.getLocalHost().getHostName(), 1099,
                    "name=RmiConnectorServer");
            // CascadingAgent remAgent = new ProxyCascadingAgent( address, "com.sun.jdmk.comm.RmiConnectorClient", new
            // ObjectName("CascadedDomain:*"), null);

            // service:jmx:rmi:///jndi/rmi://localhost:9999/server
            JMXServiceURL serviceURL = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:1099/test");
            MBeanServerConnectionFactory scf = ReconnectMBeanServerConnectionFactory.newInstance(serviceURL);
            ObjectName on = new ObjectName("CascadedDomain:*");
            CascadingAgent remAgent = new ProxyCascadingAgent(scf, on, on, null);

            ObjectInstance remAgentInstance = server.registerMBean(remAgent, mbeanObjectName);
            echo("\tCLASS NAME  = " + remAgentInstance.getClassName());
            echo("\tOBJECT NAME = " + remAgentInstance.getObjectName().toString());
            echo("Connected to:");
            address = (RmiConnectorAddress) server.getAttribute(mbeanObjectName, "Address");
            echo("\tPROTOCOL = " + address.getConnectorType());
            echo("\tHOST = " + address.getHost());
            echo("\tPORT = " + address.getPort());
            echo("\tSERVER NAME = " + address.getName());

            // Now we explicitly start the cascading agent as it is not started automatically
            //
            echo("\nStarting the cascading agent...");
            echo("\tIs ACTIVE       = " + server.getAttribute(mbeanObjectName, "Active"));
            sleep(1000);
            server.invoke(mbeanObjectName, "start", null, null);
            sleep(1000);
            echo("\tIs ACTIVE       = " + server.getAttribute(mbeanObjectName, "Active"));
            echo("done");
        } catch (Exception e) {
            echo("\t!!! Could not create the " + mbeanName + " MBean !!!");
            e.printStackTrace();
            echo("\nEXITING...\n");
            System.exit(1);
        }
    }

    private void doCascadingOperations() {

        echo("\nPress <Enter> to continue...\n");
        waitForEnterPressed();
        echo("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        echo(">>> Get Timer MBean \"CascadedDomain:type=timer\"");
        try {
            ObjectName timerName = new ObjectName("CascadedDomain:type=timer");
            ObjectInstance oi = server.getObjectInstance(timerName);

            echo("\n>>> Ask the Timer MBean to send a Timer Notification every 5 seconds ...");
            java.util.Date currentDate = new java.util.Date();
            Object pams[] = { "Timer", "Message", new Integer(5), new java.util.Date(currentDate.getTime() + new Long(2).longValue()),
                    new Long(1000) };

            String signatures[] = { "java.lang.String", "java.lang.String", "java.lang.Object", "java.util.Date", "long" };

            server.invoke(timerName, "addNotification", pams, signatures);
            server.invoke(timerName, "start", null, null);

            echo("\n>>> ADD LISTENER  to the Timer MBean");
            server.addNotificationListener(timerName, this, null, null);
            echo("Listener added");
            sleep(2000);
            echo("\nPress <Enter> to remove the listener from the Timer MBean ...");
            waitForEnterPressed();

            server.removeNotificationListener(timerName, this);

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

    }

    private void removeMBeans() {

        echo("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        try {
            echo("Stopping Cascading Agent\n");
            server.invoke(new ObjectName(server.getDefaultDomain() + ":type=CascadingAgent"), "stop", null, null);
            echo("Unregistering all MBeans except the MBean server delegate\n");
            echo("    Current MBean count = " + server.getMBeanCount() + "\n");

            Set allMBeans = server.queryNames(null, null);
            for (Iterator i = allMBeans.iterator(); i.hasNext();) {
                ObjectName name = (ObjectName) i.next();
                if (!name.toString().equals(ServiceName.DELEGATE)) {
                    echo("\tUnregistering " + name.toString());
                    server.unregisterMBean(name);
                }
            }
            echo("\n    Current MBean count = " + server.getMBeanCount() + "\n");
            echo("done\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void echo(String msg) {
        System.out.println(msg);
    }

    private static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            return;
        }
    }

    private static void waitForEnterPressed() {
        try {
            boolean done = false;
            while (!done) {
                char ch = (char) System.in.read();
                if (ch < 0 || ch == '\n') {
                    done = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * ------------------------------------------ PRIVATE VARIABLES ------------------------------------------
     */

    private MBeanServer server = null;

}
