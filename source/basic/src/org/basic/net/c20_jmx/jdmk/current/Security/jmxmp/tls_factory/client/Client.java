package org.basic.net.c20_jmx.jdmk.current.Security.jmxmp.tls_factory.client;

/*
 * @(#)file      Client.java
 * @(#)author    Sun Microsystems, Inc.
 * @(#)version   1.1
 * @(#)lastedit  04/02/05
 *
 * Copyright 2004 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.util.HashMap;

import javax.management.Attribute;
import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import org.basic.net.c20_jmx.mbean.simple.SimpleStandardMBean;

public class Client {

    public static void main(String[] args) {
        try {
            // Environment map
            //
            System.out.println("\nInitialize the environment map");
            HashMap env = new HashMap();

            // Initialize the SSLSocketFactory
            //
            String truststore = "config" + File.separator + "truststore";
            char truststorepass[] = "trustword".toCharArray();
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(new FileInputStream(truststore), truststorepass);
            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
            tmf.init(ks);
            SSLContext ctx = SSLContext.getInstance("TLSv1");
            ctx.init(null, tmf.getTrustManagers(), null);
            SSLSocketFactory ssf = ctx.getSocketFactory();

            // The profile required by this client is TLS
            //
            env.put("jmx.remote.profiles", "TLS");

            // Provide the previously initialized SSL socket factory and
            // tell the SSL stack to use the TLSv1 protocol and the
            // SSL_RSA_WITH_NULL_MD5 cipher suite
            //
            env.put("jmx.remote.tls.socket.factory", ssf);
            env.put("jmx.remote.tls.enabled.protocols", "TLSv1");
            env.put("jmx.remote.tls.enabled.cipher.suites", "SSL_RSA_WITH_NULL_MD5");

            // Create a JMXMP connector client and
            // connect it to the JMXMP connector server
            //
            System.out.println("\nCreate a JMXMP connector client and " + "connect it to the JMXMP connector server");
            JMXServiceURL url = new JMXServiceURL("jmxmp", null, 5555);
            JMXConnector jmxc = JMXConnectorFactory.connect(url, env);

            // Get an MBeanServerConnection
            //
            System.out.println("\nGet an MBeanServerConnection");
            MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();

            // Get domains from MBeanServer
            //
            System.out.println("\nDomains:");
            String domains[] = mbsc.getDomains();
            for (int i = 0; i < domains.length; i++) {
                System.out.println("\tDomain[" + i + "] = " + domains[i]);
            }

            // Create SimpleStandard MBean
            //
            ObjectName mbeanName = new ObjectName("MBeans:type=SimpleStandard");
            System.out.println("\nCreate SimpleStandard MBean...");
            mbsc.createMBean("SimpleStandard", mbeanName, null, null);

            // Get MBean count
            //
            System.out.println("\nMBean count = " + mbsc.getMBeanCount());

            // Get State attribute
            //
            System.out.println("\nState = " + mbsc.getAttribute(mbeanName, "State"));

            // Set State attribute
            //
            mbsc.setAttribute(mbeanName, new Attribute("State", "changed state"));

            // Get State attribute
            //
            // Another way of interacting with a given MBean is through a
            // dedicated proxy instead of going directly through the MBean
            // server connection
            //
            SimpleStandardMBean proxy = (SimpleStandardMBean) MBeanServerInvocationHandler.newProxyInstance(mbsc, mbeanName,
                    SimpleStandardMBean.class, false);
            System.out.println("\nState = " + proxy.getState());

            // Add notification listener on SimpleStandard MBean
            //
            ClientListener listener = new ClientListener();
            System.out.println("\nAdd notification listener...");
            mbsc.addNotificationListener(mbeanName, listener, null, null);

            // Invoke "reset" in SimpleStandard MBean
            //
            // Calling "reset" makes the SimpleStandard MBean emit a
            // notification that will be received by the registered
            // ClientListener.
            //
            System.out.println("\nInvoke reset() in SimpleStandard MBean...");
            mbsc.invoke(mbeanName, "reset", null, null);

            // Sleep for 2 seconds in order to have time to receive the
            // notification before removing the notification listener.
            //
            System.out.println("\nWaiting for notification...");
            Thread.sleep(2000);

            // Remove notification listener on SimpleStandard MBean
            //
            System.out.println("\nRemove notification listener...");
            mbsc.removeNotificationListener(mbeanName, listener);

            // Unregister SimpleStandard MBean
            //
            System.out.println("\nUnregister SimpleStandard MBean...");
            mbsc.unregisterMBean(mbeanName);

            // Close MBeanServer connection
            //
            System.out.println("\nClose the connection to the server");
            jmxc.close();
            System.out.println("\nBye! Bye!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
