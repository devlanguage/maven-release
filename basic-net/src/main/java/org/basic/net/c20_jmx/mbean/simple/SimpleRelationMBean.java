package org.basic.net.c20_jmx.mbean.simple;
/*
 * @(#)file      SimpleRelationMBean.java
 * @(#)author    Sun Microsystems, Inc.
 * @(#)version   1.4
 * @(#)lastedit  04/02/02
 *
 * Copyright 2004 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

// JMX imports
//
import javax.management.relation.*;

/**
 * This is the management interface explicitly defined for the "SimpleRelation"
 * standard MBean.
 *
 * The "SimpleRelation" standard MBean implements this interface in order to be
 * manageable through a JMX agent.
 *
 * The "SimpleRelationMBean" interface shows how to expose for management:
 * - a read/write attribute (named "State") through its getter and setter
 *   methods,
 * - a read-only attribute (named "NbChanges") through its getter method,
 * - an operation (named "reset").
 *
 * It extends also the RelationSupportMBean interface.
 */
public interface SimpleRelationMBean extends RelationSupportMBean {

    /**
     * Getter: set the "State" attribute of the "SimpleRelation" standard MBean.
     *
     * @return the current value of the "State" attribute.
     */
    public String getState();

    /**
     * Setter: set the "State" attribute of the "SimpleRelation" standard MBean.
     *
     * @param <VAR>s</VAR> the new value of the "State" attribute.
     */
    public void setState(String s);

    /**
     * Getter: get the "NbChanges" attribute of the "SimpleRelation" standard
     * MBean.
     *
     * @return the current value of the "NbChanges" attribute.
     */
    public Integer getNbChanges();

    /**
     * Operation: reset to their initial values the "State" and "NbChanges"
     * attributes of the "SimpleRelation" standard MBean.
     */
    public void reset();
}
