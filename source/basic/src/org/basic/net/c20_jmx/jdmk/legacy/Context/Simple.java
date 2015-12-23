package org.basic.net.c20_jmx.jdmk.legacy.Context;
/*
 * @(#)file      Simple.java
 * @(#)author    Sun Microsystems, Inc.
 * @(#)version   1.4
 * @(#)lastedit  04/01/19
 *
 * Copyright 2004 Sun Microsystems, Inc.  All rights reserved. Use is subject to license terms.
 */

import javax.management.ObjectName;
import javax.management.MBeanServer;
import javax.management.MBeanRegistration;

public class Simple implements SimpleMBean, MBeanRegistration {

    public ObjectName preRegister(MBeanServer server, ObjectName name) throws java.lang.Exception {
        mbs = server;
        //OpContext ctx = (OpContext) mbs.getOpContext(Thread.currentThread());
        //System.out.println("OPERATION_CONTEXT (MBEAN) = " + ctx.toString());
        return name;
    }

    public void postRegister(Boolean registrationDone) {
    }


    public void preDeregister() throws java.lang.Exception {
    }

    public void postDeregister() {
    }

    /*
     * ------------------------------------------
     *  PUBLIC METHODS
     * ------------------------------------------
     */
    
    public Simple() {
    }

    public String getState() {
        return state;
    }

    public void setState(String s) {
        state = s;
        nbChanges++;
    }

    public int getNbChanges() {
        return nbChanges;
    }

    public void performReset() {
        nbChanges = 0;
    }

    /*
     * ------------------------------------------
     *  PRIVATE VARIABLES
     * ------------------------------------------
     */
    private String        state = "initial state";
    private int           nbChanges = 0;
    private MBeanServer   mbs = null;
}
