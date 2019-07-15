package org.basic.net.c20_jmx.hellojmx;

import static org.basic.net.c20_jmx.JmxUtil.waitForEnterPressed;

import java.util.Set;

import javax.management.Attribute;
import javax.management.MBeanServerConnection;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.basic.net.c20_jmx.mbean.hello.HelloNotificationFilter;
import org.basic.net.c20_jmx.mbean.hello.HelloNotificationListener;

public class JmxClient {

    /**
     * <pre>
     * For Message Queue applications (which always use the RMI protocol for JMX connections), the JMX service URL has the following syntax:
     * 
     * service:jmx:rmi://[host[:port]][urlPath]
     * 
     * Although host and port may be included, they are ignored by the RMI protocol. If urlPath is specified, it gives the Java Naming and Directory Interface (JNDI) location of an RMI stub (typically a location within an RMI registry) in the form
     * 
     * /jndi/jndiName
     * 
     * For example, the URL
     * 
     * service:jmx:rmi://myhost/jndi/rmi://myhost:1099/myhost/myjmxconnector
     * 
     * specifies an RMI stub at the location
     * 
     * rmi://myhost:1099/myhost/myjmxconnector
     * 
     * which is an RMI registry running at location myhost/myjmxconnector on port 1099 of host myhost.
     * 
     * Alternatively, if urlPath is omitted from the service URL, the JMX connector server will generate a client URL containing the actual RMI stub embedded within it in encoded and serialized form. For example, the service URL
     * 
     * service:jmx:rmi://localhost
     * 
     * will generate a client URL of the form
     * 
     * service:jmx:rmi://localhost/stub/rmiStub
     * 
     * where rmiStub is an encoded and serialized representation of the RMI stub itself.
     * </pre>
     * 
     */
    public static void main(String[] args) {

        try {// service:jmx:rmi:///jndi/rmi://localhost:9999/server
            JMXServiceURL serviceURL = new JMXServiceURL(
                    "service:jmx:rmi:///jndi/rmi://localhost:18001/test");
            JMXConnector jmxConnector = JMXConnectorFactory.connect(serviceURL, null);

            MBeanServerConnection mbeanServerConn = jmxConnector.getMBeanServerConnection();
            System.out.println("MbeanCount:" + mbeanServerConn.getMBeanCount());

            Set<ObjectInstance> mbeans = mbeanServerConn.queryMBeans(null, null);
            for (ObjectInstance mbean : mbeans) {
                System.out.println(mbean);
            }
            waitForEnterPressed();

            // ObjectName helloMbean = new ObjectName("domainName", "id", "HelloWorldMBean");
            ObjectName helloMbeanName = new ObjectName("domainName:id=HelloWorldMBean");
            ObjectInstance helloMbeanOI = mbeanServerConn.getObjectInstance(helloMbeanName);
            mbeanServerConn.addNotificationListener(helloMbeanName,
                    new HelloNotificationListener(), new HelloNotificationFilter(), "handback");
            System.out.println(helloMbeanOI);

            System.out.println("Update the Attribute: Greeting");
            mbeanServerConn.setAttribute(helloMbeanName, new Attribute("Greeting",
                    "updated: newValue"));
            System.out.println("Call the Method: printGreeting()");
            mbeanServerConn.invoke(helloMbeanName, "printGreeting", new Object[] {}, null);

            Set<ObjectInstance> mbeanSet = mbeanServerConn.queryMBeans(new ObjectName(
                    "domain*:id=*"), null);
            System.out.println(mbeanSet);
            Set<ObjectName> mbeanNameSet = mbeanServerConn.queryNames(
                    new ObjectName("domain*:id=*"), null);
            System.out.println(mbeanNameSet);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
