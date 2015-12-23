/**
 * Tellabs Inc. ShangHai R&D Center, 2008 Copyright JMXSupporter.java Desc: Created by: ggong
 * Created Time: 4:01:44 PM Updated by: Updated Time:
 */
package org.basic.net.c20_jmx.hellojmx;

import static org.basic.net.c20_jmx.JmxUtil.JMX_HOST;
import static org.basic.net.c20_jmx.JmxUtil.JMX_HTML_ADAPTER_PORT;
import static org.basic.net.c20_jmx.JmxUtil.JMX_HTTP_CONNECTOR_PORT;
import static org.basic.net.c20_jmx.JmxUtil.JMX_RMI_CONNECTOR_PORT;

import java.rmi.registry.LocateRegistry;
import java.util.HashMap;
import java.util.Set;

import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;

import org.basic.net.c20_jmx.mbean.hello.HelloDynamicMBean;
import org.basic.net.c20_jmx.mbean.hello.HelloWorld;
import org.basic.net.c20_jmx.mbean.hello.HelloWorldMBean;
import org.basic.net.c20_jmx.mbean.user.UserManager;
import org.basic.net.c20_jmx.mbean.user.UserManagerMBean;

public class JmxServer {

    public static void registerJMXServer(int jmxport) {

        try {
            LocateRegistry.createRegistry(jmxport);

            javax.management.MBeanServer mbeanServer = java.lang.management.ManagementFactory.getPlatformMBeanServer();
            // MBeanServer mbeanServer = javax.management.MBeanServerFactory.createMBeanServer();
            // MBeanServer mbeanServer =
            // javax.management.MBeanServerFactory.createMBeanServer("DefaultDomain");
/*
            // HtmlAdapter
            com.sun.jdmk.comm.HtmlAdaptorServer htmlAdapter = new HtmlAdaptorServer();
            htmlAdapter.setPort(JMX_HTML_ADAPTER_PORT);
            htmlAdapter.start();
            ObjectName adapterName = new ObjectName("HelloAgent:name=htmladapter,port=" + JMX_HTML_ADAPTER_PORT);
            mbeanServer.registerMBean(htmlAdapter, adapterName);
            System.out.println("HtmlAdapterServer Started, port=" + JMX_HTML_ADAPTER_PORT);

            // HttpConnectorServer
            HttpConnectorServer httpConnector = new HttpConnectorServer();
            httpConnector.setPort(JMX_HTTP_CONNECTOR_PORT);
            httpConnector.start();
            System.out.println("HttpConnectorServer Started, port=" + JMX_HTTP_CONNECTOR_PORT);*/

            // RMIConnectorServer
            HashMap<String, Object> env = new HashMap<String, Object>();
            javax.management.remote.JMXServiceURL rmiUrl = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://" + JMX_HOST + ":" + jmxport
                    + "/test");
            javax.management.remote.JMXConnectorServer jmxRmiConnServer1 = JMXConnectorServerFactory.newJMXConnectorServer(rmiUrl, env,
                    mbeanServer);
//            RMIConnectorServer jmxRmiConnServer2 = new RMIConnectorServer(rmiUrl, env, mbeanServer);

//            MemoryMXBean mmxBean = ManagementFactory.getMemoryMXBean();
//            ObjectName mmxObj = new ObjectName("domainName:id=MemoryMXBean");// domainName:key=value
//            mbeanServer.registerMBean(mmxBean, mmxObj);

//            OperatingSystemMXBean osMxBean = ManagementFactory.getOperatingSystemMXBean();
//            ObjectName osMxObj = new ObjectName("domainName:id=OperatingSystemMXBean");
//            mbeanServer.registerMBean(osMxBean, osMxObj);

            UserManagerMBean userMxBean = new UserManager();
            ObjectName userMxObj = new ObjectName("domainName:id=UserManagerMXBean");
            mbeanServer.registerMBean(userMxBean, userMxObj);

            HelloWorldMBean helloMxBean = new HelloWorld();
            ObjectName helloMxObj = new ObjectName("domainName:id=HelloWorldMBean");
            mbeanServer.registerMBean(helloMxBean, helloMxObj);

            jmxRmiConnServer1.start();
            System.out.println("JmxConnectorServer started,domain=" + mbeanServer.getDefaultDomain() + ", port=" + JMX_RMI_CONNECTOR_PORT);

            ObjectName objectName_DynHello = new ObjectName("Test:id=HelloDynamicMBean.java");
            mbeanServer.createMBean(HelloDynamicMBean.class.getName(), objectName_DynHello);

            // Define the MBean Name
            ObjectName objectName_user01 = new ObjectName("domainName:key1=value1,key2=value2");
            ObjectName objectName_user02 = new ObjectName("domainName:id=UserMBean_02");
            UserManagerMBean userMxBean01 = new UserManager();
            // Register MBean by MbeanServer
            mbeanServer.registerMBean(userMxBean01, objectName_user01);
            // Create MBean by MbeanServer
            mbeanServer.createMBean(UserManager.class.getName(), objectName_user02);
            // Query MBean by MbeanServer
            Set<ObjectInstance> mbeanInstances = mbeanServer.queryMBeans(new ObjectName("*:*"), null);
            Set<ObjectName> mbeanNames = mbeanServer.queryNames(new ObjectName("*:id=*"), null);

            // Call MBean by MbeanServer
            mbeanServer.invoke(objectName_user01, "sayHi", new Object[] { "tester" }, new String[] { "java.lang.String" });
            mbeanServer.getAttribute(objectName_user01, "UserName");
            mbeanServer.getMBeanInfo(objectName_user01);
            // mbeanServer.addNotificationListener(objectName01, NotificationListener, listener,
            // filter, handback)

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void registerJMXServer() {
        registerJMXServer(JMX_RMI_CONNECTOR_PORT);
    }

    public static void main(String[] args) {

        new JmxServer().registerJMXServer();
    }
}
