package org.basic.net.c20_jmx.jdmk.legacy.MinimalAgent;
/*
 * @(#)file      MinimalAgent.java
 * @(#)author    Sun Microsystems, Inc.
 * @(#)version   1.9
 * @(#)lastedit  04/01/19
 *
 * Copyright 2004 Sun Microsystems, Inc.  All rights reserved. Use is subject to license terms.
 */

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectInstance;

public class MinimalAgent {

    public static void main(String[] args) {
        
	// Instantiate the MBean server
	System.out.println("\nCreate the MBean server");
	MBeanServer server = MBeanServerFactory.createMBeanServer();
        
	// Create and start in the MBean server:
	//    - an HTML protocol adaptor
	//    - an HTTP connector server
	//    - an RMI connector server
	try {
//	    com.sun.jdmk.TraceManager.parseTraceProperties();
	    System.out.println("\nCreate and start an HTML protocol adaptor");
	    ObjectInstance html = server.createMBean("com.sun.jdmk.comm.HtmlAdaptorServer", null);
	    server.invoke(html.getObjectName(), "start", new Object[0], new String[0]);

	    System.out.println("\nCreate and start an HTTP connector server");
	    ObjectInstance http = server.createMBean("com.sun.jdmk.comm.HttpConnectorServer", null);
	    server.invoke(http.getObjectName(), "start", new Object[0], new String[0]);

	    System.out.println("\nCreate and start an RMI connector server");
	    ObjectInstance rmi = server.createMBean("com.sun.jdmk.comm.RmiConnectorServer", null);
	    server.invoke(rmi.getObjectName(), "start", new Object[0], new String[0]);

	} catch(Exception e) {
	    e.printStackTrace();
	    return;
	}

	System.out.println("\nNow, you can point your browser to http://localhost:8082/");
	System.out.println("or start your client application to connect to this agent.\n");
    }
}
