package org.basic.net.c20_jmx.jdmk.hello;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

public class RMIManager {

    public static void main(String[] args) {

        // RmiConnectorClient client = new RmiConnectorClient();
        // RmiConnectorAddress address = new RmiConnectorAddress();
        // address.setPort(6868);
        try {
            // client.connect(address);
            // ObjectName helloWorldName = ObjectName.getInstance("HelloAgent:name=helloWorld1");
            // client.invoke(helloWorldName, "sayHello", null, null);
            // client.setAttribute(helloWorldName, new Attribute("Hello", new String("hello
            // girls!")));
            // client.invoke(helloWorldName, "sayHello", null, null);
            // Create a JMXMP connector client
            // 
            System.out.println("\nCreate a JMXMP connector client");
//            JMXServiceURL url = new JMXServiceURL("service:jmx:jmxmp://localhost:9999");

            JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:9999/server");
            JMXConnector jmxc = JMXConnectorFactory.connect(url, null);
            MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();

            // Get domains from MBeanServer, create SimpleStandard MBean
            // 
            String domains[] = mbsc.getDomains();
            for (int i = 0; i < domains.length; i++) {
                System.out.println("Domain[" + i + "] = " + domains[i]);
                ObjectName mbeanName = new ObjectName("MBeans:type=SimpleStandard");
                mbsc.createMBean("SimpleStandard", mbeanName, null, null);
            }
            // Perform simple MBean operations, add listener, reset MBean,
            // remove listener, unregister MBean

            // [...]

            // Close MBeanServer connection
            // 
            jmxc.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        // try {
        // JMXServiceURL url = new JMXServiceURL("jdmk-http", null, 6868);
        // connect(url);
        //
        // url = new JMXServiceURL("jdmk-rmi", null, 8888, "/myRMI");
        // connect(url);
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
        // System.exit(0);

    }

}
