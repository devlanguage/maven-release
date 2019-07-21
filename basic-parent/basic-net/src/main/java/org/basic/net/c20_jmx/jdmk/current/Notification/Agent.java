package org.basic.net.c20_jmx.jdmk.current.Notification;

/*
 * @(#)file Agent.java @(#)author Sun Microsystems, Inc. @(#)version 1.8 @(#)lastedit 04/02/02
 * 
 * Copyright 2004 Sun Microsystems, Inc. All rights reserved. SUN PROPRIETARY/CONFIDENTIAL. Use is
 * subject to license terms.
 */

// Java imports
//
import java.io.IOException;



// JMX imports
//
import javax.management.ObjectName;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;

import org.basic.net.c20_jmx.mbean.simple.SimpleStandard;
import org.basic.net.c20_jmx.mbean.simple.SimpleStandardListener;


// Java DMK imports
//
import com.sun.jdmk.ServiceName;
import com.sun.jdmk.comm.HtmlAdaptorServer;

public class Agent {

    /*
     * ------------------------------------------ CONSTRUCTORS
     * ------------------------------------------
     */

    public Agent() {

        // Instantiates the MBean server
        //
        echo("\n\tInstantiating the MBean server of this agent...");
        server = MBeanServerFactory.createMBeanServer();
        echo("\tdone");

        // Add the HTML adaptor
        //
        echo("\n\tInstantiating and starting the HTML adaptor on port 8082...");
        try {
            ObjectName htmlObjName = new ObjectName(":type=HtmlAdaptorServer,protocol=html,port="
                    + htmlPort);
            HtmlAdaptorServer htmlAdaptor = new HtmlAdaptorServer(htmlPort);
            server.registerMBean(htmlAdaptor, htmlObjName);
            htmlAdaptor.start();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        echo("\tdone");
    }

    /*
     * ------------------------------------------ PUBLIC METHODS
     * ------------------------------------------
     */

    public static void main(String[] args) {

        // START
        //
        echo(SEP_LINE);
        echo("Creating an instance of this Agent...");
        Agent myAgent = new Agent();

        AgentListener agentListener = null;
        SimpleStandard simpleStd = null;
        ObjectName simpleStdObjectName = null;
        SimpleStandardListener simpleStdListener = null;

        // Add a notification listener to the MBean server delegate
        // The MBean server delegate will emit MBean server notifications
        // and the registered listener will receive notifications of type:
        // - "JMX.mbean.registered" when an MBean has been registered.
        // - "JMX.mbean.unregistered" when an MBean has been unregistered.
        //
        echo("\nRegistering the MBean server delegate listener...");
        try {
            agentListener = new AgentListener();
            myAgent.server.addNotificationListener(new ObjectName(ServiceName.DELEGATE),
                    agentListener, null, null);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        echo("done");

        echo("\n\nNOW WAITING FOR THE MBEAN SERVER NOTIFICATIONS...");
        echo(SEP_LINE);

        // Add a Simple Standard MBean
        //
        // A notification of type "JMX.mbean.registered" will be emitted
        // by the MBean server delegate
        // 
        echo("\nPress <Enter> to register the SimpleStandard MBean...");
        waitForEnterPressed();
        try {
            simpleStdObjectName = new ObjectName("simple_mbean:type=SimpleStandard");
            simpleStd = new SimpleStandard();
            myAgent.server.registerMBean(simpleStd, simpleStdObjectName);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        echo(SEP_LINE);

        // Add a notification listener to the Simple Standard MBean
        //
        // The Simple Standard MBean will emit attribute change notifications
        // and the registered listener will receive notifications of type:
        // - "jmx.attribute.change"
        //
        echo("\nRegistering the Simple Standard MBean listener...");
        try {
            simpleStdListener = new SimpleStandardListener();
            myAgent.server.addNotificationListener(simpleStdObjectName, simpleStdListener, null,
                    null);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        echo("done");

        echo("\n\nNOW WAITING FOR THE SIMPLE STANDARD MBEAN NOTIFICATIONS...");
        echo("You can use the HTML adaptor to manipulate the Simple Standard");
        echo("MBean. Every time you invoke the reset operation an attribute ");
        echo("change notification should be emitted by the MBean.");
        echo(SEP_LINE);

        // Remove the Simple Standard MBean
        //
        // A notification of type "JMX.mbean.unregistered" will be emitted
        // by the MBean server delegate
        // 
        echo("\nPress <Enter> to unregister the SimpleStandard MBean...");
        waitForEnterPressed();
        try {
            myAgent.server.unregisterMBean(simpleStdObjectName);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        echo("\nPress <Enter> to stop the agent and terminate...");
        waitForEnterPressed();
        echo(SEP_LINE);

        // END
        //
        System.exit(0);
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
     * ------------------------------------------ PRIVATE VARIABLES
     * ------------------------------------------
     */

    private MBeanServer server = null;
    private static final int htmlPort = 8082;
    private static final String SEP_LINE = "\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"
            + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~";
}
