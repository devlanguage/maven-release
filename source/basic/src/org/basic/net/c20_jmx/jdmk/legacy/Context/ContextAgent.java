package org.basic.net.c20_jmx.jdmk.legacy.Context;
/*
 * @(#)file      ContextAgent.java
 * @(#)author    Sun Microsystems, Inc.
 * @(#)version   1.8
 * @(#)lastedit  04/01/19
 *
 * Copyright 2004 Sun Microsystems, Inc.  All rights reserved. Use is subject to license terms.
 */

// java imports
//

// jmx import
//
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

import com.sun.jdmk.comm.AuthInfo;
import com.sun.jdmk.comm.HttpConnectorServer;
import com.sun.jdmk.comm.RmiConnectorServer;
// jdmk import
//

public class ContextAgent {

    /* The constructor is passed the command-line arguments that were
     * given to the main method.  The arguments are in pairs, each
     * pair defining a login and password to be added to the
     * authentification list of the HTTP connector server.
     */
    
    public ContextAgent(String[] args) {
        
        int firstarg = 0;

        // Authentication Stuff
        //
        if ((args.length - firstarg) % 2 != 0) {
            System.err.println("Odd number of arguments");
            System.err.println("Arguments should be in pairs: login password");
            System.exit(1);
        }
        boolean doAuthentication = (args.length > firstarg);
        AuthInfo[] authInfoList;
        if (doAuthentication) {
            authInfoList = new AuthInfo[(args.length - firstarg) / 2];
            for (int i = firstarg, j = 0; i < args.length; i += 2, j++)
                authInfoList[j] = new AuthInfo(args[i], args[i + 1]);
        } else
            authInfoList = null;

        // Create MBeanServer
        //
        MBeanServer server = MBeanServerFactory.createMBeanServer();

        /* Create context checker.  The argument to the constructor is
         * the MBeanServer above which we intend to insert this
         * checker.  After a successful check, the checker will
         * forward any operation to this MBeanServer.
         */
        ContextChecker contextChecker = new ContextChecker(server);

        // Debug
        //
//        try {
//            TraceManager.parseTraceProperties();
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.exit(1);
//        }

        trace(">>> CREATE a new HTTP connector");
        HttpConnectorServer http = new HttpConnectorServer();
        ObjectName http_name = null;
        try {
            http_name = new ObjectName("Connector:name=http,port=8081");
        } catch (MalformedObjectNameException e) {
            e.printStackTrace();
            System.exit(1);
        }
        try {
            server.registerMBean(http, http_name);
        } catch(Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        /* Add the context checker to this HTTP connector server.  We
         * are inserting the context checker between the connector
         * server and the MBeanServer, and we do not expect there to
         * be any other objects in between (stackable MBeanServers).
         * So we check that first.
         */
        synchronized (http) {
            if (http.getMBeanServer() != server) {
                System.err.println("After registering connector MBean, " +
                                   "http.getMBeanServer() != " +
                                   "our MBeanServer");
                System.exit(1);
            }
            http.setMBeanServer(contextChecker);
        }

        /* Build the authentication list in the HTTP connector server based on
         * the logins and passwords supplied on the command line. */
        if (doAuthentication) {
            for (int i = 0; i < authInfoList.length; i++)
                http.addUserAuthenticationInfo(authInfoList[i]);
        }

        /* Start the HTTP connector server.  Note that this must be done
         * AFTER the context checker is inserted.  Connector servers refuse
         * to allow stackable MBeanServer objects such as context checkers
         * to be inserted when they are "live".  */
        http.start();

        /* Create an RMI connector and set its context checker exactly as for
         * the HTTP connector. */
        trace(">>> CREATE a new RMI connector");
        RmiConnectorServer rmi = new RmiConnectorServer();
        ObjectName rmi_name = null;
        try {
            rmi_name = new ObjectName("Connector:name=rmi,port=1099,service=RmiConnectorServer");
        } catch (MalformedObjectNameException e) {
            e.printStackTrace();
            System.exit(1);
        }
        try {
            server.registerMBean(rmi, rmi_name);
        } catch(Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        synchronized (rmi) {
            if (rmi.getMBeanServer() != server) {
                System.err.println("After registering connector MBean, " +
                                   "rmi.getMBeanServer() != our " +
                                   "MBeanServer");
                System.exit(1);
            }
            rmi.setMBeanServer(contextChecker);
        }
        rmi.start();

    }

    /*
     * ------------------------------------------
     *  PUBLIC METHODS
     * ------------------------------------------
     */
    
    public static void main(String[] args) {
        trace("\n>>> CREATE and START the Agent...");
        ContextAgent agent = new ContextAgent(args);
    }

    /*
     * ------------------------------------------
     *  PRIVATE METHODS
     * ------------------------------------------
     */

    private static void trace(String msg) {
        System.out.println(msg);
    }
}
