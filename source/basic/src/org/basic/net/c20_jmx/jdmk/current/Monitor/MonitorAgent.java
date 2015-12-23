package org.basic.net.c20_jmx.jdmk.current.Monitor;
/*
 * @(#)file      MonitorAgent.java
 * @(#)author    Sun Microsystems, Inc.
 * @(#)version   1.18
 * @(#)lastedit  04/02/02
 *
 * Copyright 2004 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

// Java imports
//
import java.io.IOException;

// JMX imports
//
import javax.management.*;
import javax.management.monitor.*;

public class MonitorAgent {

    /*
     * ------------------------------------------
     *  CONSTRUCTORS
     * ------------------------------------------
     */

    public MonitorAgent() {
        server = MBeanServerFactory.createMBeanServer();
        listener = new AgentListener();
        standardObsObj = new StandardObservedObject();
        dynamicObsObj = new DynamicObservedObject();
        counterMonitor = new CounterMonitor();
    }

    /*
     * ------------------------------------------
     *  PUBLIC METHODS
     * ------------------------------------------
     */

    public static void main(String[] args) {

        echo(SEP_LINE);

        echo("\n>>> CREATE and START the Agent...");
        MonitorAgent agent = new MonitorAgent();
        echo("\nPress <Enter> to continue...\n");
        waitForEnterPressed();

        // Get the domain name from the MBeanServer.
        //
	domain = server.getDefaultDomain();

        // Initialize the CounterMonitor MBean.
        //
        if (agent.initializeCounter() != 0) {
            echo("\n>>> An error occurred when initializing " +
		 "the CounterMonitor MBean...");
            System.exit(1);
        }

        // Initialize the Standard Observed MBean and it
	// to the CounterMonitor's observed object list.
        //
        if (agent.initializeStandardMBean() != 0) {
            echo("\n>>> An error occurred when initializing " +
		 "the Standard Observed MBean...");
            System.exit(1);
        }

        // Initialize the Dynamic Observed MBean and it
	// to the CounterMonitor's observed object list.
        //
        if (agent.initializeDynamicMBean() != 0) {
            echo("\n>>> An error occurred when initializing " +
		 "the Dynamic Observed MBean...");
            System.exit(1);
        }

        echo("\nPress <Enter> to continue...\n");
        waitForEnterPressed();

	// Start the Counter Monitor MBean.
	//
	agent.startCounter();

        // Create 4 instances of Simple MBean.
        //
        agent.populateAgent(4);

	// SHOULD GET NOTIFICATION AS THRESHOLD HAS BEEN EXCEEDED
	//
	// MBean # = 8 = 1 (MBeanServerDelegate) +
	//               1 (CounterMonitorMBean) +
	//               1 (StandardObservedMBean) +
	//               1 (DynamicObservedMBean) +
	//               4 (SimpleMBean)
	//
	// NEW THRESHOLD VALUE = 10 = 5 (Threshold) + 5 (Offset)

        // Create 6 instances of Simple MBean.
        //
        agent.populateAgent(6);

	// SHOULD GET NOTIFICATION AS THRESHOLD HAS BEEN EXCEEDED
	//
	// MBean # = 14 = 1 (MBeanServerDelegate) +
	//                1 (CounterMonitorMBean) +
	//                1 (StandardObservedMBean) +
	//                1 (DynamicObservedMBean) +
	//                10 (SimpleMBean)
	//
	// NEW THRESHOLD VALUE = 15 = 10 (Threshold) + 5 (Offset)

	// Stop the Counter Monitor MBean.
	//
	agent.stopCounter();

        echo("\n>>> STOP the Agent...\n");
        System.exit(0);
    }

    /*
     * ------------------------------------------
     *  PRIVATE METHODS
     * ------------------------------------------
     */

    private int initializeCounter() {

        echo(SEP_LINE);

        // Create a new CounterMonitor MBean and add it to the MBeanServer.
        // 
        echo("\n>>> CREATE a new CounterMonitor MBean:");
        try {
            counterMonitorName =
		new ObjectName(domain + ":type=" +
			       CounterMonitor.class.getName());
        } catch (MalformedObjectNameException e) {
            e.printStackTrace();
            return 1;
        }
        echo("\tOBJECT NAME = " + counterMonitorName);
        try {
            server.registerMBean(counterMonitor, counterMonitorName);
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }

        // Register a CounterMonitor notification listener with the
	// CounterMonitor MBean, enabling the MonitorAgent to receive
	// notifications emitted by the the CounterMonitor MBean.
        //
        echo("\n>>> ADD a listener to the CounterMonitor...");
        try {
            counterMonitor.addNotificationListener(listener, null, null);
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }

        // Initialize the attributes of the CounterMonitor MBean.
        //
        Integer threshold = new Integer(5);
        Integer offset = new Integer(5);
        echo("\n>>> SET the attributes of the CounterMonitor:");
        try {
            counterMonitor.setObservedAttribute("NbObjects");
            echo("\tATTRIBUTE \"ObservedAttribute\" = NbObjects");

            counterMonitor.setNotify(true);
            echo("\tATTRIBUTE \"Notify\"            = true");

            counterMonitor.setInitThreshold(threshold);
            echo("\tATTRIBUTE \"Threshold\"         = " + threshold);

            counterMonitor.setOffset(offset);
            echo("\tATTRIBUTE \"Offset\"            = " + offset);

            counterMonitor.setGranularityPeriod(500);
            echo("\tATTRIBUTE \"GranularityPeriod\" = 500 ms");
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }

        return 0;
    }

    private int startCounter() {
        echo("\n>>> START the CounterMonitor...");
        counterMonitor.start();
        return 0;
    }

    private int stopCounter() {
        echo("\n>>> STOP the CounterMonitor...");
        counterMonitor.stop();
        return 0;
    }

    private int initializeStandardMBean() {

        echo(SEP_LINE);

        // Create a new StandardObservedObject MBean and add it
	// to the MBeanServer.
        //
        echo("\n>>> CREATE a new StandardObservedObject MBean:");
        try {
            standardObsObjName =
		new ObjectName(domain + ":type=" +
			       StandardObservedObject.class.getName());
        } catch (MalformedObjectNameException e) {
            e.printStackTrace();
            return 1;
        }
        echo("\tOBJECT NAME = " + standardObsObjName);
        try {
            server.registerMBean(standardObsObj, standardObsObjName);
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }

        // Access the attributes of the StandardObservedObject MBean
	// and get the initial attribute values.
        //
        echo("\tATTRIBUTE \"NbObjects\" = " + standardObsObj.getNbObjects());

        echo(SEP_LINE);

        // Add the StandardObservedObject MBean to the CounterMonitor MBean.
        //
        echo("\n>>> ADD the StandardObservedObject MBean " +
	     "to the CounterMonitor:");
        try {
            counterMonitor.addObservedObject(standardObsObjName);
            echo("\tATTRIBUTE \"ObservedObject\" = " + standardObsObjName);
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }

        return 0;
    }

    private int initializeDynamicMBean() {

        echo(SEP_LINE);

        // Create a new DynamicObservedObject MBean and add it
	// to the MBeanServer.
        //
        echo("\n>>> CREATE a new DynamicObservedObject MBean:");
        try {
            dynamicObsObjName =
		new ObjectName(domain + ":type=" +
			       DynamicObservedObject.class.getName());
        } catch (MalformedObjectNameException e) {
            e.printStackTrace();
            return 1;
        }
        echo("\tOBJECT NAME = " + dynamicObsObjName);
        try {
            server.registerMBean(dynamicObsObj, dynamicObsObjName);
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }

        // Access the attributes of the DynamicObservedObject MBean
	// and get the initial attribute values.
        //
        MBeanAttributeInfo[] attributes =
	    dynamicObsObj.getMBeanInfo().getAttributes();
        for (int i = 0; i < attributes.length; i++) {
            try {
                echo("\tATTRIBUTE \"" + attributes[i].getName() + "\" = " +
		     dynamicObsObj.getAttribute(attributes[i].getName()));
            } catch (Exception e) {
                e.printStackTrace();
                return 1;
            }
        }

        echo(SEP_LINE);

        // Add the DynamicObservedObject MBean to the CounterMonitor MBean.
        //
        echo("\n>>> ADD the DynamicObservedObject MBean " +
	     "to the CounterMonitor:");
        try {
            counterMonitor.addObservedObject(dynamicObsObjName);
            echo("\tATTRIBUTE \"ObservedObject\" = " + dynamicObsObjName);
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }

        return 0;
    }

    private void populateAgent(int mbeans) {

        echo(SEP_LINE);

	echo("\n>>> PRESS <Enter> TO START THE CREATION OF " +
	     mbeans + " SIMPLE MBEANS...");
	waitForEnterPressed();

        // Create instances of Simple MBeans
	// and add them to the MBeanServer.
        //
        for (int i = 0; i < mbeans; i++) {
            try {
                server.createMBean(Simple.class.getName(),
				   ObjectName.getInstance(domain +
							  ":type=Simple," +
							  "index=" + index++));
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
            echo(">>> CREATE a new Simple MBean => " + getNbObjects() +
		 " NbObjects in the MBeanServer");

            // Wait a few seconds for the monitor to update.
            //
	    sleep(1000);
        }
    }

    private int getNbObjects() {
        try {
            return server.queryMBeans(new ObjectName("*:*"), null).size();
        } catch (Exception e) {
            return (-1);
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
     * ------------------------------------------
     *  PRIVATE VARIABLES
     * ------------------------------------------
     */
    
    private int index = 0;
    private static String domain = null;
    private static MBeanServer server = null;
    private AgentListener listener = null;
    private CounterMonitor counterMonitor = null;
    private StandardObservedObject standardObsObj = null;
    private DynamicObservedObject dynamicObsObj = null;
    private ObjectName standardObsObjName = null;
    private ObjectName dynamicObsObjName = null;
    private ObjectName counterMonitorName = null;

    private static final String SEP_LINE =
        "\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" +
        "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~";
}
