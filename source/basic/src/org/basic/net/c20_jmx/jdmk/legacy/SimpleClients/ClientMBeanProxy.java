package org.basic.net.c20_jmx.jdmk.legacy.SimpleClients;

/*
 * @(#)file      ClientMBeanProxy.java
 * @(#)author    Sun Microsystems, Inc.
 * @(#)version   1.13
 * @(#)lastedit  04/01/19
 *
 * Copyright 2004 Sun Microsystems, Inc.  All rights reserved. Use is subject to license terms.
 */

// java imports
//
import java.io.IOException;
import java.lang.reflect.Constructor;

import javax.management.ObjectInstance;
// jmx imports
//
import javax.management.ObjectName;

import org.basic.net.c20_jmx.mbean.simple.SimpleStandardProxy;

import com.sun.jdmk.Proxy;
// jdmk imports
//
import com.sun.jdmk.comm.RemoteMBeanServer;
import com.sun.jdmk.comm.RmiConnectorAddress;
import com.sun.jdmk.comm.RmiConnectorClient;

public class ClientMBeanProxy {

    private RemoteMBeanServer connectorClient = null;

    // Constructor:
    // - sets the trace level,
    // - creates an RMI connector client (behaviour specified by the RemoteMBeanServer interface),
    // - creates a default RmiConnectorAddress object (host is this host, port is 1099),
    // - connects the RMI connector client to the RMI connector server of the Java DMK agent.
    //
    public ClientMBeanProxy() {

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
        ClientMBeanProxy client = new ClientMBeanProxy();
        echo("\npress <Enter> to continue...");
        waitForEnterPressed();

        // DO THE EXAMPLE:
        // only Standard MBeans can be managed through their corresponding proxy objects,
        // provided these MBean proxy objects have been previously generated with ProxyGen.
        //
        client.doMBeanProxyExample("SimpleStandard");

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

    private void doMBeanProxyExample(String mbeanName) {

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
            // create a proxy MBean on the client side that corresponds to the MBean in the agent
            //
            echo("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            echo("\n>>> CREATE Proxy MBean for " + mbeanName + " MBean");

            Proxy mbeanProxy = new SimpleStandardProxy(mbeanObjectInstance, connectorClient); // CREATE MBEAN PROXY

            echo("\tPROXY CLASS NAME      = " + mbeanProxy.getClass().getName());
            echo("\tMBEAN OBJECT NAME     = " + mbeanProxy.getMBeanObjectInstance().getObjectName());
            echo("\tCONNECTOR CLIENT      = " + mbeanProxy.getServer().getClass().getName());
            echo("<< done <<");

            // An alternate but heavier way is to first instantiate the proxy MBean,
            // and then to bind it to the connector client:
            echo("\n>>> CREATE another Proxy MBean for " + mbeanName + " MBean (alternate way)");
            Class proxyClass = Class.forName(connectorClient.getClassForProxyMBean(mbeanObjectInstance));
            Class[] signature = new Class[1];
            signature[0] = Class.forName("javax.management.ObjectInstance");
            Constructor proxyConstr = proxyClass.getConstructor(signature);
            Object[] initargs = new Object[1];
            initargs[0] = mbeanObjectInstance;
            Proxy proxy2 = (Proxy) proxyConstr.newInstance(initargs); // ALTERNATE PROXY CREATION AND BINDING
            proxy2.setServer(connectorClient);
            echo("\tPROXY CLASS NAME      = " + proxy2.getClass().getName());
            echo("\tMBEAN OBJECT NAME     = " + proxy2.getMBeanObjectInstance().getObjectName());
            echo("\tCONNECTOR CLIENT      = " + proxy2.getServer().getClass().getName());
            echo("<< done <<");
            // as we don't need proxy2, let's remove it from the connector client
            echo("\n>> Unregistering alternate proxy MBean");
            proxy2.setServer(null); // UNBINDING ALTERNATE PROXY
            echo("<< done <<");

            echo("\npress <Enter> to continue...");
            waitForEnterPressed();

            // ----------------
            // manage the MBean through its proxy
            //
            echo("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            manage(mbeanObjectInstance);
            echo("\npress <Enter> to continue...");
            waitForEnterPressed();

            // ----------------
            // unregister the MBean from the agent and unregister the MBean proxy from the connector client
            //
            echo("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            echo("\n>>> UNREGISTERING the " + mbeanName + " MBean and its proxy MBean");

            connectorClient.unregisterMBean(mbeanObjectName); // MBEAN DEREGISTRATION
            mbeanProxy.setServer(null); // PROXY DEREGISTRATION

            echo("<< done <<");
            echo("\npress <Enter> to continue...");
            waitForEnterPressed();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void manage(ObjectInstance mbeanObjectInstance) {

        echo("\n>>> MANAGING the MBean through its MBean proxy");
        try {

            // Get a reference to the proxy MBean...
            //
            Proxy mbeanProxy = new SimpleStandardProxy(mbeanObjectInstance, connectorClient); // GET PROXY REFERENCE

            echo("\tMBEAN OBJECT NAME     = " + mbeanProxy.getMBeanObjectInstance().getObjectName());
            echo("\tMBEAN CLASS NAME      = " + mbeanProxy.getMBeanObjectInstance().getClassName());

            // cast Proxy to SimpleStandardProxy
            SimpleStandardProxy simpleStandardProxy = (SimpleStandardProxy) mbeanProxy;

            // Get attribute values
            printAttributes(simpleStandardProxy);
            echo("\n\tpress <Enter> to continue...");
            waitForEnterPressed();

            // Change State attribute
            echo("\n    Setting State attribute to value \"new state from client\"...");
            simpleStandardProxy.setState("new state from client"); // SET ATTRIBUTE

            // Get attribute values
            printAttributes(simpleStandardProxy);
            echo("\n\tpress <Enter> to continue...");
            waitForEnterPressed();

            // Invoking reset operation
            echo("\n    Invoking reset operation...");
            simpleStandardProxy.reset(); // INVOKE OPERATION

            // Get attribute values
            printAttributes(simpleStandardProxy);

        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    private void printAttributes(SimpleStandardProxy mbeanProxy) {

        // we get each attribute through the proxy (which transmits calls to the connector client)
        //
        try {
            echo("\n    Getting attribute values:");

            echo("\tState     = \"" + mbeanProxy.getState() + "\""); // GET ATTRIBUTE

            echo("\tNbChanges = " + mbeanProxy.getNbChanges()); // GET ATTRIBUTE

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
