/**
 * Copyright reserved by Tellabs Communication Corp. LTD. The file
 * org.basic.jmx.examples.chat.clients.BaseAccessTest.java is created on 2008-1-7
 */
package org.basic.net.c20_jmx.jdmk.chat.clients;

import java.io.IOException;
import java.util.Set;

import javax.management.Attribute;
import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.IntrospectionException;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.Query;
import javax.management.QueryExp;
import javax.management.ReflectionException;
import javax.management.remote.JMXConnector;

import org.basic.net.c20_jmx.MBeanUtil;

/**
 * Type the command: jconsole and type the address:
 * service:jmx:rmi:///jndi/rmi://localhost:1099/jmxrmi <br>
 * Note: URL: service:jmx:iiop:///jndi/corbaname::1.2@hostname:6888#jmx/rmi/RMIConnectorServer,
 * service:jmx:rmi:///jndi/rmi://hostName:portNum/jmxrmi <br>
 * <br>
 * ClientAccess: JMXServiceURL u = new JMXServiceURL( "service:jmx:rmi:///jndi/rmi:// �?+ hostName +
 * ":" + portNum + "/jmxrmi"); JMXConnector c = JMXConnectorFactory.connect(u);
 * 
 * JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://127.0.0.1:9999/server");
 * JMXConnector jmxc = JMXConnectorFactory.connect(url); MBeanServerConnection mbsc =
 * jmxc.getMBeanServerConnection(); ObjectName mBeanName = new
 * ObjectName("HelloAgent:name=helloWorld"); mbsc.invoke(mBeanName,"printGreeting",null,null);
 * 
 */
public abstract class BaseAccessTest {

    protected static JMXConnector client = null;

    public void displayMBeanServerConnection(MBeanServerConnection mbeanServerConnection)
            throws IOException, InstanceNotFoundException, IntrospectionException,
            MalformedObjectNameException, ReflectionException, NullPointerException,
            MBeanException, AttributeNotFoundException, InvalidAttributeValueException {

        String domains[] = mbeanServerConnection.getDomains();
        for (int i = 0; i < domains.length; i++) {
            System.out.println("\tDomain[" + i + "] = " + domains[i]);
        }

        String mbeanInfo = MBeanUtil.generateMBeanInfo(mbeanServerConnection
                .getMBeanInfo(new ObjectName(
                        "ChatConnectorServer:name=RmiConnectorServer,port=8081")));
        System.out.println(mbeanInfo);

        // PrintMBean Detail
        ObjectName userMessageObjectName = new ObjectName(
                "ChatServerMBeans:type=org.basic.jmx.examples.chat.UserMessage");
        ObjectInstance mbean = mbeanServerConnection.getObjectInstance(userMessageObjectName);
        System.err.println(mbean.getClassName());

        // Invoke the method in the MBean
        mbeanServerConnection.invoke(userMessageObjectName, "sayHello",
                new Object[] { "Hello, I am rmi cilent." }, new String[] { "java.lang.String" });
        mbeanServerConnection.invoke(userMessageObjectName, "queryMBean", new Object[] { "*:*" },
                new String[] { "java.lang.String" });

        // Print the MBean Attribute
        System.out.println("Content: "
                + mbeanServerConnection.getAttribute(userMessageObjectName, "Content"));

        // Set the MBean Attribute
        Attribute contentAttribute = new Attribute("Content", "I am a new user");
        mbeanServerConnection.setAttribute(userMessageObjectName, contentAttribute);
        System.out.println("Attribute modified as Content: "
                + mbeanServerConnection.getAttribute(userMessageObjectName, "Content"));

        QueryExp queryExp = Query.match(Query.attr("Content"), Query.value("I am a new u*"));
        Set<ObjectInstance> mbeanInstances = mbeanServerConnection.queryMBeans(
                userMessageObjectName, queryExp);
        for (ObjectInstance mbeanInstance : mbeanInstances) {
            System.out.println(mbeanInstance.getObjectName());
        }

    }
}
