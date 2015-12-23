package org.basic.net.c20_jmx.jdmk.current.Connectors.wrapping;
/*
 * @(#)file      Client.java
 * @(#)author    Sun Microsystems, Inc.
 * @(#)version   1.1
 * @(#)lastedit  04/03/17
 *
 * Copyright 2004 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

import java.util.ArrayList;

import javax.management.Attribute;
import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.Notification;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.basic.net.c20_jmx.mbean.simple.SimpleStandardMBean;

public class Client {

    public static void main(String[] args) {
	try {
	    JMXServiceURL url = new JMXServiceURL("jdmk-http", null, 6868);
	    connect(url);
	    
	    url = new JMXServiceURL("jdmk-rmi", null, 8888, "/myRMI");
	    connect(url);
        } catch (Exception e) {
            e.printStackTrace();
        }

	System.out.println("\nBye! Bye!");
	System.exit(0);
    }

    private static void connect(JMXServiceURL url) throws Exception {

	System.out.println("\n\nCreate a jdmk legacy client at the address: "+url);
	JMXConnector jmxc = JMXConnectorFactory.connect(url, null);
	MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();
	
	System.out.println("\nGet all domains:");
	String domains[] = mbsc.getDomains();
	for (int i = 0; i < domains.length; i++) {
	    System.out.println("\tDomain[" + i + "] = " + domains[i]);
	}

	System.out.println("\nMBean count in the remote MBeanServer is " + mbsc.getMBeanCount());

	ObjectName mbeanName = new ObjectName("MBeans:type=SimpleStandard");	
	System.out.println("\nCreate an MBean: \""+mbeanName+"\"");
	mbsc.createMBean("SimpleStandard", mbeanName, null, null);

	
	System.out.println("\nThe State of \""+mbeanName+"\" is " +
			   mbsc.getAttribute(mbeanName, "State"));
	
	System.out.println("\nChange the state of \""+mbeanName+"\"");
	mbsc.setAttribute(mbeanName,
			  new Attribute("State", "changed state"));
	
	// Get State attribute
	//
	// Another way of interacting with a given MBean is through a
	// dedicated proxy instead of going directly through the MBean
	// server connection
	//
	SimpleStandardMBean proxy = (SimpleStandardMBean)
	    MBeanServerInvocationHandler.newProxyInstance(
							  mbsc,
							  mbeanName,
							  SimpleStandardMBean.class,
							  false);
	System.out.println("\nThe new state of \""+mbeanName+"\" = " + proxy.getState());
	
	// Add notification listener on SimpleStandard MBean
	//
	System.out.println("\nAdd notification listener to \""+mbeanName+"\"");
	ClientListener listener = new ClientListener();
	mbsc.addNotificationListener(mbeanName, listener, null, null);
	
	// Invoke "reset" in SimpleStandard MBean
	//
	// Calling "reset" makes the SimpleStandard MBean emit a
	// notification that will be received by the registered
	// ClientListener.
	//
	System.out.println("\nInvoke reset() on \""+mbeanName+"\"");
	mbsc.invoke(mbeanName, "reset", null, null);
	
	// Print the notificatios received by the listener.
	//
	listener.printNotifs();
	
	// Remove notification listener on SimpleStandard MBean
	//
	System.out.println("\nRemove notification listener from the  \""+mbeanName+"\"");
	mbsc.removeNotificationListener(mbeanName, listener);
	
	// Unregister SimpleStandard MBean
	//
	System.out.println("\nUnregister \""+mbeanName+"\"");
	mbsc.unregisterMBean(mbeanName);
	
	// Close MBeanServer connection
	//
	System.out.println("\nClose the connection to the server "+url);
	jmxc.close();
    }

    private static class ClientListener implements NotificationListener {
	public void handleNotification(Notification notif, Object handback) {
	    synchronized(notifList) {
		notifList.add(notif);
		notifList.notifyAll();
	    }
	}

	public void printNotifs() {
	    synchronized(notifList) {
		while (notifList.size() == 0) {
		    try {
			notifList.wait();
		    } catch (InterruptedException ire) {
			// OK.
			break;
		    }
		}

		System.out.println("\nThe client listener got following notifications:");
		for (int i=0; i<notifList.size(); i++) {
		    System.out.println(""+notifList.get(i));
		}
	    }
	}

	private ArrayList notifList = new ArrayList();
    }
}
