package org.basic.net.c20_jmx.jdmk.current.Connectors.rmi;
/*
 * @(#)file      Server.java
 * @(#)author    Sun Microsystems, Inc.
 * @(#)version   1.1
 * @(#)lastedit  04/02/05
 *
 * Copyright 2004 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;

public class Server {

    public static void main(String[] args) {
        try {
            // Instantiate the MBean server
            //
            System.out.println("\nCreate the MBean server");
            MBeanServer mbs = MBeanServerFactory.createMBeanServer();

            // Create an RMI connector server
            //
            System.out.println("\nCreate an RMI connector server");
            JMXServiceURL url = new JMXServiceURL(
		      "service:jmx:rmi:///jndi/rmi://localhost:9999/server");
            JMXConnectorServer cs =
                JMXConnectorServerFactory.newJMXConnectorServer(url, null, mbs);

            // Start the RMI connector server
            //
            System.out.println("\nStart the RMI connector server");
            cs.start();
            System.out.println("\nRMI connector server successfully started");
            System.out.println("\nWaiting for incoming connections...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
