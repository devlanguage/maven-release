package org.basic.net.c20_jmx.jdmk.legacy.SimpleClients;

/*
 * @(#)file      ClientWithoutProxy.java
 * @(#)author    Sun Microsystems, Inc.
 * @(#)version   1.9
 * @(#)lastedit  04/01/19
 *
 * Copyright 2004 Sun Microsystems, Inc.  All rights reserved. Use is subject to license terms.
 */

// java imports
//
import java.io.IOException;

// jmx imports
//
import javax.management.Attribute;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanConstructorInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanNotificationInfo;
import javax.management.MBeanOperationInfo;
import javax.management.ObjectInstance;
import javax.management.ObjectName;

// jdmk imports
//
import com.sun.jdmk.comm.RemoteMBeanServer;
import com.sun.jdmk.comm.RmiConnectorAddress;
import com.sun.jdmk.comm.RmiConnectorClient;

public class ClientWithoutProxy {

    private RemoteMBeanServer connectorClient = null;

    // Constructor:
    // - sets the trace level,
    // - creates an RMI connector client (behaviour specified by the RemoteMBeanServer interface),
    // - creates a default RmiConnectorAddress object (host is this host, port is 1099),
    // - connects the RMI connector client to the RMI connector server of the Java DMK agent.
    //
    public ClientWithoutProxy() {

        // Enable the TRACE level according to the level set in system properties
        // (specified at command line, e.g. java -DLEVEL_TRACE BaseAgent)
        //
        try {
            // TraceManager.parseTraceProperties();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // CREATE and CONNECT to the agent a new RMI connector client
        //
        echo("\n\tCREATE and CONNECT to the agent a new RMI connector client:");
        // ----------------
        echo("\t>> instantiate the RMI connector client...");

        connectorClient = new RmiConnectorClient(); // CREATION

        echo("\t<< done <<");
        // ----------------
        echo("\t>> instantiate a default RmiConnectorAddress object...");
        RmiConnectorAddress address = new RmiConnectorAddress();
        echo("\t\tTYPE\t= " + address.getConnectorType());
        echo("\t\tPORT\t= " + address.getPort());
        echo("\t\tHOST\t= " + address.getHost());
        echo("\t\tSERVER\t= " + address.getName());
        echo("\t<< done <<");
        // ----------------
        echo("\t>> connect the RMI connector client to the agent, using the RmiConnectorAddress...");
        try {

            connectorClient.connect(address); // CONNECTION

            echo("\t<< done <<");
        } catch (Exception e) {
            echo("\t!!! Could not connect the RMI connector client to the agent !!!");
            e.printStackTrace();
            echo("\nEXITING...\n");
            System.exit(1);
        }
    }

    public static void main(String[] args) {

        // START
        //
        echo("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        echo("\n>>> CREATE the Client...");
        ClientWithoutProxy client = new ClientWithoutProxy();
        echo("\npress <Enter> to continue...");
        waitForEnterPressed();

        // DO THE EXAMPLE:
        // All MBeans can be managed by calling directly the connector client appropriate methods
        // (specified in the RemoteMBeanServer interface), without using any proxy class.
        //
        client.doWithoutProxyExample("SimpleStandard");
        client.doWithoutProxyExample("SimpleDynamic");

        // END
        //
        // disconnect connector client from the connector server
        echo("\n>>> DISCONNECTING the connector client...");

        client.connectorClient.disconnect(); // DISCONNECTION

        echo("<< done <<");
        echo("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        echo("\n>>> END of the example:\n");
        echo("\n\tpress <Enter> to stop the client...\n");
        waitForEnterPressed();
        System.exit(0);
    }

    private void doWithoutProxyExample(String mbeanName) {

        try {

            // ----------------
            // build the MBean ObjectName instance
            //
            ObjectName mbeanObjectName = null;
            String domain = connectorClient.getDefaultDomain();

            mbeanObjectName = new ObjectName(domain + ":type=" + mbeanName); // MBEAN OBJECT NAME

            // ----------------
            // create and register an MBean in the MBeanServer of the agent
            //
            echo("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            echo("\tRunning the example for the " + mbeanName + " MBean");
            echo("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            echo("\nCurrent MBean count in the agent = " + connectorClient.getMBeanCount());
            echo("\n>>> CREATE the " + mbeanName + " MBean in the MBeanServer of the agent:");
            String mbeanClassName = mbeanName;

            ObjectInstance mbeanObjectInstance = connectorClient.createMBean(mbeanClassName, mbeanObjectName); // CREATE
                                                                                                               // MBEAN

            echo("\tMBEAN CLASS NAME      = " + mbeanClassName);
            echo("\tMBEAN OBJECT NAME     = " + mbeanObjectName);
            echo("\nCurrent MBean count in the agent = " + connectorClient.getMBeanCount());
            echo("\npress <Enter> to continue...");
            waitForEnterPressed();

            // ----------------
            // get and display the management information exposed by the MBean
            //
            echo("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            printMBeanInfo(mbeanObjectInstance);
            echo("\npress <Enter> to continue...\n");
            waitForEnterPressed();

            // ----------------
            // manage the MBean by making calls directly to the connector client
            //
            echo("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            manage(mbeanObjectInstance);
            echo("\npress <Enter> to continue...");
            waitForEnterPressed();

            // ----------------
            // unregister the MBean from the agent
            //
            echo("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            echo("\n>>> UNREGISTERING the " + mbeanName + " MBean");

            connectorClient.unregisterMBean(mbeanObjectInstance.getObjectName()); // UNREGISTER MBEAN

            echo("<< done <<");
            echo("\npress <Enter> to continue...");
            waitForEnterPressed();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void manage(ObjectInstance mbeanObjectInstance) {

        echo("\n>>> MANAGING the MBean directly  through the connector client");
        try {

            // Get attribute values
            printAttributes(mbeanObjectInstance);
            echo("\n\tpress <Enter> to continue...");
            waitForEnterPressed();

            // Change State attribute
            echo("\n    Setting State attribute to value \"new state from client\"...");
            Attribute stateAttr = new Attribute("State", "new state from client");

            connectorClient.setAttribute(mbeanObjectInstance.getObjectName(), stateAttr); // SET ATTRIBUTE

            // Get attribute values
            printAttributes(mbeanObjectInstance);
            echo("\n\tpress <Enter> to continue...");
            waitForEnterPressed();

            // Invoking reset operation
            echo("\n    Invoking reset operation...");
            Object[] params = new Object[0];
            String[] signature = new String[0];

            connectorClient.invoke(mbeanObjectInstance.getObjectName(), "reset", params, signature); // INVOKE OPERATION

            // Get attribute values
            printAttributes(mbeanObjectInstance);

        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    private void printMBeanInfo(ObjectInstance mbeanObjectInstance) {

        ObjectName mbeanObjectName = mbeanObjectInstance.getObjectName();

        echo("\n>>> Getting the management information of the MBean");
        echo("    using the getMBeanInfo method of the connector client");
        MBeanInfo info = null;
        try {

            info = connectorClient.getMBeanInfo(mbeanObjectName); // GET MBEAN INFO

        } catch (Exception e) {
            echo("\t!!! Could not get MBeanInfo object for " + mbeanObjectName + " !!!");
            e.printStackTrace();
            return;
        }

        // display content of MBeanInfo object
        //
        echo("\nCLASSNAME: \t" + info.getClassName());
        echo("\nDESCRIPTION: \t" + info.getDescription());
        echo("\nATTRIBUTES");
        MBeanAttributeInfo[] attrInfo = info.getAttributes();
        if (attrInfo.length > 0) {
            for (int i = 0; i < attrInfo.length; i++) {
                echo(" ** NAME: \t" + attrInfo[i].getName());
                echo("    DESCR: \t" + attrInfo[i].getDescription());
                echo("    TYPE: \t" + attrInfo[i].getType() + "\tREAD: " + attrInfo[i].isReadable() + "\tWRITE: "
                        + attrInfo[i].isWritable());
            }
        } else
            echo(" ** No attributes **");
        echo("\nCONSTRUCTORS");
        MBeanConstructorInfo[] constrInfo = info.getConstructors();
        for (int i = 0; i < constrInfo.length; i++) {
            echo(" ** NAME: \t" + constrInfo[i].getName());
            echo("    DESCR: \t" + constrInfo[i].getDescription());
            echo("    PARAM: \t" + constrInfo[i].getSignature().length + " parameter(s)");
        }
        echo("\nOPERATIONS");
        MBeanOperationInfo[] opInfo = info.getOperations();
        if (opInfo.length > 0) {
            for (int i = 0; i < opInfo.length; i++) {
                echo(" ** NAME: \t" + opInfo[i].getName());
                echo("    DESCR: \t" + opInfo[i].getDescription());
                echo("    PARAM: \t" + opInfo[i].getSignature().length + " parameter(s)");
            }
        } else
            echo(" ** No operations ** ");
        echo("\nNOTIFICATIONS");
        MBeanNotificationInfo[] notifInfo = info.getNotifications();
        if (notifInfo.length > 0) {
            for (int i = 0; i < notifInfo.length; i++) {
                echo(" ** NAME: \t" + notifInfo[i].getName());
                echo("    DESCR: \t" + notifInfo[i].getDescription());
            }
        } else
            echo(" ** No notifications **");
    }

    private void printAttributes(ObjectInstance mbeanObjectInstance) {

        // we get each attribute directly through the connector client
        //
        try {
            echo("\n    Getting attribute values:");

            String State = (String) connectorClient.getAttribute(mbeanObjectInstance.getObjectName(), "State");

            Integer NbChanges = (Integer) connectorClient.getAttribute(mbeanObjectInstance.getObjectName(), "NbChanges");

            echo("\tState     = \"" + State + "\"");
            echo("\tNbChanges = " + NbChanges);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    private static void echo(String msg) {
        System.out.println(msg);
    }

    private static void waitForEnterPressed() {
        try {
            boolean done = false;
            while (!done) {
                char ch = (char) System.in.read();
                if (ch < 0 || ch == '\n') {
                    done = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
