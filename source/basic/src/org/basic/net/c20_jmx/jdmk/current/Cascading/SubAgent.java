package org.basic.net.c20_jmx.jdmk.current.Cascading;
/*
 * @(#)file      SubAgent.java
 * @(#)author    Sun Microsystems, Inc.
 * @(#)version   1.15
 * @(#)lastedit  05/11/22
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
import javax.management.JMException;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;
import javax.management.timer.Timer;

import org.basic.net.c20_jmx.mbean.simple.SimpleStandard;

// JDMK imports
//
import com.sun.jdmk.ServiceName;
import com.sun.jdmk.comm.CommunicatorServer;
import com.sun.jdmk.comm.HtmlAdaptorServer;

public class SubAgent {

    private MBeanServer myMBeanServer;
    private JMXConnectorServer jmxServer;
    private CommunicatorServer htmlAdaptor;

    // Constructor:
    //   - instantiantes the MBean server of this agent
    //   - retrieves the MBean server ID from the MBean server delegate
    //
    public SubAgent() {

        // Instantiates the MBean server
        //
        myMBeanServer = MBeanServerFactory.createMBeanServer();
    }

    private static void printline() {
        echo("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }

    public static void main(String[] args) {
        try {
            // START
            //
            printline();
            echo("Creating an instance of this SubAgent...");
            SubAgent mySubAgent = new SubAgent();

            // ADD OUR SELECTION OF PROTOCOL ADAPTORS AND CONNECTORS
            //
            mySubAgent.addAdaptorsAndConnectors();

            // Create a SimpleStandard MBean and a Timer MBean
	    //
            mySubAgent.addMBeans();

            // END
            //
            printline();

	    final int htmlPort = 
		Integer.parseInt(System.getProperty("html.port","8082")); 
            String localHost;

            try {
                localHost = java.net.InetAddress.getLocalHost().getHostName();
            } catch (UnknownHostException e) {
                localHost = "localhost";
            }
            echo("\nNow, you can point your browser to http://" +
                 localHost + ":"+htmlPort+"/");
            echo("to view this agent through the HTML protocol adaptor.");
            echo("\nOr, in another window, start a client application" +
                 " that connects");
            echo("to this agent by using the JMXServiceURL: " +
                 mySubAgent.getAddress());
            echo("\nPress <Enter> to stop the agent...");
            waitForEnterPressed();

	    // Stop the protocol adaptors and the connectors
	    //
	    mySubAgent.stopAdaptorsAndConnectors();
	    
            // Remove the MBeans that we have added
	    // (i.e. the adaptors and connectors)
            // removes all the MBeans except the delegate
            //
            mySubAgent.removeMBeans();

            printline();
        } catch (Exception x) {
            echo("Unexpected exception: " + x);
            x.printStackTrace();
            System.exit(1);
        }
    }

    public JMXServiceURL getAddress() throws IOException {
        if (jmxServer == null) return null;
        return jmxServer.getAddress();
    }

    public void addAdaptorsAndConnectors() throws IOException {

        // *******************************
        // Adding an HTML protocol adaptor
        // *******************************

        // Below we illustrate one method of adding an MBean in an MBean
        // server:
	//   - first, we get the HTML port number to use (default is 8082)
        //   - first, we instantiate the HTML protocol adaptor
        //   - then, we register it into the MBean server
        //

	final int htmlPort = 
	    Integer.parseInt(System.getProperty("html.port","8082"));
        echo("Instantiating an HTML protocol adaptor with port " + htmlPort);
	
        // sets port to htmlPort (default is 8082)
        //
        htmlAdaptor = new HtmlAdaptorServer(htmlPort);

        try {
            // We let the HTML protocol adaptor provide its default name
            //
            ObjectInstance htmlAdaptorInstance =
                myMBeanServer.registerMBean(htmlAdaptor, null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("Failed to start HTML adaptor: " + e);
        }

        // Now we explicitly start the HTML protocol adaptor as it is not
        // started automatically
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
            throw new IOException("Failed to start HTML adaptor: "
                                  + htmlAdaptor.getState());
        }

        // Use url=xxx System Property - if there is no URL, then
        // use service:jmx:jmxmp://
        //
        final String defaultURL = "service:jmx:jmxmp://";
        final JMXServiceURL url =
            new JMXServiceURL(System.getProperty("url", defaultURL));

        // *****************************
        // Adding a JMX connector server
        // *****************************

        // Create a JMX Connector Server from the input URL. The input
        // URL may not be the actual address at which the connector
        // server will be listening: for instance, if the protocol is
        // "jmxmp" and the port number is "0" or is omitted, the server
        // will be listening at the first available port number (which
        // will be greater than 0). There are many other examples where
        // the input URL is not exactly the URL that will be used for
        // connections. See the Java Documentation of the
        // javax.management.remote packages for more details.
        //
        echo("Instantiating a JMX Connector Server using input url: " + url);
        jmxServer =
            JMXConnectorServerFactory.newJMXConnectorServer(url,
                                                            null,
                                                            myMBeanServer);

        // We start the server before registering it in the MBeanServer.
        // We can do so because we have supplied the `myMBeanServer'
        // MBeanServer to the JMXConnectorServerFactory when creating
        // the JMXConnectorServer.
        //
        echo("Starting the JMX Connector Server");
        jmxServer.start();

        // Now that the server is started, we can know its actual final
        // address: this is the JMXServiceURL that client will be able
        // to use to connect to the JMXConnectorServer.
        // Note that in some deterministic cases (use of an external
        // directory for rmi/iiop URLs, or supplying a given port for JMXMP)
        // that URL will be equal to the input URL used to create the
        // server. However, it is not always the case, in particular
        // if the input URL was not fully deterministic.
        //
        final JMXServiceURL serverAddress = jmxServer.getAddress();

        try {
            // We provide an object name for the JMX connector server
            // We use some of the characteristics of the actual address
            // to construct this ObjectName. Note however that the
            // information serverAddress.getHost() and
            // serverAddress.getPort() are not always meaningful (JMXMP
            // URLs excepted)
            //
            ObjectName jmxServerName = new ObjectName(
                          ServiceName.DOMAIN + ":" +
                          "type=" + jmxServer.getClass().getName() +
                          ",jmxProtocolType=" + serverAddress.getProtocol() +
                          ",jmxAgentHost=" + serverAddress.getHost() +
                          ",port=" + serverAddress.getPort());
            ObjectInstance jmxConnectorInstance =
                myMBeanServer.registerMBean(jmxServer,jmxServerName);
        } catch (JMException e) {
            e.printStackTrace();
            throw new IOException("Failed to register JMX Connector Server: "
                                  + e);
        }
        echo("JMX Connector Server up and running at: " + serverAddress);
    }

    public void stopAdaptorsAndConnectors() throws IOException {

        printline();
	echo("Stopping Adaptors and Connectors\n");

        // **********************************
        // Stopping the HTML protocol adaptor
        // **********************************

	htmlAdaptor.stop();

        // *******************************
        // Stopping a JMX connector server
        // *******************************

	jmxServer.stop();

	echo("done\n");
    }

    public void addMBeans() {
        printline();
        echo("Registering a SimpleStandard MBean...");

        SimpleStandard simple = new SimpleStandard(); // default constructor
        try {
            ObjectName simpleName =
                new ObjectName(myMBeanServer.getDefaultDomain() + ":" +
                               "type=SimpleStandard");
            ObjectInstance simpleObjectInstance =
                myMBeanServer.registerMBean(simple, simpleName);
            echo("\tOBJECT NAME = " +
                 simpleObjectInstance.getObjectName().toString());
            echo("done");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        printline();
        echo("Registering a Timer MBean...");

        Timer timer = new Timer(); // default constructor
        try {
            ObjectName timerName =
                new ObjectName("ExportDomain:type=Timer");
            ObjectInstance timerObjectInstance =
                myMBeanServer.registerMBean(timer, timerName);
            echo("\tOBJECT NAME = " +
                 timerObjectInstance.getObjectName().toString());
            echo("done");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void removeMBeans() {
        printline();
        try {
            echo("Unregistering all the MBeans except " +
                 "the MBean server delegate\n");
            echo("    Current MBean count = " +
                 myMBeanServer.getMBeanCount() + "\n");
            Set allMBeans = myMBeanServer.queryNames(null, null);
            for (Iterator i = allMBeans.iterator(); i.hasNext(); ) {
                ObjectName name = (ObjectName) i.next();
                if (!name.toString().equals(ServiceName.DELEGATE)) {
                    echo("\tUnregistering " + name.toString());
                    myMBeanServer.unregisterMBean(name);
                }
            }
            echo("\n    Current MBean count = " +
                 myMBeanServer.getMBeanCount() + "\n");
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
}
