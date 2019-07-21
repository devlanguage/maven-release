package org.basic.net.c20_jmx.jdmk.current.Connectors.wrapping;
/*
 * @(#)file      Server.java
 * @(#)author    Sun Microsystems, Inc.
 * @(#)version   1.1
 * @(#)lastedit  04/03/17
 *
 * Copyright 2004 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;

import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;

public class Server {

    public static void main(String[] args) {
	try {
            // Instantiate the MBean server
            //
            System.out.println("\nCreate the MBean server.");
            MBeanServer mbs = MBeanServerFactory.createMBeanServer();

            // Create a jdmk-http connector server
            //
            JMXServiceURL httpURL = new JMXServiceURL("jdmk-http", null, 6868);
            JMXConnectorServer httpCS =
                JMXConnectorServerFactory.newJMXConnectorServer(httpURL, null, mbs);
            System.out.println("\nCreate a wrapped jdmk-http connector server at: "+httpURL);

	    ObjectName httpON = new ObjectName("legacyWrapper:protocol=jdmk-http,port=6868");
	    mbs.registerMBean(httpCS, httpON);
	    System.out.println("The wrapped server has been registered with name "+httpON);

            // Start the jdmk-http connector server
            //
            System.out.println("\nStart the wrapped jdmk-http connector server");
            httpCS.start();

            // Create a jdmk-rmi connector server
            //
            JMXServiceURL rmiURL = new JMXServiceURL("jdmk-rmi", null, 8888, "/myRMI");
            System.out.println("\nCreate a wrapped jdmk-rmi connector server at: "+rmiURL);
            JMXConnectorServer rmiCS =
                JMXConnectorServerFactory.newJMXConnectorServer(rmiURL, null, mbs);

	    ObjectName rmiON = new ObjectName("legacyWrapper:protocol=jdmk-rmi,port=8888");
	    mbs.registerMBean(rmiCS, rmiON);
	    System.out.println("The wrapped server has been registered with name "+rmiON);

            // Start the jdmk-rmi connector server
            //
            System.out.println("\nStart the jdmk-rmi connector server");
            rmiCS.start();

            System.out.println("\nWaiting for incoming connections...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
