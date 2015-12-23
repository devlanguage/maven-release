package org.basic.net.c20_jmx.jdmk.current.Security.jmxmp.fine_grained.server;
/*
 * @(#)file      Server.java
 * @(#)author    Sun Microsystems, Inc.
 * @(#)version   1.1
 * @(#)lastedit  04/02/05
 *
 * Copyright 2004 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

import java.io.File;
import java.security.Security;
import java.util.HashMap;
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

            // Environment map
            //
            System.out.println("\nInitialize the environment map");
            HashMap env = new HashMap();

            // Add SASL/PLAIN mechanism server provider
            //
            Security.addProvider(new com.sun.jdmk.security.sasl.Provider());

            // The profiles supported by this server are TLS and SASL/PLAIN
            //
            env.put("jmx.remote.profiles", "TLS SASL/PLAIN");

            // Callback handler used by the PLAIN SASL server mechanism
            // to perform user authentication
            //
            env.put("jmx.remote.sasl.callback.handler",
                    new PropertiesFileCallbackHandler("config" +
                                                      File.separator +
                                                      "password.properties"));

            // Create a JMXMP connector server
            //
            System.out.println("\nCreate a JMXMP connector server");
            JMXServiceURL url = new JMXServiceURL("jmxmp", null, 5555);
            JMXConnectorServer cs =
                JMXConnectorServerFactory.newJMXConnectorServer(url, env, mbs);

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
