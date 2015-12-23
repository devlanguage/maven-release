package org.basic.net.c20_jmx.jdmk.current.Queries;
/*
 * @(#)file      ClassMBMBean.java
 * @(#)author    Sun Microsystems, Inc.
 * @(#)version   1.5
 * @(#)lastedit  04/02/02
 *
 * Copyright 2004 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

/**
 * Management interface of a JMX compliant MBean representing a java class.
 *
 * This interface defines the following attributes of the ClassMB MBean:
 *      - Name   : the full name of the class that the ClassMB MBean represents
 *      - Public : specifies if the class is public or not
 */

public interface ClassMBMBean {

    /**
     * Getter for the "Name" attribute:
     * it is the full name of the class that the ClassMB MBean represents
     *
     * @return the current value of the "Name" attribute.
     */
    public String getName();

    /**
     * Getter for the "Public" attribute:
     * it tells if the class that the ClassMB MBean represents is public or not.
     *
     * @return the current value of the "Public" attribute.
     */
    public Boolean getPublic();    
}
