package org.basic.net.c20_jmx.jdmk.current.OpenMBean;

/*
 * @(#)file OpenAgent.java @(#)author Sun Microsystems, Inc. @(#)version 1.2 @(#)lastedit 04/02/02
 * 
 * Copyright 2004 Sun Microsystems, Inc. All rights reserved. SUN PROPRIETARY/CONFIDENTIAL. Use is
 * subject to license terms.
 */

// Java imports
//
import java.io.*;

// JMX imports
//
import javax.management.*;
import javax.management.openmbean.*;

public class OpenAgent {

    private static MBeanServer server = null;
    private static final String SEP_LINE = "\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"
            + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~";

    public static void main(String[] args) {

        SampleOpenMBean openMBean = null;
        ObjectName openMBeanObjectName = null;

        try {
            // Create an MBean server
            //
            echo(SEP_LINE);
            echo("\nCreate an MBeanServer");
            server = MBeanServerFactory.createMBeanServer();

            // Instantiate a SampleOpenMBean
            //
            echo("\nInstantiate a SampleOpenMBean");
            openMBean = new SampleOpenMBean();

            // Register in the MBean server the SampleOpenMBean
            //
            echo("\nRegister in the MBeanServer the SampleOpenMBean");
            openMBeanObjectName = new ObjectName("TestDomain:className=SampleOpenMBean");
            echo("ObjectName: " + openMBeanObjectName);
            server.registerMBean(openMBean, openMBeanObjectName);
            waitForEnterPressed();

            echo(SEP_LINE);
            printOpenMBeanInfo(openMBeanObjectName);
            waitForEnterPressed();

            echo(SEP_LINE);
            echo("\nBuild tShirt1: let default values for first 3 "
                    + "parameters: (null, null, null, new Float(10.0f))");
            CompositeData tShirt1 = openMBean.buildTShirt(null, null, null, new Float(10.0f));
            echo("BUILT tShirt1 = [" + tShirt1);
            echo("\nBuild tShirt2: specify all parameters: "
                    + "(\"JDMK\", \"red\", \"L\", new Float(15.0f))");
            CompositeData tShirt2 = openMBean.buildTShirt("JDMK", "red", "L", new Float(15.0f));
            echo("BUILT tShirt2 = [" + tShirt2);
            echo("\nAttempt to build tShirt with illegal model: "
                    + "(\"BADBAD\", \"red\", \"L\", new Float(15.0f))");
            try {
                openMBean.buildTShirt("BADBAD", "red", "L", new Float(15.0f));
            } catch (OpenDataException ode) {
                ode.printStackTrace();
            }
            echo("\nAttempt to build tShirt with illegal price: "
                    + "(\"JAVA\", \"blue\", \"XXL\", new Float(999.0f))");
            try {
                openMBean.buildTShirt("JAVA", "blue", "XXL", new Float(999.0f));
            } catch (OpenDataException ode) {
                ode.printStackTrace();
            }
            waitForEnterPressed();

            echo(SEP_LINE);
            echo("\nAdd tShirt1 to TabularData");
            echo("RETURN: " + openMBean.addTShirt(tShirt1));
            echo("\nBuild tShirt3: differs from tShirt1 with its "
                    + "price only: (null, null, null, new Float(15.0f))");
            CompositeData tShirt3 = openMBean.buildTShirt(null, null, null, new Float(15.0f));
            echo("BUILT tShirt3 = [" + tShirt3);
            echo("\nAttempt to add tShirt3 to TabularData: will fail as "
                    + "price is not part of the index");
            echo("(see SampleOpenMBean.java code)");
            echo("RETURN: " + openMBean.addTShirt(tShirt3));
            waitForEnterPressed();

            // END
            //
            echo(SEP_LINE);
            echo("\n>>> END of the OpenMBean example\n");
            echo("\n\tPress <Enter> to stop the agent...\n");
            waitForEnterPressed();
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    /**
     * Displays all OpenMBean information for the specified MBean.
     */
    private static void printOpenMBeanInfo(ObjectName openMBeanObjectName) {

        echo("\n>>> Getting the OpenMBeanInfo for MBean:\n\t" + openMBeanObjectName);
        sleep(1000);
        OpenMBeanInfo info = null;
        try {
            info = (OpenMBeanInfoSupport) server.getMBeanInfo(openMBeanObjectName);
        } catch (Exception e) {
            echo("\t!!! Could not get OpenMBeanInfo object for " + openMBeanObjectName + " !!!");
            e.printStackTrace();
            return;
        }
        echo("\nCLASSNAME   : " + info.getClassName());
        echo("\nDESCRIPTION : " + info.getDescription());

        // Attributes
        //
        echo("\n\n----ATTRIBUTES----\n");
        MBeanAttributeInfo[] attrInfos = info.getAttributes();
        if (attrInfos.length > 0) {
            OpenMBeanAttributeInfoSupport attrInfo;
            for (int i = 0; i < attrInfos.length; i++) {
                attrInfo = (OpenMBeanAttributeInfoSupport) attrInfos[i];
                echo(" -- ATTRIBUTE NAME : " + attrInfo.getName() + "\tREAD: "
                        + attrInfo.isReadable() + "\tWRITE: " + attrInfo.isWritable());
                echo("    DESCRIPTION    : " + attrInfo.getDescription());
                echo("    JAVA TYPE      : " + attrInfo.getType());
                echo("    OPEN TYPE      : " + attrInfo.getOpenType().getClass().getName());
                echo("    DEFAULT VALUE  : " + attrInfo.getDefaultValue());
                echo("    LEGAL VALUES   : " + attrInfo.getLegalValues());
                echo("    MIN VALUE      : " + attrInfo.getMinValue());
                echo("    MAX VALUE      : " + attrInfo.getMaxValue());
                echo("");
            }
        } else
            echo(" ** No attributes **\n");

        // Constructors
        //
        echo("\n\n----CONSTRUCTORS----\n");
        MBeanConstructorInfo[] constrInfos = info.getConstructors();
        if (constrInfos.length > 0) {
            OpenMBeanConstructorInfoSupport constrInfo;
            for (int i = 0; i < constrInfos.length; i++) {
                constrInfo = (OpenMBeanConstructorInfoSupport) constrInfos[i];
                echo(" -- CONSTRUCTOR NAME : " + constrInfo.getName());
                echo("    DESCRIPTION      : " + constrInfo.getDescription());
                MBeanParameterInfo[] paramInfos = constrInfo.getSignature();
                echo("    PARAMETERS       : " + paramInfos.length + " parameter"
                        + (paramInfos.length > 1 ? "s\n" : ""));
                if (paramInfos.length > 0) {
                    OpenMBeanParameterInfoSupport paramInfo;
                    for (int j = 0; j < paramInfos.length; j++) {
                        paramInfo = (OpenMBeanParameterInfoSupport) paramInfos[j];
                        echo("    ." + (j + 1) + ". PARAMETER NAME : " + paramInfo.getName());
                        echo("        DESCRIPTION    : " + paramInfo.getDescription());
                        echo("        JAVA TYPE      : " + paramInfo.getType());
                        echo("        OPEN TYPE      : "
                                + paramInfo.getOpenType().getClass().getName());
                        echo("        DEFAULT VALUE  : " + paramInfo.getDefaultValue());
                        echo("        LEGAL VALUES   : " + paramInfo.getLegalValues());
                        echo("        MIN VALUE      : " + paramInfo.getMinValue());
                        echo("        MAX VALUE      : " + paramInfo.getMaxValue());
                        echo("");
                    }
                }
                echo("");
            }
        } else
            echo(" ** No constructors **\n");

        // Operations
        //
        echo("\n\n----OPERATIONS----\n");
        MBeanOperationInfo[] opInfos = info.getOperations();
        if (opInfos.length > 0) {
            OpenMBeanOperationInfoSupport opInfo;
            for (int i = 0; i < opInfos.length; i++) {
                opInfo = (OpenMBeanOperationInfoSupport) opInfos[i];
                echo(" -- OPERATION NAME   : " + opInfo.getName());
                echo("    DESCRIPTION      : " + opInfo.getDescription());
                echo("    RETURN JAVA TYPE : " + opInfo.getReturnType());
                echo("    RETURN OPEN TYPE : " + opInfo.getReturnOpenType().getClass().getName());
                MBeanParameterInfo[] paramInfos = opInfo.getSignature();
                echo("    PARAMETERS       : " + paramInfos.length + " parameter"
                        + (paramInfos.length > 1 ? "s\n" : ""));
                if (paramInfos.length > 0) {
                    OpenMBeanParameterInfoSupport paramInfo;
                    for (int j = 0; j < paramInfos.length; j++) {
                        paramInfo = (OpenMBeanParameterInfoSupport) paramInfos[j];
                        echo("    ." + (j + 1) + ". PARAMETER NAME : " + paramInfo.getName());
                        echo("        DESCRIPTION    : " + paramInfo.getDescription());
                        echo("        JAVA TYPE      : " + paramInfo.getType());
                        echo("        OPEN TYPE      : "
                                + paramInfo.getOpenType().getClass().getName());
                        echo("        DEFAULT VALUE  : " + paramInfo.getDefaultValue());
                        echo("        LEGAL VALUES   : " + paramInfo.getLegalValues());
                        echo("        MIN VALUE      : " + paramInfo.getMinValue());
                        echo("        MAX VALUE      : " + paramInfo.getMaxValue());
                        echo("");
                    }
                }
                echo("");
            }
        } else
            echo(" ** No operations **\n");

        // Notifications
        //
        echo("\n\n----NOTIFICATIONS----\n");
        MBeanNotificationInfo[] notifInfos = info.getNotifications();
        if (notifInfos.length > 0) {
            for (int i = 0; i < notifInfos.length; i++) {
                echo(" -- NOTIFICATION NAME : " + notifInfos[i].getName());
                echo("    DESCRIPTION       : " + notifInfos[i].getDescription());
                String notifTypes[] = notifInfos[i].getNotifTypes();
                for (int j = 0; j < notifTypes.length; j++) {
                    echo("    TYPE              : " + notifTypes[j]);
                }
                echo("");
            }
        } else
            echo(" ** No notifications **\n");
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
            echo("\nPress Enter to continue...");
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
