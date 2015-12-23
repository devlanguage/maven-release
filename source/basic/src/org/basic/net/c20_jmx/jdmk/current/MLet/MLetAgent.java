package org.basic.net.c20_jmx.jdmk.current.MLet;
/*
 * @(#)file      MLetAgent.java
 * @(#)author    Sun Microsystems, Inc.
 * @(#)version   1.16
 * @(#)lastedit  04/02/02
 *
 * Copyright 2004 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

// Java imports
//
import java.io.IOException;
import java.util.Set;
import java.util.Iterator;

// JMX imports
//
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectInstance;
import javax.management.ObjectName;

public class MLetAgent {

    public static void main(String[] args) {

	try {
	    // Parse command line arguments.
	    //
	    if (!parseArgs(args)) {
		System.exit(1);
	    }

	    // Instantiate the MBean server.
	    //
	    echo("\n>>> Create the MBean server");
	    MBeanServer server = MBeanServerFactory.createMBeanServer();
	    echo("\nPress <Enter> to continue...\n");
	    waitForEnterPressed();

	    // Get the domain name from the MBeanServer.
	    //
	    String domain = server.getDefaultDomain();

	    // Create a new MLet MBean and add it to the MBeanServer.
	    // 
	    echo("\n>>> Create a new MLet MBean:");
	    String mletClass = "javax.management.loading.MLet";
            ObjectName mletName = new ObjectName(domain + ":type=" + mletClass);
            server.createMBean(mletClass, mletName);
	    echo("\tOBJECT NAME = " + mletName);
	    echo("\nPress <Enter> to continue...\n");
	    waitForEnterPressed();

	    // Add a new URL to the MLet MBean to look for classes and
	    // resources.
	    //
	    echo("\n>>> Add a new URL to the MLet MBean to look for " +
		 "classes and resources:");
	    echo("\tURL = " + url_1);
	    Object mletParams_1[] = {url_1};
	    String mletSignature_1[] = {"java.lang.String"};
	    server.invoke(mletName, "addURL", mletParams_1, mletSignature_1);
	    echo("\nPress <Enter> to continue...\n");
	    waitForEnterPressed();

	    // Create a new Square MBean which is contained in the Square.jar
	    // file.
	    //
	    echo("\n>>> Create a new Square MBean which is contained " +
		 "in the Square.jar file:");
	    String squareClass = "Square";
            ObjectName squareName = new ObjectName("MLetExample:type=" +
						   squareClass);
	    Object squareParams[] = {new Integer(10)};
	    String squareSignature[] = {"java.lang.Integer"};
            server.createMBean(squareClass, squareName, mletName,
			       squareParams, squareSignature);
	    echo("\tOBJECT NAME = " + squareName);
	    echo("\nPress <Enter> to continue...\n");
	    waitForEnterPressed();

	    // Create and register new EquilateralTriangle MBeans by means of
	    // an HTML document containing MLET tags.
	    //
	    echo("\n>>> Create and register new EquilateralTriangle MBeans " +
		 "by means of an HTML document containing MLET tags:");
	    echo("\tURL = " + url_2);
	    Object mletParams_2[] = {url_2};
	    String mletSignature_2[] = {"java.lang.String"};
	    Set mbeanSet = (Set) server.invoke(mletName, "getMBeansFromURL",
					       mletParams_2, mletSignature_2);
	    for (Iterator i = mbeanSet.iterator(); i.hasNext(); ) {
		Object element = i.next();
		if (element instanceof ObjectInstance) {
		    echo("\tOBJECT NAME = " +
			 ((ObjectInstance)element).getObjectName());
		} else {
		    echo("\tEXCEPTION = " +
			 ((Throwable)element).getMessage());
		}
	    }
	    echo("\nPress <Enter> to continue...\n");
	    waitForEnterPressed();

	    // Create a new EquilateralTriangle MBean which is contained in the
	    // EquilateralTriangle.jar file.
	    //
	    echo("\n>>> Create a new EquilateralTriangle MBean which is " +
		 "contained in the EquilateralTriangle.jar file:");
	    String triangleClass = "EquilateralTriangle";
            ObjectName triangleName = new ObjectName("MLetExample:type=" +
						     triangleClass + ",id=3");
	    Object triangleParams[] = {new Integer(20)};
	    String triangleSignature[] = {"java.lang.Integer"};
            server.createMBean(triangleClass, triangleName, mletName,
			       triangleParams, triangleSignature);
	    echo("\tOBJECT NAME = " + triangleName);
	    echo("\nPress <Enter> to continue...\n");
	    waitForEnterPressed();

	    // Stop the agent
	    //
	    echo("\n\tPress <Enter> to stop the agent...\n");
	    waitForEnterPressed();
	    System.exit(0);

	} catch (Exception e) {
	    e.printStackTrace();
	    System.exit(1);
	}
    }

    /**
     * Parse the command line arguments.
     */
    private static boolean parseArgs(String argv[]) {
	if (argv.length != 2) {
            usage();
            return false;
        }
        url_1 = argv[0];
	url_2 = argv[1];
        return true;
    }

    /**
     * Print trace to standard output stream.
     */
    private static void echo(String msg) {
        System.out.println(msg);
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
        }
    }

    /**
     * Displays help information.
     */
    private static void usage() {
	echo("Usage: java MLetAgent <URL_for_addURL> " +
	     "<URL_for_getMBeansFromURL>");
	echo("where:");
	echo("\tURL_for_addURL: the URL used by the addURL method to look " +
	     "for classes and resources.");
	echo("\tURL_for_getMBeansFromURL: the URL pointing to the file " +
	     "containing the MLET tags.\n");
	echo("If the URL contains spaces, you have to enclose it within " +
	     "double-quotes.\n");
	echo("Example using Solaris:");
	echo("\tjava MLetAgent " +
	     "file:/opt/" + exdir + "/Square.jar " +
	     "file:/opt/" + exdir + "/GeometricShapes.html\n");
	echo("Example using Windows:");
	echo("\tjava MLetAgent " +
	     "\"file:/C:/Program Files/" + exdir + "/Square.jar\" " +
	     "\"file:/C:/Program Files/" + exdir + "/GeometricShapes.html\"\n");
    }

    // PRIVATE VARIABLES
    //
    private static String url_1 = null;
    private static String url_2 = null;
    private static final String exdir = "SUNWjdmk/5.1/examples/current/MLet";
}
