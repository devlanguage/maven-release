package org.basic.net.c20_jmx.jdmk.current.DynamicMBean;
/*
 * @(#)file      DynamicAgent.java
 * @(#)author    Sun Microsystems, Inc.
 * @(#)version   1.20
 * @(#)lastedit  04/02/02
 *
 * Copyright 2004 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

// Java imports
//
import java.io.IOException;

// JMX imports
//
import javax.management.Attribute;
import javax.management.MalformedObjectNameException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanConstructorInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanNotificationInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanParameterInfo;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;

public class DynamicAgent {

    /*
     * ------------------------------------------
     *  CONSTRUCTORS
     * ------------------------------------------
     */

    public DynamicAgent() {
        // CREATE the MBeanServer
        //
        echo("\n\tCREATE the MBeanServer.");
        server = MBeanServerFactory.createMBeanServer();
    }

    /*
     * ------------------------------------------
     *  PUBLIC METHODS
     * ------------------------------------------
     */

    public static void main(String[] args) {
        // START
        //
        echo(SEP_LINE);
        echo("\n>>> CREATE the agent...");
        DynamicAgent agent = new DynamicAgent();
        echo("\nPress <Enter> to continue...\n");
        waitForEnterPressed();

        // DO THE DEMO
        //
        agent.doSimpleDemo();

        // END
        //
        echo(SEP_LINE);
        echo("\n>>> END of the DynamicMBean example\n");
        echo("\n\tPress <Enter> to stop the agent...\n");
        waitForEnterPressed();
        System.exit(0);
    }

    /*
     * ------------------------------------------
     *  PRIVATE METHODS
     * ------------------------------------------
     */

    private void doSimpleDemo() {
        // Build the SimpleDynamic MBean ObjectName
        //
        ObjectName mbeanObjectName = null;
        String domain = server.getDefaultDomain();
        String mbeanName = "SimpleDynamic";
        try {
            mbeanObjectName = new ObjectName(domain + ":type=" + mbeanName);
        } catch (MalformedObjectNameException e) {
            echo("\t!!! Could not create the MBean ObjectName !!!");
            e.printStackTrace();
            echo("\nEXITING...\n");
            System.exit(1);
        }

        // Create and register the MBean
        //
        echo(SEP_LINE);
        createSimpleMBean(mbeanObjectName, mbeanName);
        echo("\nPress <Enter> to continue...\n");
        waitForEnterPressed();

        // Get and display the management information exposed by the MBean
        //
        echo(SEP_LINE);
        printMBeanInfo(mbeanObjectName, mbeanName);
        echo("\nPress <Enter> to continue...\n");
        waitForEnterPressed();

        // Manage the Simple Dynamic MBean
        // 
        echo(SEP_LINE);
        manageSimpleMBean(mbeanObjectName, mbeanName);
        echo("\nPress <Enter> to continue...\n");
        waitForEnterPressed();

        // Trying to perform illegal management operations...
        //
        echo(SEP_LINE);
        goTooFar(mbeanObjectName);
        echo("\nPress <Enter> to continue...\n");
        waitForEnterPressed();
    }

    private void createSimpleMBean(ObjectName mbeanObjectName,
                                   String mbeanName) {
        echo("\n>>> CREATE the " + mbeanName +
             " MBean within the MBeanServer:");
        String mbeanClassName = mbeanName;
        echo("\tOBJECT NAME           = " + mbeanObjectName);
        try {
            server.createMBean(mbeanClassName, mbeanObjectName);
        } catch (Exception e) {
            echo("\t!!! Could not create the " + mbeanName + " MBean !!!");
            e.printStackTrace();
            echo("\nEXITING...\n");
            System.exit(1);
        }
    }

    private void printMBeanInfo(ObjectName mbeanObjectName, String mbeanName) {
        echo("\n>>> Getting the management information for the " +
             mbeanName + " MBean");
        echo("    using the getMBeanInfo method of the MBeanServer");
        sleep(1000);
        MBeanInfo info = null;
        try {
            info = server.getMBeanInfo(mbeanObjectName);
        } catch (Exception e) {
            echo("\t!!! Could not get MBeanInfo object for " +
                 mbeanName + " !!!");
            e.printStackTrace();
            return;
        }
        echo("\nCLASSNAME: \t" + info.getClassName());
        echo("\nDESCRIPTION: \t" + info.getDescription());
        echo("\nATTRIBUTES");
        MBeanAttributeInfo[] attrInfo = info.getAttributes();
        if (attrInfo.length>0) {
            for (int i=0; i<attrInfo.length; i++) {
                echo(" ** NAME: \t" + attrInfo[i].getName());
                echo("    DESCR: \t" + attrInfo[i].getDescription());
                echo("    TYPE: \t" + attrInfo[i].getType() +
                     "\tREAD: " + attrInfo[i].isReadable() +
                     "\tWRITE: " + attrInfo[i].isWritable());
            }
        } else echo(" ** No attributes **");
        echo("\nCONSTRUCTORS");
        MBeanConstructorInfo[] constrInfo = info.getConstructors();
        for (int i=0; i<constrInfo.length; i++) {
            echo(" ** NAME: \t" + constrInfo[i].getName());
            echo("    DESCR: \t" + constrInfo[i].getDescription());
            echo("    PARAM: \t" + constrInfo[i].getSignature().length +
                 " parameter(s)");
        }
        echo("\nOPERATIONS");
        MBeanOperationInfo[] opInfo = info.getOperations();
        if (opInfo.length>0) {
            for (int i=0; i<opInfo.length; i++) {
                echo(" ** NAME: \t" + opInfo[i].getName());
                echo("    DESCR: \t" + opInfo[i].getDescription());
                echo("    PARAM: \t" + opInfo[i].getSignature().length +
                     " parameter(s)");
            }
        } else echo(" ** No operations ** ");
        echo("\nNOTIFICATIONS");
        MBeanNotificationInfo[] notifInfo = info.getNotifications();
        if (notifInfo.length>0) {
            for (int i=0; i<notifInfo.length; i++) {
                echo(" ** NAME: \t" + notifInfo[i].getName());
                echo("    DESCR: \t" + notifInfo[i].getDescription());
                String notifTypes[] = notifInfo[i].getNotifTypes();
                for (int j = 0; j < notifTypes.length; j++) {
                    echo("    TYPE: \t" + notifTypes[j]);
                }
            }
        } else echo(" ** No notifications **");
    }

    private void manageSimpleMBean(ObjectName mbeanObjectName,
                                   String mbeanName) {
        echo("\n>>> MANAGING the " + mbeanName + " MBean");
        echo("    using its attributes and operations exposed for management");

        try {
            // Get attribute values
            //
            sleep(1000);
            printSimpleAttributes(mbeanObjectName);

            // Change State attribute
            //
            sleep(1000);
            echo("\n    Setting State attribute to value \"new state\"...");
            Attribute stateAttribute = new Attribute("State", "new state");
            server.setAttribute(mbeanObjectName, stateAttribute);

            // Get attribute values
            //
            sleep(1000);
            printSimpleAttributes(mbeanObjectName);

            // Invoking reset operation
            //
            sleep(1000);
            echo("\n    Invoking reset operation...");
            server.invoke(mbeanObjectName, "reset", null, null);

            // Get attribute values
            //
            sleep(1000);
            printSimpleAttributes(mbeanObjectName);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    private void printSimpleAttributes(ObjectName mbeanObjectName) {
        try {
            echo("\n    Getting attribute values:");
            String state =
                (String) server.getAttribute(mbeanObjectName, "State");
            Integer nbChanges =
                (Integer) server.getAttribute(mbeanObjectName, "NbChanges");
            echo("\tState     = \"" + state + "\"");
            echo("\tNbChanges = " + nbChanges);
        } catch (Exception e) {
            echo("\t!!! Could not read attributes !!!");
            e.printStackTrace();
            return;
        }
    }

    private void goTooFar(ObjectName mbeanObjectName) {
        // Try to set the NbChanges attribute
        //
        echo("\n>>> Trying to set the NbChanges attribute (read-only)!");
        echo("\n... We should get an AttributeNotFoundException:\n");
        sleep(1000);
        Attribute nbChangesAttribute =
            new Attribute("NbChanges", new Integer(1));
        try {
            server.setAttribute(mbeanObjectName, nbChangesAttribute);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Try to access the NbResets property
        //
        echo("\n\n>>> Trying to access the NbResets property " +
             "(not exposed for management)!");
        echo("\n... We should get an AttributeNotFoundException:\n");
        sleep(1000);
        try {
            Integer NbResets =
                (Integer) server.getAttribute(mbeanObjectName, "NbResets");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return;
    }

    private static void echo(String msg) {
        System.out.println(msg);
    }

    private static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            return;
        }
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

    /*
     * ------------------------------------------
     *  PRIVATE VARIABLES
     * ------------------------------------------
     */

    private MBeanServer server = null;
    private static final String SEP_LINE =
        "\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" +
        "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~";
}
