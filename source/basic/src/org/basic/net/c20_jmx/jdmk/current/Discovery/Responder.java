package org.basic.net.c20_jmx.jdmk.current.Discovery;
/*
 * @(#)file      Responder.java
 * @(#)author    Sun Microsystems, Inc.
 * @(#)version   1.6
 * @(#)lastedit  04/05/13
 *
 * Copyright 2004 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

// java imports
//
import java.io.IOException ;
import java.net.UnknownHostException ;
import java.util.Enumeration ;
import java.util.Iterator ;
import java.util.Set ;
import java.util.Vector ;

// JMX imports
//
import javax.management.*;

// JMX-remote imports
//
import javax.management.remote.*;
 
// Java DMK imports
//
import com.sun.jdmk.ServiceName;
import com.sun.jdmk.comm.*;
import com.sun.jdmk.discovery.*;

public class Responder {
    public static void main(String[] args) {
	try {
	    MBeanServer myMBeanServer = MBeanServerFactory.createMBeanServer();

	    echo("\nCreate and register a DiscoveryResponder MBean.");
	    ObjectName dc = new ObjectName("DiscoveryExample:name=DiscoveryResponder");
	    myMBeanServer.createMBean("com.sun.jdmk.discovery.DiscoveryResponder", dc);
	    myMBeanServer.invoke(dc, "start", null, null);
	    
	    // jdmk servers as legacy MBean
	    echo("\nCreate an HtmlAdaptorServer on the default port.");
	    CommunicatorServer htmlAdaptor = new HtmlAdaptorServer();
	    htmlAdaptor.start();
	    
	    ObjectInstance htmlAdaptorOI = myMBeanServer.registerMBean(htmlAdaptor, null);
	    echo("The HtmlAdaptorServer is registered as "+htmlAdaptorOI.getObjectName());
	    
	    // jmx-remote connector servers
	    echo("\n>>> Press return to register JMX Remote connector servers.");
	    System.in.read();
	    
	    JMXServiceURL url;
	    JMXConnectorServer server;
	    ObjectName on;
	    
	    // rmi
	    echo("\nCreate a JMX Remote RMI connector server.");
	    url = new JMXServiceURL("rmi", null, 0);
	    server = JMXConnectorServerFactory.newJMXConnectorServer(url, null, myMBeanServer);
	    server.start();
	    
	    url = server.getAddress();
	    echo("The JMX Remote RMI connector server is started at the address: "+server.getAddress());
	    
	    on = new ObjectName("jmx-remote:protocol=rmi");
	    myMBeanServer.registerMBean(server, on);
	    echo("The JMX Remote RMI connector server is registered as "+on);
	    
	    
	    // iiop
	    echo("\nCreate a JMX Remote IIOP connector server.");
	    url = new JMXServiceURL("iiop", null, 0);
	    server = JMXConnectorServerFactory.newJMXConnectorServer(url, null, myMBeanServer);
	    server.start();
	    
	    url = server.getAddress();
	    echo("The JMX Remote IIOP connector server is started at the address: "+server.getAddress());
	    
	    on = new ObjectName("jmx-remote:protocol=iiop");
	    myMBeanServer.registerMBean(server, on);
	    echo("The JMX Remote IIOP connector server is registered as "+on);
	    
	    // jmxmp
	    echo("\nCreate a JMX Remote JMXMP connector server.");
	    url = new JMXServiceURL("jmxmp", null, 0);
	    server = JMXConnectorServerFactory.newJMXConnectorServer(url, null, myMBeanServer);
	    server.start();
	    
	    url = server.getAddress();
	    echo("The JMX Remote JMXMP connector server is started at the address: "+server.getAddress());
	    
	    on = new ObjectName("jmx-remote:protocol=jmxmp");
	    myMBeanServer.registerMBean(server, on);
	    echo("The JMX Remote JMXMP connector server is registered as "+on);

	    /* stop/start the responder to make send a notification for a
	       DiscoveryMonitor. Add a sleep to allow the DiscoveryResponder
	       to have time to finish stop before being restarted.*/
	    myMBeanServer.invoke(dc, "stop", null, null);

	    Thread.sleep(100);

	    myMBeanServer.invoke(dc, "start", null, null);
	    
	    // legacy connector servers
	    echo("\n>>> Press return to create legacy Java DMK connector servers but wrap them and register them as a JMXConnectorServerMBean.");
	    System.in.read();
	    
	    // rmi
	    echo("\nCreate a Java DMK RMI connector server; the server is wrapped as a JMXConnectorServer.");
	    url = new JMXServiceURL("jdmk-rmi", null, 2345, "/wrappedRMIServer");
	    server = JMXConnectorServerFactory.newJMXConnectorServer(url, null, myMBeanServer);

	    on = new ObjectName("jmx-remote:protocol=jdmk-rmi");
	    myMBeanServer.registerMBean(server, on);
	    echo("The  wrapped Java DMK RMI connector server is registered as "+on);

	    server.start();	    
	    echo("The wrapped Java DMK RMI connector server is started at the address: "+url);
	    

	    
	    // http
	    echo("\nCreate a Java DMK HTTP connector server; the server is wrapped as a JMXConnectorServer.");
	    url = new JMXServiceURL("jdmk-http", null, 8888);
	    server = JMXConnectorServerFactory.newJMXConnectorServer(url, null, myMBeanServer);
	    server.start();
	    
	    echo("The wrapped Java DMK HTTP connector server is started at the address: "+url);
	    
	    on = new ObjectName("jmx-remote:protocol=jdmk-http");
	    myMBeanServer.registerMBean(server, on);
	    echo("The Java DMK HTTP connector server is registered as "+on);
	    
	    /* stop/start the responder to make send a notification for a
	       DiscoveryMonitor. Add a sleep to allow the DiscoveryResponder
	       to have time to finish before being restarted. */
	    myMBeanServer.invoke(dc, "stop", null, null);

	    Thread.sleep(100);

	    myMBeanServer.invoke(dc, "start", null, null);

	    echo("\n>>> Press return to create legacy Java DMK connector servers and register them as a legacy MBeans.");
	    System.in.read();
	    
	    echo("\nCreate a RmiConnectorServer on the port 2345.");
	    RmiConnectorServer rmiServer = new RmiConnectorServer(2345);
	    
	    ObjectInstance rmiServerOI = 
		myMBeanServer.registerMBean(rmiServer, null);

	    rmiServer.start();

	    echo("The RmiConnectorServer is registered as "+rmiServerOI.getObjectName());
	    
	    
	    echo("\nCreate an HttpConnectorServer on the default port.");
	    HttpConnectorServer httpServer = new HttpConnectorServer();
	    httpServer.start();
	    
	    ObjectInstance httpServerOI = 
		myMBeanServer.registerMBean(httpServer, null);
	    echo("The HttpConnectorServer is registered as "+httpServerOI.getObjectName());
	    
	    echo("Now a client can discover these registered legacy Java DMK connector servers.");

	    /* stop/start the responder to make send a notification for a
	       DiscoveryMonitor. Add a sleep to allow the DiscoveryResponder
	       to have time to finish stop before being restarted. */
	    myMBeanServer.invoke(dc, "stop", null, null);

	    Thread.sleep(100);

	    myMBeanServer.invoke(dc, "start", null, null);

	    echo("All servers have been registered.");

	    echo("\n>>> Press return to exit.");
	    System.in.read();
	    System.exit(0);
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    private static void echo(String s) {
	System.out.println(s);
    }
}
