package org.basic.net.c20_jmx.jdmk.current.ModelMBean;
/*
 * @(#)file      TestBeanFriend.java
 * @(#)author    Sun Microsystems, Inc.
 * @(#)version   1.3
 * @(#)lastedit  04/02/02
 *
 * Copyright 2004 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

// Java imports
//
import java.io.Serializable;

/**
 * Simple definition of a Java Bean, named "TestBeanFriend", which is managed
 * by a Model MBean.
 *
 * The "TestBeanFriend" Java Bean implements the same methods as "TestBean".
 * It is used by the ModelAgent test program to illustrate and demonstrate
 * how to have attributes in the same Model MBean supported by methods on
 * different objects.
 *
 * This Java Bean exposes for management two attributes and one operation:
 *      - the read/write "State" attribute,
 *      - the read only "NbChanges" attribute,
 *      - the "reset()" operation.
 *
 * Note that as "TestBean" explicitly defines one constructor,
 * this constructor must be public and exposed for management
 * through the ModelMBeanInfo object.
 */

public class TestBeanFriend implements Serializable {

    /*
     * -----------------------------------------------------
     * CONSTRUCTORS
     * -----------------------------------------------------
     */

    public TestBeanFriend() {
        echo("\n\tTestBeanFriend Constructor invoked: State " + state +
             " nbChanges: " + nbChanges + " nbResets: " + nbResets);
    }

    /*
     * -----------------------------------------------------
     * OTHER PUBLIC METHODS
     * -----------------------------------------------------
     */

    /**
     * Getter: get the "State" attribute of the "TestBeanFriend"
     * managed resource.
     */
    public String getState() {
        echo("\n\tTestBeanFriend: getState invoked: " + state);
        return state;
    }

    /**
     * Setter: set the "State" attribute of the "TestBeanFriend"
     * managed resource.
     */
    public void setState(String s) {
        state = s;
        nbChanges++;
        echo("\n\tTestBeanFriend: setState to " + state +
             " nbChanges: " + nbChanges);
    }

    /**
     * Getter: get the "NbChanges" attribute of the "TestBeanFriend"
     * managed resource.
     */
    public Integer getNbChanges() {
        echo("\n\tTestBeanFriend: getNbChanges invoked: " + nbChanges);
        return new Integer(nbChanges);
    }

    /**
     * Operation: reset to their initial values the "State" and "NbChanges" 
     * attributes of the "TestBeanFriend" managed resource.
     */
    public void reset() {
        echo("\n\tTestBeanFriend: reset invoked");
        state = "reset initial state";
        nbChanges = 0;
        nbResets++;
    }

    /**
     * Return the "NbResets" property.
     * This method is not a Getter in the JMX sense because
     * it is not returned by the getMBeanInfo() method.
     */
    public Integer getNbResets() {
        echo("\n\tTestBeanFriend: getNbResets invoked: " + nbResets);
        return new Integer(nbResets);
    }

    /*
     * -----------------------------------------------------
     * PRIVATE METHODS
     * -----------------------------------------------------
     */

    private void echo(String outstr) {
        System.out.println(outstr);
    }

    /*
     * -----------------------------------------------------
     * PRIVATE VARIABLES
     * -----------------------------------------------------
     */

    private String state = "initial state";
    private int nbChanges = 0;
    private int nbResets = 0;
}
