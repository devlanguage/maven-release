package org.basic.net.c20_jmx.jdmk.current.Connectors.jmxmp;
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

            // Create a JMXMP connector server
            //
            System.out.println("\nCreate a JMXMP connector server");
            JMXServiceURL url = new JMXServiceURL("jmxmp", null, 5555);
            JMXConnectorServer cs =
                JMXConnectorServerFactory.newJMXConnectorServer(url, null, mbs);

            // Start the JMXMP connector server
            //
            System.out.println("\nStart the JMXMP connector server");
            cs.start();
            System.out.println("\nJMXMP connector server successfully started");
            System.out.println("\nWaiting for incoming connections...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
