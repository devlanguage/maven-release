package org.basic.net.c20_jmx.jdmk.current.Security.jmxmp.sasl_privacy.server;
/*
 * @(#)file      Server.java
 * @(#)author    Sun Microsystems, Inc.
 * @(#)version   1.2
 * @(#)lastedit  04/05/10
 *
 * Copyright 2004 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

import java.io.File;
import java.security.Security;
import java.util.HashMap;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;
import javax.security.sasl.Sasl;

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

            // Add SASL/DIGEST-MD5 mechanism server provider
            //
	    // This line should be commented out when running on J2SE 1.5
	    // as the DIGEST-MD5 SASL server mechanism provider is part of
	    // the platform.
	    //
            Security.addProvider(new com.sun.security.sasl.Provider());

            // The profile required by this server is SASL/DIGEST-MD5
            //
            env.put("jmx.remote.profiles", "SASL/DIGEST-MD5");

            // Set DIGEST-MD5 QOP to authentication plus integrity and
	    // confidentiality protection
            //
            env.put(Sasl.QOP, "auth-conf");

            // Set the server's fully qualified hostname required by
            // the DIGEST-MD5 SASL mechanism. If the system property
            // is not set the server's name will default to the value
            // returned by calling getLocalAddress().getHostName() on
            // the socket.
            //
            final String fqhn = System.getProperty("server.name");
            if (fqhn != null)
                env.put("jmx.remote.x.sasl.server.name", fqhn);

            // Callback handler used by the DIGEST-MD5 SASL server mechanism
            // to perform user authentication
            //
            env.put("jmx.remote.sasl.callback.handler",
		    new DigestMD5ServerCallbackHandler("config" +
						       File.separator +
						       "password.properties",
						       null,
						       null));

            // Provide the access level file used by the connector server to
            // perform user authorization. The access level file is a properties
            // based text file specifying username/access level pairs where
            // access level is either "readonly" or "readwrite" access to the
            // MBeanServer operations. This properties based access control
            // checker has been implemented using the MBeanServerForwarder
            // interface which wraps the real MBean server inside an access
            // controller MBean server which performs the access control checks
            // before forwarding the requests to the real MBean server.
            //
            // This property is implementation-dependent and might not be
	    // supported by all implementations of the JMX Remote API.
            //
            env.put("jmx.remote.x.access.file",
		    "config" + File.separator + "access.properties");

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
