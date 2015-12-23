package org.basic.net.c20_jmx.jdmk.legacy.Context;
/*
 * @(#)file      ContextClient.java
 * @(#)author    Sun Microsystems, Inc.
 * @(#)version   1.12
 * @(#)lastedit  04/01/19
 *
 * Copyright 2004 Sun Microsystems, Inc.  All rights reserved. Use is subject to license terms.
 */

// java import
//
import java.io.IOException;
import java.net.InetAddress;

// jmx import
//
import javax.management.MBeanInfo;
import javax.management.ObjectInstance;
import javax.management.ObjectName;

// jdmk import
//
import com.sun.jdmk.OperationContext;
import com.sun.jdmk.comm.AuthInfo;
import com.sun.jdmk.comm.CommunicationException;
import com.sun.jdmk.comm.ConnectorAddress;
import com.sun.jdmk.comm.HttpConnectorAddress;
import com.sun.jdmk.comm.HttpConnectorClient;
import com.sun.jdmk.comm.RemoteMBeanServer;
import com.sun.jdmk.comm.RmiConnectorAddress;
import com.sun.jdmk.comm.RmiConnectorClient;

public class ContextClient {
    public static void main(String argv[]) {

        try {
            /* Run the test, showing details for any exception it might
               generate.  */
            new ContextClient().test(argv);
        } catch (Exception e) {
            System.out.println("Got an exception !");
            System.out.println("Exception: " + e);
            System.out.println("Message: " + e.getMessage());
            if (e instanceof CommunicationException) {
                Throwable te = ((CommunicationException)e).getTargetException();
                if (te != null) {
                    System.out.println("Target Exception: " + te);
                    System.out.println("Target Message: " + te.getMessage());
                }
            }
            System.out.println("Stack Trace:");
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void test(String argv[]) throws Exception {

        // Debug
        //
//        TraceManager.parseTraceProperties();

        /* Parse command-line arguments:
           -host    followed by the name of the host the agent is running on
           (default is the local host);
           -port    followed by the port number the agent is running on
           (default is 1099 for RMI or 8081 for HTTP);
           -ident   followed by two words: the login and password to be used
           as the user identity for the HTTP connector (default is
           not to set any identity);
           -context followed by the string to be set as the result of the
           toString() on the context object associated with the
           connector (default is the string "nice");
           -rmi     use an RMI connector (default is an HTTP connector).  */
        String agentHost = null;
        int agentPort = -1;
        String login = null;
        String password = null;
        String contextName = "nice";
        boolean useRmi = false;

        final String Usage =
            "Usage: ContextClient [-host agent-host] [-port agent-port] " +
            "[-ident login password] [-context context-name] [-rmi]";

        try {
            for (int i = 0; i < argv.length; i++) {
                String arg = argv[i];
                if (arg.equals("-host"))
                    agentHost = argv[++i];
                else if (arg.equals("-port"))
                    agentPort = Integer.parseInt(argv[++i]);
                else if (arg.equals("-ident")) {
                    login = argv[++i];
                    password = argv[++i];
                } else if (arg.equals("-context"))
                    contextName = argv[++i];
                else if (arg.equals("-rmi"))
                    useRmi = true;
                else
                    throw new Exception();
            }
        } catch (Exception e) {
            /* We might get an ArrayIndexOutOfBoundsException or a
               NumberFormatException if the command line is bad.  */
            System.err.println(Usage);
            System.exit(1);
        }

        if (agentHost == null)
            agentHost = InetAddress.getLocalHost().getHostName();
        if (agentPort < 0)
            agentPort = useRmi ? 1099 : 8081;

        /* Initialize the client authentication info.  */
        AuthInfo authInfo = null;
        if (login != null) {
            if (useRmi)
                System.err.println("Warning: -ident has no meaning with " +
                                   "-rmi");
            authInfo = new AuthInfo(login, password);
        }

        OperationContext context = new StringOperationContext(contextName);

        System.out.println(">>> Connecting to " + agentHost +
                           " using port number " + agentPort);

        final RemoteMBeanServer connector =
            useRmi ?
            (RemoteMBeanServer) new RmiConnectorClient() :
            (RemoteMBeanServer) new HttpConnectorClient();

        connector.setOperationContext(context);

        ConnectorAddress addr = makeAddress(useRmi, agentHost, agentPort,
                                            authInfo);
	    
        System.out.println("BEGIN ---> connect()");
        connector.connect(addr);
        System.out.println("END -----> connect()");

        // Get MBeanServerId.
        //
        System.out.println("BEGIN ---> getMBeanServerId()");
        System.out.println("MBeanServerId = " + connector.getMBeanServerId());
        System.out.println("END -----> getMBeanServerId()");

        // Get DefaultDomain.
        //
        System.out.println("BEGIN ---> getDefaultDomain()");
        String domain = connector.getDefaultDomain();
        System.out.println("DefaultDomain = " + domain);
        System.out.println("END -----> getDefaultDomain()");

        domain = useRmi ? "RMI_1" : "HTTP_1";

        int i = 0;
        while (true) {
            i++;

            // Get MBeanCount.
            //
            System.out.println("BEGIN ---> getMBeanCount()");
            System.out.println("MBeanCount = " + connector.getMBeanCount());
            System.out.println("END -----> getMBeanCount()");

            // Create MBean.
            //
            System.out.println("BEGIN ---> createMBean()");
            System.out.println("createMBean");
            ObjectName on = new ObjectName(domain + ":name=Simple" + i);
            ObjectInstance oi = connector.createMBean("Simple", on);
            System.out.println("OI --> ClassName=" + oi.getClassName() +
                               " ObjectName=" + oi.getObjectName());
            System.out.println("END -----> createMBean()");

            // Check if registered.
            //
            System.out.println("BEGIN ---> isRegistered()");
            boolean reg = connector.isRegistered(on);
            System.out.println("Registered = " + reg);
            System.out.println("END -----> isRegistered()");

            // Get MBeanInfo.
            //
            System.out.println("BEGIN ---> getMBeanInfo()");
            MBeanInfo mbi = connector.getMBeanInfo(on);
            System.out.println("MBeanInfo = " + mbi);
            System.out.println("END -----> getMBeanInfo()");

            // Invoke operation on MBean.
            //
            System.out.println("BEGIN ---> invoke()");
            System.out.println("invoke");
            Object res = connector.invoke(on, "performReset", null, null);
            System.out.println("RESULT = " + res);
            System.out.println("END -----> invoke()");

            // Delete MBean.
            //
            System.out.println("BEGIN ---> unregisterMBean()");
            System.out.println("unregisterMBean");
            connector.unregisterMBean(oi.getObjectName());
            System.out.println("END -----> unregisterMBean()");

            // Wait for user to hit ENTER before looping.
            //
            boolean exit = waitForEnter();
            if (exit)
                break;
        }

        // Terminate communication with the remote MBean server.
        System.out.println("BEGIN ---> disconnect()");
        connector.disconnect();
        System.out.println("END -----> disconnect()");
    }

    // Make the Connector Address object we will be connecting to, based
    // on the information supplied on the command line.
    private static ConnectorAddress makeAddress(boolean useRmi,
						String agentHost,
                                                int agentPort,
                                                AuthInfo authInfo) {
        if (useRmi) {
            final String serviceName =
                com.sun.jdmk.ServiceName.RMI_CONNECTOR_SERVER;
            RmiConnectorAddress addr =
                new RmiConnectorAddress(agentHost, agentPort, serviceName);
            return addr;
        } else {
            HttpConnectorAddress addr =
                new HttpConnectorAddress(agentHost, agentPort, authInfo);
            return addr;
        }
    }

    /* In this example, the agent checks the OperationContext of
       each operation by examining its toString() method, so we
       define a simple implementation of OperationContext whose
       toString() is a constant string supplied at construction time.  */
    static class StringOperationContext
	  implements OperationContext, Cloneable {

	private String s;

	StringOperationContext(String s) {
	    this.s = s;
	}

	public String toString() {
	    return s;
	}

	public Object clone() throws CloneNotSupportedException {
	    return super.clone();
	}

    }

    // Wait for ENTER or EOF.  Return true if EOF (means we should exit).
    private static boolean waitForEnter() {
        System.out.println("\nPress <Enter> to continue...\n");
        try {
            int c;
            while ((c = System.in.read()) >= 0 && c != '\n')
                ;
            return (c < 0);
        } catch (IOException e) {
            return true;
        }
    }
}
