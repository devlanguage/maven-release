package org.basic.net.c20_jmx.jdmk.current.Security.jmxmp.tls_factory.server;
/*
 * @(#)file      Server.java
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
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;
import javax.net.ssl.*;

public class Server {

    public static void main(String[] args) {
        try {
            // Instantiate the MBean server
            //
            System.out.println("\nCreate the MBean server");
            MBeanServer mbs = MBeanServerFactory.createMBeanServer();

            // Environment map
            //
            System.out.println("\nInitialize the environment map");
            HashMap env = new HashMap();

            // Initialize the SSLSocketFactory
            //
            String keystore = "config" + File.separator + "keystore";
            char keystorepass[] = "password".toCharArray();
            char keypassword[] = "password".toCharArray();
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(new FileInputStream(keystore), keystorepass);
            KeyManagerFactory kmf =
                KeyManagerFactory.getInstance("SunX509");
            kmf.init(ks, keypassword);
            SSLContext ctx = SSLContext.getInstance("TLSv1");
            ctx.init(kmf.getKeyManagers(), null, null);
            SSLSocketFactory ssf = ctx.getSocketFactory();

            // The profile supported by this server is TLS
            //
            env.put("jmx.remote.profiles", "TLS");

            // Provide the previously initialized SSL socket factory and
            // tell the JSSE stack to use the TLSv1 protocol and the
            // SSL_RSA_WITH_NULL_MD5 cipher suite
            //
            env.put("jmx.remote.tls.socket.factory", ssf);
            env.put("jmx.remote.tls.enabled.protocols", "TLSv1");
            env.put("jmx.remote.tls.enabled.cipher.suites",
                    "SSL_RSA_WITH_NULL_MD5");

            // Create a JMXMP connector server
            //
            System.out.println("\nCreate a JMXMP connector server");
            JMXServiceURL url = new JMXServiceURL("jmxmp", null, 5555);
            JMXConnectorServer cs =
                JMXConnectorServerFactory.newJMXConnectorServer(url, env, mbs);

            // Start the JMXMP connector server
            //
            System.out.println("\nStart the JMXMP connector server");
            cs.start();
            System.out.println("\nJMXMP connector server successfully started");
            System.out.println("\nWaiting for incoming connections...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
