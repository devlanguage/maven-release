package org.basic.net.c20_jmx.jdmk.legacy.Context;
/*
 * @(#)file      ContextChecker.java
 * @(#)author    Sun Microsystems, Inc.
 * @(#)version   1.8
 * @(#)lastedit  04/01/19
 *
 * Copyright 2004 Sun Microsystems, Inc.  All rights reserved. Use is subject to license terms.
 */

// jmx import
//
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.QueryExp;

// jdmk import
//
import com.sun.jdmk.MBeanServerChecker;
import com.sun.jdmk.OperationContext;

/**
 * <p>This is a simple example of a context checker for a connector server such
 * as the HTTP connector server or the RMI connector server.  It is inserted
 * between the connector server and the MBeanServer.  Each method in the
 * MBeanServer interface is sent to it.  If it accepts the method, it passes
 * it through to the real MBeanServer, and passes the result back to the
 * caller.  If it does not accept the method, it throws an exception to reject
 * it (a SecurityException here, which is typical).</p>
 *
 * <p>This particular checker consults the connector client's OperationContext
 * for every operation, to see if the operation should be allowed.  An
 * operation is allowed if the OperationContext's toString() method returns
 * the string "nice".</p>
 *
 * <p>The checker also prints out messages to show which checking methods
 * are called and with what arguments.</p>
 */
public class ContextChecker extends MBeanServerChecker {
    public ContextChecker(MBeanServer mbs) {
        super(mbs);
    }

    protected void checkCreate(String methodName, String className,
                               ObjectName objectName, ObjectName loaderName,
                               Object[] params, String[] signature) {
        System.out.println("checkCreate(\"" + methodName + "\", " + className +
                           ", " + objectName + ", " + loaderName + ", " +
                           objectArrayString(params) + ", " +
                           objectArrayString(signature) + ")");
    }

    protected void checkInstantiate(String methodName, String className,
                                    ObjectName loaderName, Object[] params,
                                    String[] signature) {
        System.out.println("checkInstantiate(\"" + methodName + "\", " +
                           className + ", " + loaderName + ", " +
                           objectArrayString(params) + ", " +
                           objectArrayString(signature) + ")");
    }

    protected void checkDelete(String methodName, ObjectName objectName) {
        System.out.println("checkDelete(\"" + methodName + "\", " +
                           objectName + ")");
    }

    protected void checkRead(String methodName, ObjectName objectName) {
        System.out.println("checkRead(\"" + methodName + "\", " + objectName +
                           ")");
    }

    protected void checkWrite(String methodName, ObjectName objectName) {
        System.out.println("checkWrite(\"" + methodName + "\", " + objectName +
                           ")");
    }

    protected void checkQuery(String methodName, ObjectName name,
                              QueryExp query) {
        System.out.println("checkQuery(\"" + methodName + "\", " + name +
                           ", " + query + ")");
    }

    protected void checkInvoke(String methodName, ObjectName objectName,
                               String operationName, Object[] params,
                               String[] signature) {
        System.out.println("checkInvoke(\"" + methodName + "\", " +
                           objectName + ", " + operationName + ", " +
                           objectArrayString(params) + ", " +
                           objectArrayString(signature) + ")");
    }

    protected void checkNotification(String methodName,
                                     ObjectName objectName) {
        System.out.println("checkNotification(\"" + methodName + "\", " +
                           objectName + ")");
    }

    protected void checkDeserialize(String methodName,
                                    Object objectNameOrClass) {
        System.out.println("checkDeserialize(\"" + methodName + "\", " +
                           objectNameOrClass + ")");
    }

    /**
     * Concatenate the toString() methods of the objects in <code>a</code> into
     * a single comma-separated string for printing.
     */
    private static String objectArrayString(Object[] a) {
        if (a == null)
            return "null";
        StringBuffer buf = new StringBuffer("{");
        for (int i = 0; i < a.length; i++) {
            if (i > 0)
                buf.append(", ");
            buf.append(a[i]);
        }
        buf.append("}");
        return buf.toString();
    }

    /**
     * This method is called by every operation in the MBeanServer interface.
     * This is where we implement the check that requires every operation to
     * be called with an OperationContext whose toString() method returns
     * the string "nice".
     */
    protected void checkAny(String methodName, ObjectName objectName) {
        System.out.println("checkAny(\"" + methodName + "\", " + objectName);
        OperationContext context = getOperationContext();
        System.out.println("  OperationContext: " + context);

        if (context == null || !context.toString().equals("nice")) {
            RuntimeException ex =
                new SecurityException("  Bad context: " + context);
            ex.printStackTrace();
            throw ex;
        }
    }

}
