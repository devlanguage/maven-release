package org.basic.net.c20_jmx.jdmk.current.ModelMBean;
/*
 * @(#)file      TestBean.java
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
 * Simple definition of a Java Bean, named "TestBean", which is managed
 * by a Model MBean.
 *
 * The "TestBean" Java Bean shows how to expose for management attributes
 * and operations, at runtime, by instantiating and customizing the Model
 * MBean implementation. (See AgentModel code for this).
 *
 * This Java Bean exposes for management two attributes and one operation:
 *      - the read/write "State" attribute,
 *      - the read only "NbChanges" attribute,
 *      - the "reset()" operation.
 *
 * The program creating the Model MBean puts this information in
 * a ModelMBeanInfo object that is returned by the getMBeanInfo()
 * method of the DynamicMBean interface.
 *
 * The Model MBean implementation implements the access to its attributes
 * through the getAttribute(), getAttributes(), setAttribute(), and
 * setAttributes() methods of the DynamicMBean interface.
 *
 * The Model MBean implements the invocation of its reset() operation
 * through the invoke() method of the DynamicMBean interface.
 *
 * Note that as "TestBean" explicitly defines one constructor,
 * this constructor must be public and exposed for management
 * through the ModelMBeanInfo object.
 */

public class TestBean implements Serializable {

    /*
     * -----------------------------------------------------
     * CONSTRUCTORS
     * -----------------------------------------------------
     */

    public TestBean() {
        echo("\n\tTestBean Constructor Invoked: State " + state +
             " nbChanges: " + nbChanges + " nbResets: " + nbResets);
    }

    /*
     * -----------------------------------------------------
     * OTHER PUBLIC METHODS
     * -----------------------------------------------------
     */

    /**
     * Getter: get the "State" attribute of the "TestBean" managed resource.
     */
    public String getState() {
        echo("\n\tTestBean: getState invoked: " + state);
        return state;
    }

    /** 
     * Setter: set the "State" attribute of the "TestBean" managed resource.
     */
    public void setState(String s) {
        state = s;
        nbChanges++;
        echo("\n\tTestBean: setState to " + state + " nbChanges: " + nbChanges);
    }

    /**
     * Getter: get the "NbChanges" attribute of the "TestBean" managed resource.
     */
    public Integer getNbChanges() {
        echo("\n\tTestBean: getNbChanges invoked: " + nbChanges);
        return new Integer(nbChanges);
    }

    /**
     * Operation: reset to their initial values the "State" and "NbChanges"
     * attributes of the "TestBean" managed resource.
     */
    public void reset() {
        echo("\n\tTestBean: reset invoked");
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
        echo("\n\tTestBean: getNbResets invoked: " + nbResets);
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
