package org.basic.net.c20_jmx;

import java.io.IOException;
import java.util.Set;

import javax.management.MBeanAttributeInfo;
import javax.management.MBeanConstructorInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanNotificationInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanServerConnection;
import javax.management.ObjectInstance;
import javax.management.ObjectName;

/**
 * <pre>
 *
 * </pre>
 */
public class MBeanUtil {

    public final static String JMX_HOST = "localhost";

    public final static int JMX_RMI_CONNECTOR_PORT = 18001;
    public final static int JMX_HTML_ADAPTER_PORT = 18002;

    public final static int JMX_HTTP_CONNECTOR_PORT = 18003;
    public final static int JMX_IIOP_CONNECTOR_PORT = 18004;
    public final static int JMX_SNMP_CONNECTOR_PORT = 18005;

    // service:jmx:rmi:///jndi/rmi://localhost:18000/jmxrmi
    public final static String JMX_RMI_URL = "service:jmx:rmi:///jndi/rmi://" + JMX_HOST + ":" + JMX_RMI_CONNECTOR_PORT + "/jmxrmi";
    // public final static String JMX_RMI_URL = "service:jmx:rmi:///jndi/rmi://" + JMX_HOST + ":" +
    // JMX_RMI_CONNECTOR_PORT + "/jmxtest";
    public static final String MBEAN_DOMAIN_JMXTEST_SIMPLE = "java.jmxtest.simple";

    public static void waitForEnterPressed() {

        try {
            System.out.println("\nPress <Enter> to continue...");
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void echo(String msg) {

        System.out.println(msg);
    }

    public static final void printAllMBeans(MBeanServerConnection mbsc) {
        try {
            System.out.println("Number of MBeans:---------------");
            System.out.println("MBean count = " + mbsc.getMBeanCount());

            // 把所有Domain都打印出来
            System.out.println("Domains:---------------");
            String domains[] = mbsc.getDomains();
            for (int i = 0; i < domains.length; i++) {
                System.out.println("\tDomain[" + i + "] = " + domains[i]);
            }

            // Print all the mbeans
            Set<ObjectName> mbeanNames = mbsc.queryNames(null, null);
            for (ObjectName mbeanObjectName : mbeanNames) {
                printMBeanInfo(mbsc, mbeanObjectName);
            }

            Set<ObjectInstance> mbeans = mbsc.queryMBeans(null, null);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public static void printMBeanInfo(MBeanServerConnection mbs, ObjectName mbeanObjectName) {

        echo("\n>>> Retrieve the management information for the " + mbeanObjectName);
        echo("    MBean using the getMBeanInfo() method of the MBeanServer");
        MBeanInfo info = null;
        try {
            info = mbs.getMBeanInfo(mbeanObjectName);
        } catch (Exception e) {
            echo("\t!!! Could not get MBeanInfo object for " + info.getClassName() + " !!!");
            e.printStackTrace();
            return;
        }
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
                String notifTypes[] = notifInfo[i].getNotifTypes();
                for (int j = 0; j < notifTypes.length; j++) {
                    echo("    TYPE: \t" + notifTypes[j]);
                }
            }
        } else
            echo(" ** No notifications **");
    }

    public final static String generateMBeanInfo(MBeanInfo mBeanInfo) {

        StringBuffer sb = new StringBuffer();
        try {

            sb.append("\n\nMBean Info: ");
            sb.append("\nClass: " + mBeanInfo.getClassName());
            sb.append("\nDescription: " + mBeanInfo.getDescription());
            sb.append("\nDescriptor: " + mBeanInfo.getDescriptor());

            sb.append("\n\nMBean Constructor Info: ");
            sb.append(generateMBeanConstructors(mBeanInfo.getConstructors()));

            sb.append("\n\nMBean Attribute Info: ");
            sb.append(generateMBeanAttributes(mBeanInfo.getAttributes()));

            sb.append("\n\nMBean Operation Info: ");
            sb.append(generateMBeanOperations(mBeanInfo.getOperations()));

            sb.append("\n\nMBean Notification Info: ");
            sb.append(generateMBeanNotifications(mBeanInfo.getNotifications()));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();

    }

    public final static String generateMBeanConstructors(MBeanConstructorInfo... beanConstructorInfos) {

        StringBuffer sb = new StringBuffer();
        for (MBeanConstructorInfo constructorInfo : beanConstructorInfos) {
            sb.append("\n").append("Name: " + constructorInfo.getName());
            sb.append("\n").append("Description: " + constructorInfo.getDescription());
            sb.append("\n").append("Descriptor: " + constructorInfo.getDescriptor());
            sb.append("\n").append("Signature: " + constructorInfo.getSignature());
        }
        return sb.toString();
    }

    public final static String generateMBeanAttributes(MBeanAttributeInfo... beanAttributeInfos) {

        StringBuffer sb = new StringBuffer();
        for (MBeanAttributeInfo attributeInfo : beanAttributeInfos) {
            sb.append("\n").append("Name: " + attributeInfo.getName());
            sb.append("\n").append("Description: " + attributeInfo.getDescription());
            sb.append("\n").append("Descriptor: " + attributeInfo.getDescriptor());
            sb.append("\n").append("Type: " + attributeInfo.getType());
        }
        return sb.toString();
    }

    public final static String generateMBeanOperations(MBeanOperationInfo... beanOperationInfos) {

        StringBuffer sb = new StringBuffer();
        for (MBeanOperationInfo beanOperationInfo : beanOperationInfos) {
            sb.append("\n").append("Name: " + beanOperationInfo.getName());
            sb.append("\n").append("Description: " + beanOperationInfo.getDescription());
            sb.append("\n").append("Descriptor: " + beanOperationInfo.getDescriptor());
            sb.append("\n").append("Impact: " + beanOperationInfo.getImpact());
            sb.append("\n").append("ReturnType: " + beanOperationInfo.getReturnType());
            sb.append("\n").append("Signature: " + beanOperationInfo.getSignature());
        }
        return sb.toString();
    }

    public final static String generateMBeanNotifications(MBeanNotificationInfo... beanNotificationInfos) {

        StringBuffer sb = new StringBuffer();
        for (MBeanNotificationInfo beanNotificationInfo : beanNotificationInfos) {
            sb.append("\n").append("Name: " + beanNotificationInfo.getName());
            sb.append("\n").append("Description: " + beanNotificationInfo.getDescription());
            sb.append("\n").append("Descriptor: " + beanNotificationInfo.getDescriptor());
            sb.append("\n").append("NotifTypes: " + beanNotificationInfo.getNotifTypes());
        }
        return sb.toString();
    }
}
