package org.basic.net.c20_jmx.jdmk.current.BaseAgent;

/*
 * @(#)file      BaseAgent.java
 * @(#)author    Sun Microsystems, Inc.
 * @(#)version   1.19
 * @(#)lastedit  04/02/18
 *
 * Copyright 2004 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

// Java imports
//
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.Set;

// JMX imports
//
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectInstance;
import javax.management.ObjectName;

// Java DMK imports
//
import com.sun.jdmk.ServiceName;
import com.sun.jdmk.comm.CommunicatorServer;
import com.sun.jdmk.comm.HtmlAdaptorServer;

public class BaseAgent {

    private MBeanServer mbs;

    private static final String SEP_LINE = "\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"
            + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~";

    // Constructor:
    // - instantiantes the MBean server of this agent,
    // - retrieves the MBean server ID from the MBean server delegate.
    //
    public BaseAgent() {
        // Instantiates the MBean server
        //
        echo("\n\tInstantiating the MBean server of this agent...");
        mbs = MBeanServerFactory.createMBeanServer();
        echo("\tdone");

        // Retrieves ID of the MBean server from
        // the associated MBean server delegate
        //
        echo("\n\tGetting the ID of the MBean server from the " + "associated MBean server delegate ...");
        try {
            echo("\tID = " + mbs.getAttribute(new ObjectName(ServiceName.DELEGATE), "MBeanServerId"));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        echo("\tdone");
    }

    public static void main(String[] args) {
        // Instantiate the BaseAgent
        //
        echo(SEP_LINE);
        echo("Creating an instance of this BaseAgent...");
        BaseAgent myBaseAgent = new BaseAgent();
        echo("\ndone");

        // Add the HTML protocol adaptor
        //
        myBaseAgent.addHtmlAdaptor();

        // Wait for incoming connections until <Enter> is pressed
        //
        echo(SEP_LINE);
        String localHost;
        try {
            localHost = java.net.InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            localHost = "localhost";
        }
        echo("\nNow, you can point your browser to http://" + localHost + ":8082/");
        echo("to view this agent through the HTML protocol adaptor.");
        echo("\nPress <Enter> to stop the agent...");
        waitForEnterPressed();

        // Remove all the registered MBeans except the MBean server delegate
        //
        myBaseAgent.removeMBeans();

        echo(SEP_LINE);
        System.exit(0);
    }

    public void addHtmlAdaptor() {

        // *******************************
        // Adding an HTML protocol adaptor
        // *******************************

        // Below we illustrate one method of adding an MBean in an MBean server:
        // - first, we instantiate the HTML protocol adaptor
        // - then, we register it into the MBean server
        //
        echo(SEP_LINE);
        echo("Instantiating an HTML protocol adaptor with default port...");

        // Default constructor: sets port to default 8082
        //
        CommunicatorServer htmlAdaptor = new HtmlAdaptorServer();
        echo("\tPROTOCOL    = " + htmlAdaptor.getProtocol());
        echo("\tPORT        = " + htmlAdaptor.getPort());
        echo("done");

        echo("\nRegistering the HTML protocol adaptor, with default name, " + "in the MBean server...");
        try {
            // We let the HTML protocol adaptor provide its default name
            //
            ObjectInstance htmlAdaptorInstance = mbs.registerMBean(htmlAdaptor, null);
            echo("\tCLASS NAME  = " + htmlAdaptorInstance.getClassName());
            echo("\tOBJECT NAME = " + htmlAdaptorInstance.getObjectName().toString());
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        echo("done");

        // Now we explicitly start the HTML protocol adaptor as it is not
        // started automatically
        //
        echo("\nStarting the HTML protocol adaptor...");
        echo("\tSTATE       = " + htmlAdaptor.getStateString());
        htmlAdaptor.start();

        // Waiting to leave starting state...
        //
        while (htmlAdaptor.getState() == CommunicatorServer.STARTING) {
            echo("\tSTATE       = " + htmlAdaptor.getStateString());
            sleep(1000);
        }
        echo("\tSTATE       = " + htmlAdaptor.getStateString());

        // Check that the HTML protocol adaptor is started
        //
        if (htmlAdaptor.getState() != CommunicatorServer.ONLINE) {
            echo("Cannot start the HTML protocol adaptor.");
            echo("Check that the port is not already in use.");
            System.exit(1);
        }
        echo("done");
    }

    public void removeMBeans() {
        echo(SEP_LINE);
        try {
            echo("Unregistering all the registered MBeans except " + "the MBean server delegate\n");
            echo("    Current MBean count = " + mbs.getMBeanCount() + "\n");
            Set allMBeans = mbs.queryNames(null, null);
            for (Iterator i = allMBeans.iterator(); i.hasNext();) {
                ObjectName name = (ObjectName) i.next();
                if (!name.toString().equals(ServiceName.DELEGATE)) {
                    echo("\tUnregistering " + name.toString());
                    mbs.unregisterMBean(name);
                }
            }
            echo("\n    Current MBean count = " + mbs.getMBeanCount() + "\n");
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
            System.exit(0);
        }
    }
}
