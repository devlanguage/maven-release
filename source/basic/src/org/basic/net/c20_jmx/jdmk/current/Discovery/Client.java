package org.basic.net.c20_jmx.jdmk.current.Discovery;
/*
 * @(#)file      Client.java
 * @(#)author    Sun Microsystems, Inc.
 * @(#)version   1.2
 * @(#)lastedit  04/03/17
 *
 * Copyright 2004 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

// java imports
//
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;
import java.util.Collection;

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

public class Client {
    public static void main(String[] args) throws Exception {
	BufferedReader br
	    = new BufferedReader(new InputStreamReader(System.in));
 
	discoveryClient = new DiscoveryClient();
	discoveryClient.start();

	while(true) {
	    echo("\n>>> Press return to discover connector servers;");
	    echo(">>> Type a host name then return to discover connector servers running on that machine;");
	    echo(">>> Type bye then return to stop the client.");

	    String s = br.readLine();

	    if (s.equalsIgnoreCase("bye")) {
		System.exit(0);
	    } else {
		try {
		    discover(s);
		} catch (Exception e) {
		    echo("Got exception "+e);
		    e.printStackTrace();
		}
	    }
	}
    }

    private static void discover(String host) throws Exception {
	Vector v = null;
	if (host ==  null || host.equals("")) {
	   v = discoveryClient.findCommunicators();
	} else {
	    v= discoveryClient.findCommunicators(host);
	}

	if (v.size() == 0) {
	    echo("No connector server has been found.");

	    return;
	}

	for (int i=0; i<v.size(); i++) {
	    DiscoveryResponse dr = (DiscoveryResponse)v.get(i);
	    JMXServiceURL url = null;

	    // legacy servers
	    Collection c = dr.getObjectList().values();
	    for (Iterator iter=c.iterator(); iter.hasNext();) {
		Object o = iter.next();

		if (!(o instanceof ConnectorAddress)) {
		    continue;
		}

		ConnectorAddress ca = (ConnectorAddress)o;
		if (ca.getConnectorType().equals("SUN RMI")) {
		    url = new JMXServiceURL("jdmk-rmi",
					    ((RmiConnectorAddress)ca).getHost(),
					    ((RmiConnectorAddress)ca).getPort());
		} else if (ca.getConnectorType().equals("SUN HTTP")) {
		    url = new JMXServiceURL("jdmk-http",
					    ((HttpConnectorAddress)ca).getHost(),
					    ((HttpConnectorAddress)ca).getPort());
		} else if (ca.getConnectorType().equals("SUN HTTPS")) {
		    url = new JMXServiceURL("jdmk-https",
					    ((HttpConnectorAddress)ca).getHost(),
					    ((HttpConnectorAddress)ca).getPort());
		} else {
		    echo("Got an unknown protocol: "+ca.getConnectorType());

		    continue;
		}

		echo("\nFound a legacy server which is registered as a legacy MBean: "
		     +url.getProtocol()+" "+url.getHost()+" "+url.getPort());
		echo("Connecting to that server.");
		JMXConnector jc = JMXConnectorFactory.connect(url);
		echo("Its default domain is "+jc.getMBeanServerConnection().getDefaultDomain());
		echo("Closing the connection to that server.");
		jc.close();
	    }

	    // JMX-remote servers
	    JMXServiceURL[] urls = dr.getServerAddresses();

	    echo("");
	    for (int ii=0; ii<urls.length; ii++) {
		echo("\nFound a server which is registered as a JMXConnectorServerMBean: "
		     +urls[ii]);
		echo("Connecting to that server.");
		JMXConnector jc = JMXConnectorFactory.connect(urls[ii]);
		echo("Its default domain is "+jc.getMBeanServerConnection().getDefaultDomain());
		echo("Closing the connection to that server.");
		jc.close();
	    }
	}
    }


    private static void echo(String s) {
	System.out.println(s);
    }

    private static DiscoveryClient discoveryClient;

}
