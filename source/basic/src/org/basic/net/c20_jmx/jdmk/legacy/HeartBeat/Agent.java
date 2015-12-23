package org.basic.net.c20_jmx.jdmk.legacy.HeartBeat;
/*
 * @(#)file      Agent.java
 * @(#)author    Sun Microsystems, Inc.
 * @(#)version   1.5
 * @(#)lastedit  04/05/11
 *
 * Copyright 2004 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

// jmx import
//
import javax.management.ObjectInstance;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;

public class Agent {

    public static void main(String[] args) {

        try {

            // Instantiate the MBean server.
            //
            echo("\n>>> Create the MBean server");
            MBeanServer server = MBeanServerFactory.createMBeanServer();

            // Create and start in the MBean server an RMI connector server.
            //
            echo("\n>>> Create and start an RMI connector server");
            ObjectInstance rmi =
                server.createMBean("com.sun.jdmk.comm.RmiConnectorServer",
                                   null);
            echo("\tOBJECT NAME = " + rmi.getObjectName());
            server.invoke(rmi.getObjectName(), "start", null, null);

            echo("\n>>> Now, you can start your client " +
                 "application to connect to this agent.");
            echo(">>> Type CTRL-C to stop the agent.\n");

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Print trace to standard output stream.
     */
    private static void echo(String msg) {
        System.out.println(msg);
    }
}
