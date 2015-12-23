package org.basic.net.c20_jmx.jdmk.legacy.Notification;
/*
 * @(#)file      BaseAgent.java
 * @(#)author    Sun Microsystems, Inc.
 * @(#)version   1.10
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
import javax.management.ObjectName;

import com.sun.jdmk.ServiceName;
import com.sun.jdmk.comm.CommunicatorServer;
import com.sun.jdmk.comm.HtmlAdaptorServer;



public class BaseAgent {


    private MBeanServer myMBeanServer ;


    // Constructor: 
    //   - sets the trace level,
    //   - instantiantes the MBean server of this agent.
    //
    public BaseAgent() {
    
        // Enable the TRACE level according to the level set in system properties
        // (specified at command line, e.g. java -DLEVEL_TRACE BaseAgent)
        //
//        try {
//            TraceManager.parseTraceProperties();
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//            System.exit(0);
//        }

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
            localHost = "localhost" ;
        }
        echo("\nNow, you can point your browser to http://"+localHost+":8082/");
        echo("to view this agent through the HTML protocol adaptor.");
        echo("\nOr, in another window, start a client application that connects");
        echo("to this agent by using an RMI connector client");
        echo("\nPress <Enter> to stop the agent...");
        waitForEnterPressed();
        echo("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        
        // REMOVE OUR SELECTION OF PROTOCOL ADAPTORS AND CONNECTORS
        //
        myBaseAgent.removeAdaptorsAndConnectors();

        // END
        //
        System.exit(0);
    }


    public void addAdaptorsAndConnectors() {

        // *******************************
        // Adding an HTML protocol adaptor
        // *******************************

        // Below we illustrate one method of adding an MBean in an MBean server:
        //   - first, we instantiate the HTML protocol adaptor
        //   - then, we register it into the MBean server
        //
        echo("Instantiating an HTML protocol adaptor with default port...");

        CommunicatorServer htmlAdaptor = new HtmlAdaptorServer();

        try {
            // We let the html protocol adaptor provides its default name and port number
            ObjectInstance htmlAdaptorInstance = myMBeanServer.registerMBean(htmlAdaptor, null);
        } catch(Exception e) {
            e.printStackTrace();
            System.exit(0);
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
            System.exit(0);
        }

        // ******************************
        // Adding an RMI connector server
        // ******************************

        // Below we illustrate a third method of adding an MBean in an MBean server:
        //   - first, we instantiate the RMI connector server using a non default constructor
        //   - then, we register it into the MBean server
        //
        
        echo("Instantiating an RMI connector server with non default port 8086...");
        CommunicatorServer rmiConnector = null;
        Object[] params = {new Integer(8086)};
        String[] signature = {"int"};
        try {
            String RmiConnectorClassName = "com.sun.jdmk.comm.RmiConnectorServer";
            // specify the RMI port number to use
            // let the RMI connector server provides its default name
            rmiConnector = (CommunicatorServer)myMBeanServer.instantiate(RmiConnectorClassName, params, signature);
        } catch(Exception e) {
            e.printStackTrace();
            System.exit(0);
        }

        try {
            // We let the RMI connector server provides its default name
            ObjectInstance rmiConnectorInstance = myMBeanServer.registerMBean(rmiConnector, null);
        } catch(Exception e) {
            e.printStackTrace();
            System.exit(0);
        }

        // Now we explicitly start the RMI connector server as it is not started automatically
        //
        rmiConnector.start();

        // waiting to leave starting state...
        while (rmiConnector.getState() == CommunicatorServer.STARTING) {
            sleep(1000);
        }
        
        // Check that the RMI connector server is started
        if (rmiConnector.getState() != CommunicatorServer.ONLINE) {
            echo("Cannot start the RMI connector server");
            echo("Check that the port is not already used");
            System.exit(0);
        }
    }


    public void removeAdaptorsAndConnectors() {
        
        try {
            echo("Unregistering the HTML protocol adaptor...");
            myMBeanServer.unregisterMBean(new ObjectName(ServiceName.DOMAIN + ":" + ServiceName.HTML_ADAPTOR_SERVER));
            echo("Unregistering the RMI connector server...");
            myMBeanServer.unregisterMBean(new ObjectName(ServiceName.DOMAIN + ":" + ServiceName.RMI_CONNECTOR_SERVER));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
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
                if (ch<0||ch=='\n') {
                    done = true;
                }
	    }
        }
        catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

}
