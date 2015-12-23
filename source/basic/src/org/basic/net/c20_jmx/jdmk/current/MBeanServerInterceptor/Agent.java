package org.basic.net.c20_jmx.jdmk.current.MBeanServerInterceptor;
/*
 * @(#)file      Agent.java
 * @(#)author    Sun Microsystems, Inc.
 * @(#)version   1.7
 * @(#)lastedit  04/04/21
 *
 * Copyright 2004 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

// Java imports
//
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.Set;

// JMX imports
//
import javax.management.InstanceNotFoundException;
import javax.management.MalformedObjectNameException;
import javax.management.MBeanException;
import javax.management.MBeanServer;
import javax.management.MBeanServerDelegate;
import javax.management.MBeanServerFactory;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.ReflectionException;

// Java DMK imports
//
import com.sun.jdmk.interceptor.DefaultMBeanServerInterceptor;
import com.sun.jdmk.interceptor.MBeanServerInterceptor;
import com.sun.jdmk.ServiceName;
import com.sun.jdmk.JdmkMBeanServer;
import com.sun.jdmk.comm.CommunicatorServer;
import com.sun.jdmk.comm.HtmlAdaptorServer;

/**
 * This class implements a simple Java DMK agent which instantiates a
 * {@link MasterMBeanServerInterceptor} and plugs in a
 * {@link FileMBeanServerInterceptor}.
 *
 * The <code>FileMBeanServerInterceptor</code> simulates virtual MBeans
 * which represent files and directories.
 *
 * @see DefaultMBeanServerInterceptor
 * @see FileMBeanServerInterceptor
 * @see MasterMBeanServerInterceptor
 */
public class Agent {

    private MBeanServer mbs;

    private static final String SEP_LINE =
        "\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" +
        "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~";

    /**
     * Constructor:
     *   - instantiates the MBean server of this agent,
     *   - retrieves the MBean server ID from the MBean server delegate.
     */
    public Agent() {
        // Instantiates the MBean server
        //
        echo("\n\tInstantiating the MBean server of this agent...");
        mbs = MBeanServerFactory.createMBeanServer();
        echo("\tdone");

        // Retrieves ID of the MBean server from the associated MBean
        // server delegate
        //
        echo("\n\tGetting the ID of the MBean server from " +
             " the associated MBean server delegate...");
        try {
            final ObjectName delegateName =
                new ObjectName(ServiceName.DELEGATE);
            final String mBeanServerId = (String)
                mbs.getAttribute(delegateName, "MBeanServerId");
            echo("\tID = " + mBeanServerId);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        echo("\tdone");
    }

    /**
     * This method creates a new {@link FileMBeanServerInterceptor} and
     * inserts it in the MBeanServer. This method first creates a
     * {@link FileMBeanServerInterceptor}, and then plugs it into a
     * {@link MasterMBeanServerInterceptor} that will route requests to either
     * the {@link DefaultMBeanServerInterceptor} or the
     * {@link FileMBeanServerInterceptor}, depending on the domain part of the
     * MBean's ObjectName. It then inserts the
     * {@link MasterMBeanServerInterceptor} into the given MBeanServer.
     *
     * @param server The MBeanServer where the new FileMBeanServerInterceptor
     * is to be inserted.
     * @param domain The domain name reserved for the MBeans managed
     * by the FileMBeanServerInterceptor.
     *
     * @return The new FileMBeanServerInterceptor.
     *
     * @exception IllegalArgumentException if the given MBeanServer is
     * null, or does not support {@link com.sun.jdmk.MBeanServerInterceptor}s.
     */
    public static FileMBeanServerInterceptor insertFileInterceptor(
						       MBeanServer server,
						       String domain) {
        // The server must not be null
        //
        if (server == null)
	    throw new IllegalArgumentException("MBeanServer can't be null");

        // The server must support MBeanServerInterceptors
        //
        if (!(server instanceof JdmkMBeanServer))
	    throw new IllegalArgumentException(
		      "MBeanServer does not support MBeanServerInterceptor");

        // We get the server's DefaultMBeanServerInterceptor. We will pass
	// this interceptor to our MasterMBeanServerInterceptor so that no
	// MBeans are lost.
        //
        final MBeanServerInterceptor defaultInterceptor =
            ((JdmkMBeanServer)server).getMBeanServerInterceptor();

        // We get the MBeanServerDelegate. We will pass the MBeanServerDelegate
	// to our FileMBeanServerInterceptor so that we can fake the creation
	// and destruction of MBeans by sending MBeanServerNotifications through
	// the delegate.
        //
        final MBeanServerDelegate delegate =
            ((JdmkMBeanServer)server).getMBeanServerDelegate();

        // We create the FileMBeanServerInterceptor. The
	// FileMBeanServerInterceptor handles virtual MBeans which represent
	// files and directories. All these MBeans will share the same domain.
	// No other MBean should use this domain. This is not imposed by Java
	// DMK MBeanServerInterceptors, but it is a design choice in this
	// example: both the FileMBeanServerInterceptor and the
	// MasterMBeanServerInterceptor implementations we provide here are
	// designed to work that way. The FileMBeanServerInterceptor will use
	// the MBeanServerDelegate in order to fake the creation and destruction
	// of MBeans by sending MBeanServerNotifications at startup, and when
	// the operation "cd" is invoked on a virtual Directory MBean. The
	// FileMBeanServerInterceptor will use the MBeanServer in order to
	// evaluate QueryExp objects.
        //
        final FileMBeanServerInterceptor fileInterceptor =
            new FileMBeanServerInterceptor(domain, delegate, server);

        // We create a new MasterMBeanServerInterceptor that will route requests
        // to either the DefaultMBeanServerInterceptor, or the
	// FileMBeanServerInterceptor.
	//
        // The MasterMBeanServerInterceptor will use the domain part of the
	// ObjectName in order to decide to which interceptor the requests
	// should be forwarded.
	//
        // The MasterMBeanServerInterceptor is needed so that the MBeanServer
	// can behave as a regular MBeanServer. If we had simply set the
        // FileMBeanServerInterceptor as default interceptor of the MBeanServer
        // then:
        // * all MBeans already registered in the DefaultMBeanServerInterceptor
        //   would be lost (this includes the HTML adaptor and also the
	//   MBeanServerDelegate),
        // * No user MBean creation or unregistration would be possible,
        //   since the FileMBeanServerInterceptor only handles its own virtual
        //   MBean.
	//
        // The MasterMBeanServerInterceptor makes it possible to keep the MBeans
        // from the DefaultMBeanServerInterceptor accessible, and reroute only
        // those requests that are targeted to the virtual file MBeans to
        // the FileMBeanServerInterceptor.
        //
        final MBeanServerInterceptor master =
            new MasterMBeanServerInterceptor(defaultInterceptor,
					     fileInterceptor,
					     domain);

        // We set the MasterMBeanServerInterceptor as the MBeanServer's default
        // MBeanServerInterceptor.
        // 
        ((JdmkMBeanServer)server).setMBeanServerInterceptor(master);

        return fileInterceptor;
    }

    /**
     * This method activates the FileSystem simulation.
     * <p>
     * It first calls <tt>insertFileInterceptor()</tt> in order to
     * insert a {@link FileMBeanServerInterceptor} into the MBeanServer,
     * and then starts the inserted {@link FileMBeanServerInterceptor} on
     * the current directory.
     */
    public void showFiles() {
        echo("\n\tInserting the FileMBeanServerInterceptor in this agent...");
        FileMBeanServerInterceptor fileInterceptor =
            insertFileInterceptor(mbs,"file");
        echo("\tdone");
        echo("\n\tStarting the FileMBeanServerInterceptor...");
        fileInterceptor.start(".");
        echo("\tdone");
    }

    public static void main(String[] args) {
        // START
        //
        echo(SEP_LINE);
        echo("Creating an instance of this Agent...");
        Agent myAgent = new Agent();
        echo("\ndone");

        // ADD THE HTML PROTOCOL ADAPTOR
        //
        myAgent.addHtmlAdaptor();
        echo(SEP_LINE);
        String localHost;
        try {
            localHost = java.net.InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            localHost = "localhost";
        }
        echo("\nNow, you can point your browser to http://" +
	     localHost + ":8082/");
        echo("to view this agent through the HTML protocol adaptor.");
	echo("\nSince we have not inserted the FileMBeanServerInterceptor yet");
	echo("only the HtmlAdaptor and the MBeanServerDelegate MBeans are");
	echo("visible.");

        // ACTIVATE FILESYSTEM SIMULATION
        //
        echo("\nPress <Enter> to insert the FileMBeanServerInterceptor and ");
        echo("view the files from the local directory as virtual MBeans.");
        waitForEnterPressed();
        myAgent.showFiles();
        echo("\nNow, you can ask your browser to reload http://" +
             localHost + ":8082/");
        echo("in order to view the new MBeans.");

        // END
        //
        echo("\nPress <Enter> to stop the agent...");
        waitForEnterPressed();

        // Remove the MBeans that we have added, i.e. the HTML adaptor.
        // Removes all MBeans except the delegate and the MBeans from
	// the "file" domain.
        //
        myAgent.removeMBeans("file");

        echo(SEP_LINE);
        System.exit(0);
    }

    public void addHtmlAdaptor() {

        // *******************************
        // Adding an HTML protocol adaptor
        // *******************************

        // Below we illustrate one method of adding an MBean in an MBean server:
        //   - first, we instantiate the HTML protocol adaptor
        //   - then, we register it into the MBean server
        //
        echo(SEP_LINE);
        echo("Instantiating an HTML protocol adaptor with default port...");

        // Default constructor: sets port to default 8082
        //
        CommunicatorServer htmlAdaptor = new HtmlAdaptorServer();
        echo("\tPROTOCOL    = " + htmlAdaptor.getProtocol());
        echo("\tPORT        = " + htmlAdaptor.getPort());
        echo("done");

        echo("\nRegistering the HTML protocol adaptor, with default name, " +
             "in the MBean server...");
        try {
            // We let the HTML protocol adaptor provide its default name
            //
            ObjectInstance htmlAdaptorInstance =
                mbs.registerMBean(htmlAdaptor, null);
            echo("\tCLASS NAME  = " +
                 htmlAdaptorInstance.getClassName());
            echo("\tOBJECT NAME = " +
                 htmlAdaptorInstance.getObjectName().toString());
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        echo("done");

        // Now we explicitly start the HTML protocol adaptor as it is not
        // started automatically
        //
        echo("\nStarting the HTML protocol adaptor...");
        echo("\tSTATE       = " + htmlAdaptor.getStateString());
        htmlAdaptor.start();

        // Waiting to leave starting state...
        //
        while (htmlAdaptor.getState() == CommunicatorServer.STARTING) {
            echo("\tSTATE       = " + htmlAdaptor.getStateString());
            sleep(1000);
        }
        echo("\tSTATE       = " + htmlAdaptor.getStateString());

        // Check that the HTML protocol adaptor is started
        //
        if (htmlAdaptor.getState() != CommunicatorServer.ONLINE) {
            echo("Cannot start the HTML protocol adaptor.");
            echo("Check that the port is not already in use.");
            System.exit(1);
        }
        echo("done");
    }

    public void removeMBeans(String excludedDomain) {
        echo(SEP_LINE);
        try {
            echo("Unregistering all MBeans except " +
                 "the MBean server delegate");
            if (excludedDomain != null)
                echo("and the MBeans from the \"" + excludedDomain +
                     ":\" domain.\n");
            else 
                echo("");
            echo("    Current MBean count = " + 
                 mbs.getMBeanCount() + "\n");

            Set allMBeans = mbs.queryNames(null, null);
            for (Iterator i = allMBeans.iterator(); i.hasNext(); ) {
                ObjectName name = (ObjectName) i.next();
                if (name ==  null) continue;
                if (name.toString().equals(ServiceName.DELEGATE)) continue;
                if (excludedDomain != null &&
                    name.getDomain().equals(excludedDomain)) continue;
                echo("\tUnregistering " + name.toString());
                mbs.unregisterMBean(name);
            }
            echo("\n    Current MBean count = " + 
                 mbs.getMBeanCount() + "\n");
            echo("done\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void echo(String msg) {
        System.out.println(msg);
    }

    private static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            return;
        }
    }

    private static void waitForEnterPressed() {
        try {
            boolean done = false;
            while (!done) {
                char ch = (char) System.in.read();
                if (ch < 0 || ch == '\n') {
                    done = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
}
