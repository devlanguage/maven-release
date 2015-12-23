package org.basic.net.c20_jmx.jdmk.current.Cascading;
/*
 * @(#)file      MasterAgent.java
 * @(#)author    Sun Microsystems, Inc.
 * @(#)version   1.18
 * @(#)lastedit  04/05/03
 *
 * Copyright 2004 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

// Java imports
//
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Set;
import java.util.Iterator;

// JMX imports
//
import javax.management.MalformedObjectNameException;
import javax.management.MBeanServer;
import javax.management.MBeanServerInvocationHandler;
import javax.management.MBeanServerFactory;
import javax.management.NotificationListener;
import javax.management.Notification;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import javax.management.timer.TimerNotification;
import javax.management.timer.TimerMBean;

// JDMK imports
//
import com.sun.jdmk.ServiceName;
import com.sun.jdmk.comm.CommunicatorServer;
import com.sun.jdmk.comm.HtmlAdaptorServer;
import com.sun.jdmk.remote.cascading.CascadingService;
import com.sun.jdmk.remote.cascading.CascadingServiceMBean;

public class MasterAgent implements NotificationListener {

    /*
     * ------------------------------------------
     *  CONSTRUCTORS
     * ------------------------------------------
     */

    public MasterAgent(JMXServiceURL[] urls) {
        // CREATE the MBeanServer
        //
        server = MBeanServerFactory.createMBeanServer();
        subagentURLs = urls;
    }

    /*
     * ------------------------------------------
     *  PUBLIC METHODS
     * ------------------------------------------
     */

    private static void printline() {
        echo("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }

    public static void main(String[] args) {

        // Check parameters validity
        //
        if (args.length == 0) {
            echo("Syntax is: java MasterAgent <sub-agent-url>");
            System.exit(1);
        }

        // Check validity of SubAgent URLs
        //
        final JMXServiceURL urls[] = new JMXServiceURL[args.length];
	for (int i=0; i<args.length ; i++) {
	    try {
		urls[i] = new JMXServiceURL(args[i]);
		final JMXConnector c = 
		    JMXConnectorFactory.connect(urls[i], null);
		c.close();
	    } catch (Exception x) {
		echo("Cannot create connection with subagent#" + i +" at: " + 
		     args[i]);
		echo("\tError is: " + x);
		x.printStackTrace();
		System.exit(1);
	    }
	}

        // START
        //
        printline();
        echo("Creating an instance of this MasterAgent...");
        MasterAgent agent = new MasterAgent(urls);

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
        echo("\nNow, you can point your browser to http://" +
             localHost + ":8084/");
        echo("to view this agent through the HTML protocol adaptor.");
        echo("\nPress <Enter> to stop the agent...");
        waitForEnterPressed();

	// Stop the Cascading Service
	//
        agent.unmountAll();

	// Stop the protocol adaptors and the connectors
	//
	agent.stopAdaptorsAndConnectors();

        // Remove all registered MBeans
	//
        agent.removeMBeans();
    }

    /**
     * Handle the received notification
     */
    public void handleNotification(Notification notification, 
				   Object handback) {
        echo("\n>>> RECEIVED Notification");
        echo ("\tSOURCE = " + notification.getSource());
        echo ("\tTYPE = " + notification.getType());
        echo ("\tTIMESTAMP = " + notification.getTimeStamp());
    }

    /*
     * ------------------------------------------
     *  PRIVATE METHODS
     * ------------------------------------------
     */

    private void doDemo() {

        addHtmlAdaptor();

        createCascadingService();

	mountSubagents(subagentURLs);

        doCascadingOperations();
    }

    private void addHtmlAdaptor() {

	final int htmlPort = 
	    Integer.parseInt(System.getProperty("html.port","8084"));
        echo("Instantiating an HTML protocol adaptor with port " + htmlPort);

	htmlAdaptor = new HtmlAdaptorServer(htmlPort);

        try {
            // We let the HTML protocol adaptor provide its default name
	    //
            ObjectInstance htmlAdaptorInstance =
                server.registerMBean(htmlAdaptor, null);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        // Now we explicitly start the HTML protocol adaptor as it is
        // not started automatically
        //
        htmlAdaptor.start();

        // Waiting to leave starting state...
	//
        while (htmlAdaptor.getState() == CommunicatorServer.STARTING) {
            sleep(1000);
        }

        // Check that the HTML adaptor server is started
	//
        if (htmlAdaptor.getState() != CommunicatorServer.ONLINE) {
            echo("Cannot start the HTML adaptor server");
            echo("Check that the port is not already in use");
	    echo("or specify a different port number with the Java System "+
		 " Property -Dhtml.port=<html-port#>");
            System.exit(1);
        }
    }

    private void createCascadingService() {

        printline();
        echo("Creating the CascadingService" +
             " MBean within the MBeanServer:");
        try {
	    // We create a CascadingServiceMBean that we register in our
	    // local MBeanServer. Our local MBeanServer thus becomes the
	    // target MBeanServer of this CascadingServiceMBean.
	    //
	    // We use a null ObjectName, which means that the CascadingService
	    // will be registered under its default name:
	    //   CascadingServiceMBean.CASCADING_SERVICE_DEFAULT_NAME
	    //
            CascadingService service  = new CascadingService();
            ObjectInstance   cascadingInstance =
                server.registerMBean(service, null);
            echo("\tCLASS NAME  = " + cascadingInstance.getClassName());
            echo("\tOBJECT NAME = " + cascadingInstance.getObjectName());
	    cascadingService = service;
        } catch (Exception e) {
            echo("\t!!! Could not create the " +
                 CascadingService.class.getName() + " MBean !!!");
            e.printStackTrace();
            echo("\nEXITING...\n");
            System.exit(1);
        }
    }

    private void mountSubagents(JMXServiceURL[] agentURLs) {
	// We re going to mount all subagents whose JMXServiceURL were given
	// as arguments.
	// 
	// If the subagents are not accessible, the mount() operation will
	// throw an IOException. We do not expect any here however because
	// we already checked that we could connect to the given URLs.
	// We could stil get an IOException if e.g. one subagent went down
	// since we checked its URL.
	// 
	mountPoints = new String[agentURLs.length];
	for (int i=0;i<agentURLs.length;i++) {
	    try {
		// Mount subagent#(i+1) - start numbering at 1 - under the
		// "subagents/agent<i+1>" target path.
		// We use "ExportDomain:*" as filter because we only want
		// to see the MBeans that are exported in the source 
		// subagent under ExportDomain.
		//
		final String mountPointID =
		    cascadingService.mount(agentURLs[i],null,
					   new ObjectName("ExportDomain:*"),
					   "subagents/agent"+(i+1));

		// Store the mountPointID for further use.
		//
		mountPoints[i] = mountPointID;
		echo(mountPointID);
	    } catch (Exception x) {
		echo("\t!!! Could not mount subagent#"+(i+1)+" at " + 
		     agentURLs[i] + "\n\tError is: " + x);
		x.printStackTrace();
		echo("\nEXITING...\n");
		System.exit(1);
	    }
	}
    }

    private void unmountAll() {
        printline();

	// We first unregister the cascading service MBean so that it is
	// no longer accessible - we do not want anybody to fiddle with
	// it while we unmount everything.
	//
	echo("Unregistering CascadingServiceMBean");

	// Unregister the cascading service MBean
	//
	try {
	    server.unregisterMBean(CascadingServiceMBean.
				   CASCADING_SERVICE_DEFAULT_NAME);
	} catch (Exception x) {
	    echo("\t!!! Could not unregister " + CascadingServiceMBean.
		 CASCADING_SERVICE_DEFAULT_NAME + "\n\tError is: " + x);
	    x.printStackTrace();
	    echo("\nEXITING...\n");
	    System.exit(1);
	} 

	echo("Unmounting all mount points");

	// Now we get all current mountpoint ids. We are sure that 
	// nobody is going to mount new subagents while we're doing this
	// because the CascadingServiceMBean is no longer accessible (it
	// is no longer registered in the target MBeanServer). So only
	// this MasterAgent object can access it.
	//
	final String mounts[] = cascadingService.getMountPointIDs();

	// Unmount all ids. For each returned mountPointID, call
	// CascadingServiceMBean.unmount(mountPointID);
	//
	for (int i=0;i<mounts.length;i++) {
	    try {
		if (cascadingService.unmount(mounts[i]))
		    echo("unmounted "+mounts[i]);
	    } catch (Exception x) {
		echo("\t!!! Could not unmount " + mounts[i] +
		     "\n\tError is: " + x);
		x.printStackTrace();
		echo("\nEXITING...\n");
		System.exit(1);
	    }
	}
    }

    private void doCascadingOperations() {

        echo("\nPress <Enter> to continue...\n");
        waitForEnterPressed();
        printline();
        try {
	    // We're going to register a listener on a timer MBean residing
	    // in the source subagent#1, mounted under the path 
	    // "subagents/agent1". 
	    // We first check that this agent is still mounted - it could 
	    // have been already unmounted if the subagent had stopped, or
	    // if there were a network failure. 
	    //
	    // Another means to know when subagents are unmounted is to 
	    // register with the CascadingService for:
	    //   * CascadingServiceMBean.CASCADING_STOPPED_NOTIFICATION
	    //   * CascadingServiceMBean.CASCADING_FAILED_NOTIFICATION
	    // (See CascadingService Java Documentation).
	    //
	    // Here we will simply call CascadingServiceMBean.isMounted().
	    //
	    // mountPoints[0] is the mountPointID that was returned by
	    // CascadingServiceMBean.mount() when we have mounted the
	    // subagent#1
	    //
	    if (! cascadingService.isMounted(mountPoints[0])) {
		echo(mountPoints[0] + ": \n\t" + "not mounted! ");
		echo("Cannot do cascading operations.");
		return;
	    }

	    // That's the target name of the mounted MBean in the target
	    // MBeanServer. If you look at the subagent#1, you will see
	    // that the source name of this MBean in the source subagent#1
	    // MBeanServer is "ExportDomain:type=Timer".
	    //
            ObjectName timerName =
                new ObjectName("subagents/agent1/ExportDomain:type=Timer");
	    echo(">>> Get Timer MBean \""+timerName+"\"");
	    
	    // Get a handle on the source TimerMBean, through our local
	    // MBeanServer (the CascadingService target). We can access
	    // the source MBean just as if it were a local MBean residing
	    // in our local MBeanServer. In reallity all our calls are 
	    // going to be automagically forwarded to the source MBean
	    // named "ExportDomain:type=Timer" in the subagent#1.
	    //
	    // The use of MBeanServerInvocationHandler here is just a 
	    // conveniency. We could as well have dealt directly with
	    // the generic MBeanServer interface "invoke" method.
	    //
	    TimerMBean timer = (TimerMBean) MBeanServerInvocationHandler.
		newProxyInstance(server, timerName, TimerMBean.class, true);

            echo("\n>>> Ask the Timer MBean to send a " +
                 "Timer Notification every 3 seconds");
            Date currentDate = new Date();

            timer.addNotification("Timer","Message",null,
				  new Date(currentDate.getTime() + 2),3000);

            timer.start();

            echo("\n>>> Add listener to the Timer MBean");
            server.addNotificationListener(timerName, this, null, null);
            echo("\tListener added successfully");
            echo("\nPress <Enter> to remove the listener from the " +
		 "Timer MBean...");
            waitForEnterPressed();

	    // Check whether the subagent is still mounted. The subagent 
	    // could already be unmounted if e.g. it has been stopped.
	    //
	    if (cascadingService.isMounted(mountPoints[0])) {
		try {
		    // If the subagent is still mounted, unregister our
		    // listener.  
		    //
		    server.removeNotificationListener(timerName, this);
		} catch (Exception x) {
		    echo("Unexpected exception while unregistering listener:");
		    echo("\tError is: " + x);
		}
	    } else {
		// Subagent already unmounted: no need to unregister listener!
		// The source MBean is no longer mounted in our local 
		// MBeanServer - the unmount operation has already 
		// unregistered its proxy. Any attempt to access it would
		// thus throw an InstanceNotFoundException.
		//
		echo(mountPoints[0] + ": \n\t" + "already unmounted! ");
	    }

	} catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void stopAdaptorsAndConnectors() {

        printline();
	echo("Stopping Adaptors and Connectors\n");

	try {

	    // **********************************
	    // Stopping the HTML protocol adaptor
	    // **********************************

	    htmlAdaptor.stop();

        } catch (Exception e) {
            e.printStackTrace();
        }

	echo("done\n");
    }

    private void removeMBeans() {
        printline();
        try {
            echo("Unregistering all the MBeans except the " +
                 "MBean server delegate\n");
            echo("    Current MBean count = " + server.getMBeanCount() + "\n");

            Set allMBeans = server.queryNames(null, null);
            for (Iterator i = allMBeans.iterator(); i.hasNext(); ) {
                ObjectName name = (ObjectName) i.next();
                if (!name.toString().equals(ServiceName.DELEGATE)) {
                    echo("\tUnregistering " + name.toString());
                    server.unregisterMBean(name);
                }
            }
            echo("\n    Current MBean count = " +
                 server.getMBeanCount() + "\n");
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
     * ------------------------------------------
     *  PRIVATE VARIABLES
     * ------------------------------------------
     */

    private HtmlAdaptorServer htmlAdaptor;
    private CascadingService  cascadingService;
    private JMXServiceURL[]   subagentURLs;
    private String       []   mountPoints;
    private MBeanServer       server;
}
