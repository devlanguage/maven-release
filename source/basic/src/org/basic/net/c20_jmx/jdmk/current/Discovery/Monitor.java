package org.basic.net.c20_jmx.jdmk.current.Discovery;
/*
 * @(#)file      Monitor.java
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

public class Monitor {
    public static void main(String[] args) throws Exception {

	echo("Create a DiscoveryMonitor.");
	DiscoveryMonitor dm = new DiscoveryMonitor();
	dm.start();

	echo("Add a listener to receive monitor notifications.");
	dm.addNotificationListener(new MyListener(), null, null);
 
	echo("Waiting for new server notifications ...");

	echo("\nType any key to stop the monitor.");
	System.in.read();

	dm.stop();
	echo("\nThe monitor has been stopped.");

	System.exit(0);
    }

    private static class MyListener implements NotificationListener {
	public void handleNotification(Notification notif, Object handback) {

	    echo("\nReceived a DiscoveryResponderNotification and found the following connectors:");
	    echo("...........................");

	    try {
		DiscoveryResponderNotification dn = (DiscoveryResponderNotification)notif;

		DiscoveryResponse dr = (DiscoveryResponse)dn.getEventInfo()  ;

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
	    } catch (Exception e) {
		echo("Got unexpected exception: "+e);
		e.printStackTrace();
	    }

	    echo("---------------------------------------");
	}
    }

    private static void echo(String s) {
	System.out.println(s);
    }
}
