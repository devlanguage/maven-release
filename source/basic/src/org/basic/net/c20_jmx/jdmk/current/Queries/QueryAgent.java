package org.basic.net.c20_jmx.jdmk.current.Queries;

/*
 * @(#)file QueryAgent.java @(#)author Sun Microsystems, Inc. @(#)version 1.11 @(#)lastedit 04/02/02
 * 
 * Copyright 2004 Sun Microsystems, Inc. All rights reserved. SUN PROPRIETARY/CONFIDENTIAL. Use is
 * subject to license terms.
 */

// Java imports
//
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

// JMX imports
//
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.QueryExp;

/**
 * The purpose of this example is to illustrate how query expressions are used as filters and allow
 * to retrieve MBeans from within an MBean server according to their attribute values.
 * 
 * The MBeans defined for this agent are - ClassMB : this MBean represents a java class - MethodMB :
 * this MBean represents a method of a java class
 * 
 * The classes that the agent must register as class MBeans can be given as arguments. If none is
 * provided, the agent will use a few basic classes as example.
 * 
 * Each class that is registered in the MBean server also registers the methods that are defined
 * within this class.
 * 
 * The agent then applies a set of queries when getting the objects from the MBean Server. The
 * queries that are applied can be examined by looking at the Queries class.
 */

public class QueryAgent {

    /**
     * The main method allows you to specify the name of classes which this agent is going to
     * register. An MBean of class ClassMB is created for each class in the "Classes.mine" domain.
     * Its object name "Classes.mine:class=ClassMB,name=<the-argument-class>".
     * 
     * Furthermore, for each public method of those classes (including inherited methods), an object
     * name of type MethodMB is registered into the "Methods.myMethods" domain. Its object name is
     * based on the method class name and the method parameters.
     */
    public static void main(String args[]) {

        // Instantiate the MBean server
        //
        echo(SEP_LINE);
        MBeanServer server = MBeanServerFactory.createMBeanServer();
        echo("\nCreated the MBean server");

        // Get the class names for which ClassMB MBeans are to be created
        //
        String[] classes;
        if (args.length == 0) {
            classes = new String[2];
            classes[0] = "java.lang.String";
            classes[1] = "java.io.File";
        } else {
            classes = args;
        }

        // Create the ClassMB MBeans
        //
        echo("\nCreating the ClassMB MBeans in the MBean server...\n");
        int iclass;
        for (iclass = 0; iclass < classes.length; iclass++) {
            // Build the params and signature for MBean server to
            // instantiate MBean using the appropriate constructor
            //
            Object[] constrParams = new Object[1];
            constrParams[0] = classes[iclass];
            String[] constrSignature = new String[1];
            constrSignature[0] = "java.lang.String";
            ObjectName objName = null; // MBean will provide its object name
            try {
                // Have MBean server create the ClassMB MBean
                //
                server.createMBean("ClassMB", objName, constrParams, constrSignature);
            } catch (Exception e) {
                echo("Problem creating ClassMB MBean for class " + classes[iclass]);
                e.printStackTrace();
                System.exit(-1);
            }
        }

        // Do the queries
        //
        echo(SEP_LINE);
        QueryAgent.doQueries(server);

        // END
        //
        echo(SEP_LINE);
        echo("\n>>> END of the Queries example\n");
        echo("\n\tPress <Enter> to stop the agent...\n");
        waitForEnterPressed();
        System.exit(0);
    }

    private static void doQueries(MBeanServer server) {

        // Apply the queries to our objects
        //
        QueryExp aQry;
        for (int i = 1; (aQry = Queries.query(i)) != null; i++) {
            echo("\n-" + i + "- Getting objects that validate the following query :");
            echo("\n\t" + aQry + "\n");

            // The result of the queryMBeans method is a set
            // of ObjectInstance objects
            //
            Set objInsts = null;
            try {
                // The query request:
                //
                ObjectName pattern = new ObjectName("*:*");
                // i.e. the scope of the query is all MBeans
                objInsts = server.queryMBeans(pattern, aQry);
            } catch (Exception e) {
                echo("Problem getting objects");
                e.printStackTrace();
            }

            if (objInsts == null || objInsts.size() == 0) {
                echo("No match");
            } else {
                for (Iterator j = objInsts.iterator(); j.hasNext();) {
                    ObjectInstance objInst = (ObjectInstance) j.next();
                    echo("\t" + objInst.getObjectName());
                }
            }
            waitForEnterPressed();
        }
    }

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

    private static final String SEP_LINE = "\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"
            + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~";
}
