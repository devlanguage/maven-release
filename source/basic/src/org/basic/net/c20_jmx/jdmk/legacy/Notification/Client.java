package org.basic.net.c20_jmx.jdmk.legacy.Notification;
/*
 * @(#)file      Client.java
 * @(#)author    Sun Microsystems, Inc.
 * @(#)version   1.8
 * @(#)lastedit  04/01/19
 *
 * Copyright 2004 Sun Microsystems, Inc.  All rights reserved. Use is subject to license terms.
 */

import javax.management.Notification;
import javax.management.NotificationListener;
import javax.management.ObjectName;

import com.sun.jdmk.comm.ClientNotificationHandler;
import com.sun.jdmk.comm.RmiConnectorAddress;
import com.sun.jdmk.comm.RmiConnectorClient;


public class Client implements NotificationListener {
	public Client(String[] args) {
		Object[] params = new Object[1];
		String[] signatures = new String[1];

		try {

			// ---------------------
			// get the agent address, if not set by user, local host will be used
			String agentHost = null;
			if (args.length >= 1) {
				agentHost = args[0];
			} else {
				agentHost = java.net.InetAddress.getLocalHost().getHostName();
			}

			System.out.println("\n>>> The client is starting...");
			
			// ---------------------
			// use RNI connector to communicate with the agent
			System.out.println(">>> Create an RMI connector client");
			RmiConnectorClient connectorClient = new RmiConnectorClient();

			RmiConnectorAddress rmiAddress = new RmiConnectorAddress(agentHost, 8086, com.sun.jdmk.ServiceName.RMI_CONNECTOR_SERVER);

			connectorClient.connect(rmiAddress);

			// ---------------------
			// wait 1 second for connecting
			Thread.sleep(1000);

			// ---------------------
			System.out.println(">>> Create an NotificationEmitter instance as MBean Object.");
			ObjectName mbean = new ObjectName ("Default:name=NotificationEmitter");

			connectorClient.createMBean("NotificationEmitter", mbean);

			// ---------------------
			System.out.println(">>> Add notification listener.");
			connectorClient.addNotificationListener(mbean, this, null, null);

			// ---------------------
			System.out.println("\n>>> Set notification forward mode to PUSH.");
			connectorClient.setMode(ClientNotificationHandler.PUSH_MODE);

			// ---------------------
			System.out.println(">>> Send a request to ask the MBean object to send 10 notifications...");
			params[0] = new Integer(10);
			signatures[0] = "java.lang.Integer";
			connectorClient.invoke(mbean, "sendNotifications", params, signatures);
			System.out.println(">>> Done.");

			// ---------------------
			System.out.println(">>> Receiving notifications...\n");
			Thread.sleep(2000);
			System.out.println("\n>>> Hit <Enter> to continue.");
			System.in.read();

			// ---------------------
			System.out.println(">>> Set notification forward mode to PULL.");
			connectorClient.setMode(ClientNotificationHandler.PULL_MODE);

			System.out.println(">>> Set notification forward period to 500 milleseconds.");
			connectorClient.setPeriod(500);

			// ---------------------
			System.out.println(">>> send a request to ask the MBean object to send 20 notifications...");
			params[0] = new Integer(20);
			signatures[0] = "java.lang.Integer";
			connectorClient.invoke(mbean, "sendNotifications", params, signatures);
			System.out.println(">>> Done.");

			// ---------------------
			System.out.println(">>> Receiving notifications...\n");
			Thread.sleep(2000);
			System.out.println("\n>>> Hit <Enter> to continue.");
			System.in.read();

			// ---------------------
			System.out.println(">>> Use pull mode but set period to zero. We should call the method getNotification to receive notifications.");
			connectorClient.setMode(ClientNotificationHandler.PULL_MODE);
			connectorClient.setPeriod(0);

			System.out.println(">>> Send a request to ask the MBean object to send 30 notifications...");
			params[0] = new Integer(30);
			signatures[0] = "java.lang.Integer";
			connectorClient.invoke(mbean, "sendNotifications", params, signatures);
			System.out.println(">>> Done.");

			System.out.println("\n>>> Hit <Enter> to get notifications.");
			System.in.read();
			connectorClient.getNotifications();

			Thread.sleep(100);
			System.out.println("\n>>> Hit <Enter> to continue.");
			System.in.read();

			// ---------------------
			System.out.println(">>> Use pull mode but set period to zero, set cache size to 10, set overflow mode to DISCARD_OLD.");
			connectorClient.setMode(ClientNotificationHandler.PULL_MODE);
			connectorClient.setPeriod(0);
			connectorClient.setCacheSize(10, true);
			connectorClient.setOverflowMode(ClientNotificationHandler.DISCARD_OLD);

			System.out.println(">>> Send a request to ask the MBean object to send 30 notifications...");
			params[0] = new Integer(30);
			signatures[0] = "java.lang.Integer";
			connectorClient.invoke(mbean, "sendNotifications", params, signatures);
			System.out.println(">>> Done.");

			System.out.println("\n>>> Hit <Enter> to get notifications.");
			System.in.read();
			connectorClient.getNotifications();

			Thread.sleep(100);
			System.out.println("\n>>> Hit <Enter> to continue.");
			System.in.read();

			System.out.println(">>> Get overflow count = " +connectorClient.getOverflowCount());

			System.out.println("\n>>> Hit <Enter> to continue.");
			System.in.read();

			// ---------------------
			System.out.println(">>> Use pull mode but set period to zero, set cache size to 10, set overflow mode to DISCARD_NEW.");
			connectorClient.setMode(ClientNotificationHandler.PULL_MODE);
			connectorClient.setPeriod(0);
			connectorClient.setCacheSize(10, true);
			connectorClient.setOverflowMode(ClientNotificationHandler.DISCARD_NEW);

			System.out.println(">>> Send a request to ask the MBean object to send 30 notifications...");
			params[0] = new Integer(30);
			signatures[0] = "java.lang.Integer";
			connectorClient.invoke(mbean, "sendNotifications", params, signatures);
			System.out.println(">>> Done.");

			System.out.println("\n>>> Hit <Enter> to get notifications.");
			System.in.read();
			connectorClient.getNotifications();

			Thread.sleep(100);
			System.out.println("\n>>> Hit <Enter> to continue.");
			System.in.read();

			System.out.println(">>> Get overflow count = " +connectorClient.getOverflowCount());

			System.out.println("\n>>> Hit <Enter> to continue.");
			System.in.read();

			// ---------------------
			System.out.println(">>> The client is leaving, do cleaning at first...");
			connectorClient.removeNotificationListener(mbean, this);
			connectorClient.unregisterMBean(mbean);

			System.out.println(">>> Bye bye");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	// used to receive notifications
	public void handleNotification(Notification notif, Object handback) {
		System.out.println("Client: receive a notification of type " + notif.getType() +
                           "\nwith the sequence number " + notif.getSequenceNumber());
	}

	public static void main(String[] args) {
		Client client = new Client(args);
		System.exit(0);
	}
}
