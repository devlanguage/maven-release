package org.basic.net.c20_jmx.jdmk.current.Monitor;
/*
 * @(#)file      StandardObservedObject.java
 * @(#)author    Sun Microsystems, Inc.
 * @(#)version   4.6
 * @(#)lastedit  04/02/02
 *
 * Copyright 2004 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

// JMX imports
//
import javax.management.*;

public class StandardObservedObject
    implements StandardObservedObjectMBean, MBeanRegistration {

    /*
     * ------------------------------------------
     *  PUBLIC METHODS
     * ------------------------------------------
     */

    public ObjectName preRegister(MBeanServer server, ObjectName name)
        throws Exception {
        if (name == null)
            name = new ObjectName(server.getDefaultDomain() +
                                  ":type=" + this.getClass().getName());
        this.server = server;
        return name;
    }

    public void postRegister(Boolean registrationDone) {
    }

    public void preDeregister() throws Exception {
    }

    public void postDeregister() {
    }

    // GETTERS AND SETTERS
    //--------------------
    
    public Integer getNbObjects() {
        try {
            return new Integer((server.queryMBeans(new ObjectName("*:*"),
                                                   null)).size());
        } catch (Exception e) {
            return new Integer(-1);
        }
    }

    /*
     * ------------------------------------------
     *  PRIVATE VARIABLES
     * ------------------------------------------
     */

    private MBeanServer server = null;
}
