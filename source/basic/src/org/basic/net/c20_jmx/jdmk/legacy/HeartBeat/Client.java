package org.basic.net.c20_jmx.jdmk.legacy.HeartBeat;
/*
 * @(#)file      Client.java
 * @(#)author    Sun Microsystems, Inc.
 * @(#)version   1.9
 * @(#)lastedit  04/05/11
 *
 * Copyright 2004 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

// java imports
//
import java.io.IOException;

// jmx imports
//
import javax.management.Notification;
import javax.management.NotificationListener;

// jdmk imports
//
import com.sun.jdmk.ServiceName;
import com.sun.jdmk.comm.ConnectorAddress;
import com.sun.jdmk.comm.RmiConnectorClient;
import com.sun.jdmk.comm.RmiConnectorAddress;
import com.sun.jdmk.comm.HeartBeatNotification;

public class Client implements NotificationListener {

    /**
     * Constructor: 
     *   - creates an RMI connector client (behavior specified by the
     *     RemoteMBeanServer interface),
     *   - sets the heartbeat period and number of retries and adds
     *     this manager as a listener for
     *     heartbeat notifications,
     *   - creates an RmiConnectorAddress object (default host is
     *     localhost, default port is 1099), 
     *   - connects the RMI connector client to the RMI connector
     *     server of the Java DMK agent.
     */
    public Client(String[] args) {

        // CREATE a new RMI connector client
        //
        echo("\tInstantiate the RMI connector client...");
        connectorClient = new RmiConnectorClient();

        // SET heartbeat period to 1 sec. Default value is 10 secs
        //
        echo("\tSet heartbeat period to 1 second...");
        connectorClient.setHeartBeatPeriod(1000);

        // SET heartbeat number of retries to 2. Default value is 6 times
        //
        echo("\tSet heartbeat number of retries to 2 times...");
        connectorClient.setHeartBeatRetries(2);

        // ADD this manager as a listener for heartbeat notifications
        //
        echo("\tAdd this manager as a listener for heartbeat notifications...");
        connectorClient.addHeartBeatNotificationListener(this, null, null);

        // CREATE the Connector Server Address
        //
        String host = null;
        String port = null;
        RmiConnectorAddress address = null;
        echo("\tInstantiate the RmiConnectorAddress object...");
        if (args.length == 0) {
            address = new RmiConnectorAddress();
        } else if (args.length == 2) {
            host = args[0];
            port = args[1];
            address = new RmiConnectorAddress(host, Integer.parseInt(port),
                                              ServiceName.RMI_CONNECTOR_SERVER);
        } else {
            usage();
        }
        echo("\t\tTYPE   = " + address.getConnectorType());
        echo("\t\tHOST   = " + address.getHost());
        echo("\t\tPORT   = " + address.getPort());
        echo("\t\tSERVER = " + address.getName());

        // CONNECT the RMI connector client to the agent
        //
        echo("\tConnect the RMI connector client to the agent, " +
             "using the RmiConnectorAddress...");
        try {
            connectorClient.connect(address);
        } catch (Exception e) {
            echo("\t!!! Could not connect the RMI connector client " +
                 "to the agent !!!");
            e.printStackTrace();
            echo("\nEXITING...\n");
            System.exit(1);
        }
    }

    /**
     * Notification handler implementation
     */
    public void handleNotification(Notification notification, Object handback) {
        echo("\n>>> Notification has been received...");
        echo("\tNotification type = " + notification.getType());
        if (notification instanceof HeartBeatNotification) {
            ConnectorAddress notif_address =
                ((HeartBeatNotification)notification).getConnectorAddress();
            if (notif_address instanceof RmiConnectorAddress) {
                RmiConnectorAddress rmi_address =
                    (RmiConnectorAddress) notif_address;
                echo("\tNotification connector address:");
                echo("\t\tTYPE   = " + rmi_address.getConnectorType());
                echo("\t\tHOST   = " + rmi_address.getHost());
                echo("\t\tPORT   = " + rmi_address.getPort());
                echo("\t\tSERVER = " + rmi_address.getName());
            }
        }
    }

    /**
     * Application main
     */
    public static void main(String[] args) {

        // Create and connect a new RMI connector client to the agent.
        //
        echo("\n>>> Create the connector client...");
        Client client = new Client(args);

        // Wait until Enter key is pressed down.
        //
        echo("\nPress <Enter> to continue...");
        waitForEnterPressed();

        // Disconnect connector client from the connector server.
        //
        if (client.connectorClient.isConnected()) {
            echo("\n>>> Disconnect the connector client...");
            client.connectorClient.disconnect();
        }
    }

    /**
     * Wait until Enter key is pressed down.
     */
    private static void waitForEnterPressed() {
        try {
            boolean done = false;
            while (!done) {
                char ch = (char) System.in.read();
                if (ch<0||ch=='\n') {
                    done = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Print trace to standard output stream.
     */
    private static void echo(String msg) {
        System.out.println(msg);
    }

    /**
     * Displays help information.
     */
    private static void usage() {
        echo("Usage: java Client [host port]");
        System.exit(1);
    }

    // PRIVATE VARIABLES
    //
    private String host = null;
    private String port = null;
    private RmiConnectorClient connectorClient = null;
}
