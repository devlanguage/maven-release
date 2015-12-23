package org.basic.net.c20_jmx.jdmk.legacy.SimpleClients;

/*
 * @(#)file      BaseAgent.java
 * @(#)author    Sun Microsystems, Inc.
 * @(#)version   1.9
 * @(#)lastedit  04/01/19
 *
 * Copyright 2004 Sun Microsystems, Inc.  All rights reserved. Use is subject to license terms.
 */

// java imports
//
import java.io.IOException;
import java.net.UnknownHostException;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectInstance;
// JMX imports
//
import javax.management.ObjectName;

import com.sun.jdmk.comm.CommunicatorServer;
import com.sun.jdmk.comm.HtmlAdaptorServer;
import com.sun.jdmk.comm.RmiConnectorServer;
// Java DMK imports
//

public class BaseAgent {

    private MBeanServer myMBeanServer;

    // Constructor:
    // - sets the trace level,
    // - instantiantes the MBean server of this agent.
    //
    public BaseAgent() {

        // Enable the TRACE level according to the level set in system properties
        // (specified at command line, e.g. java -DLEVEL_TRACE BaseAgent)

        // Instantiates the MBean server
        //
        myMBeanServer = MBeanServerFactory.createMBeanServer();
    }

    public static void main(String[] args) {

        // START
        //
        echo("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        echo("Creating an instance of this BaseAgent...");
        BaseAgent myBaseAgent = new BaseAgent();

        // ADD OUR SELECTION OF PROTOCOL ADAPTORS AND CONNECTORS
        //
        myBaseAgent.addAdaptorsAndConnectors();

        // END
        //
        echo("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        String localHost;
        try {
            localHost = java.net.InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            localHost = "localhost";
        }
        echo("\nNow, you can point your browser to http://" + localHost + ":8082/");
        echo("to view this agent through the HTML protocol adaptor.");
        echo("\nOr, in another window, start a client application that connects");
        echo("to this agent by using an RMI connector client");
        echo("\nPress <Enter> to stop the agent...\n");
        waitForEnterPressed();
        System.exit(0);

    }

    public void addAdaptorsAndConnectors() {

        // *******************************
        // Adding an HTML protocol adaptor
        // *******************************

        // Below we illustrate one method of adding an MBean in an MBean server:
        // - first, we instantiate the HTML protocol adaptor
        // - then, we register it into the MBean server
        //
        echo("Instantiating an HTML protocol adaptor with default port...");

        CommunicatorServer htmlAdaptor = new HtmlAdaptorServer();

        try {
            // We let the html protocol adaptor provides its default name
            ObjectName htmlAdaptorObjectName = null;

            ObjectInstance htmlAdaptorInstance = myMBeanServer.registerMBean(htmlAdaptor, htmlAdaptorObjectName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Now we explivitly start the html protocol adaptor as it is not started automatically
        //
        htmlAdaptor.start();

        // waiting to leave starting state...
        while (htmlAdaptor.getState() == CommunicatorServer.STARTING) {
            sleep(1000);
        }

        // ******************************
        // Adding an RMI connector server
        // ******************************

        echo("Instantiating an RMI connector server with default port...");

        CommunicatorServer rmiConnector = new RmiConnectorServer();

        try {
            // We let the RMI connector server provides its default name
            ObjectName rmiConnectorName = null;

            ObjectInstance rmiConnectorInstance = myMBeanServer.registerMBean(rmiConnector, rmiConnectorName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Now we explivitly start the RMI connector server as it is not started automatically
        //
        rmiConnector.start();

        // waiting to leave starting state...
        while (rmiConnector.getState() == CommunicatorServer.STARTING) {
            sleep(1000);
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

}
