package org.basic.net.c20_jmx.jdmk.legacy.Cascading;
/*
 * @(#)file      SubAgent.java
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
import java.util.Iterator;
import java.util.Set;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.timer.Timer;

import org.basic.net.c20_jmx.mbean.simple.SimpleStandard;

import com.sun.jdmk.ServiceName;
import com.sun.jdmk.comm.CommunicatorServer;
import com.sun.jdmk.comm.HtmlAdaptorServer;



public class SubAgent {


    private MBeanServer myMBeanServer ;


    // Constructor: 
    //   - sets the trace level,
    //   - instantiantes the MBean server of this agent,
    //   - retrieves the MBean server ID from the MBean server delegate,
    //
    public SubAgent() {
    
        // Enable the TRACE level according to the level set in system properties
        // (specified at command line, e.g. java -DLEVEL_TRACE SubAgent)
        //
//        try {
//            TraceManager.parseTraceProperties();
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//            System.exit(1);
//        }

        // Instantiates the MBean server
        //
        myMBeanServer = MBeanServerFactory.createMBeanServer();
    }


    public static void main(String[] args) {
        
        // START
        //
        echo("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        echo("Creating an instance of this SubAgent...");
        SubAgent mySubAgent = new SubAgent();

        // ADD OUR SELECTION OF PROTOCOL ADAPTORS AND CONNECTORS
        //
        mySubAgent.addAdaptorsAndConnectors();

        // Create a SimpleStandard MBean and a Timer MBean
        mySubAgent.addMBeans();

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
        echo("to this agent by using an HTTP or RMI connector client");
        echo("\nPress <Enter> to stop the agent...");
        waitForEnterPressed();
        
        // remove the MBeans that we have added (i.e. the adaptors and connectors)
        mySubAgent.removeMBeans(); // removes all MBeans except the delegate

        echo("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
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

        CommunicatorServer htmlAdaptor = new HtmlAdaptorServer(); // default constructor: sets port to default 8082

        try {
            // We let the html protocol adaptor provides its default name
            ObjectInstance htmlAdaptorInstance = myMBeanServer.registerMBean(htmlAdaptor, null);
        } catch(Exception e) {
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

        // ******************************
        // Adding an RMI connector server
        // ******************************

        // Below we illustrate a third method of adding an MBean in an MBean server:
        //   - first, we instantiate the RMI connector server using a non default constructor
        //   - then, we register it into the MBean server
        //
        
        echo("Instantiating an RMI connector server using port 1099..."); 
        CommunicatorServer rmiConnector = null;
        Object[] params = {new Integer(1099)};    // param is RMI port number to use
        String[] signature = {"int"};             //
        try {
            String RmiConnectorClassName = "com.sun.jdmk.comm.RmiConnectorServer";
            rmiConnector = (CommunicatorServer)myMBeanServer.instantiate(RmiConnectorClassName, params, signature);
        } catch(Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        try {
            // We provides an object name for the RMI connector server 
            // (which is actually the same as the one which would have been provided by default if we had passed null)
            ObjectName rmiConnObjName = new ObjectName(ServiceName.DOMAIN +":"+ ServiceName.RMI_CONNECTOR_SERVER);
            ObjectInstance rmiConnectorInstance = myMBeanServer.registerMBean(rmiConnector, rmiConnObjName);
        } catch(Exception e) {
            e.printStackTrace();
            System.exit(1);
        }


        // Now we explicitly start the RMI connector server as it is not started automatically
        //
        rmiConnector.start();

        // waiting to leave starting state...
        while (rmiConnector.getState() == CommunicatorServer.STARTING) {
            sleep(500);
        }
        
        // Check that the RMI connector server is started
        if (rmiConnector.getState() != CommunicatorServer.ONLINE) {
            echo("Cannot start the RMI connector server");
            echo("Check that the port is not already used");
            System.exit(1);
        }
    }



    public void addMBeans() {

        echo("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        echo("Registering a SimpleStandard MBean...");

        SimpleStandard simple = new SimpleStandard(); // default constructor
        try {	
            ObjectName simpleName = new ObjectName(myMBeanServer.getDefaultDomain() + ":" + "type = SimpleStandard");
            ObjectInstance simpleObjectInstance = myMBeanServer.registerMBean(simple, simpleName);
            echo("\tOBJECT NAME    = " + simpleObjectInstance.getObjectName().toString());
            echo("done");
        } catch(Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        echo("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        echo("Registering a Timer MBean...");

        Timer timer = new Timer(); // default constructor
        try {	
            ObjectName timerName = new ObjectName("CascadedDomain:type=timer");	
            ObjectInstance timerObjectInstance = myMBeanServer.registerMBean(timer, timerName);
            echo("\tOBJECT NAME    = " + timerObjectInstance.getObjectName().toString());
            echo("done");
        } catch(Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

  
    public void removeMBeans() {
        
        echo("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        try {
            echo("Unregistering all MBeans except the MBean server delegate\n");
            echo("    Current MBean count = "+ myMBeanServer.getMBeanCount() +"\n");

            Set allMBeans = myMBeanServer.queryNames(null, null);
            for (Iterator i = allMBeans.iterator(); i.hasNext(); ) {
                ObjectName name = (ObjectName) i.next();
                if (!name.toString().equals(ServiceName.DELEGATE)) {
                    echo("\tUnregistering " + name.toString());
                    myMBeanServer.unregisterMBean(name);
                }
            }
            echo("\n    Current MBean count = "+ myMBeanServer.getMBeanCount() +"\n");
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
