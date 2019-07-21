package org.basic.net.c20_jmx.jdmk.current.Queries;
/*
 * @(#)file      MethodMBMBean.java
 * @(#)author    Sun Microsystems, Inc.
 * @(#)version   1.5
 * @(#)lastedit  04/02/02
 *
 * Copyright 2004 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

// JMX imports
//
import javax.management.ObjectName;

/**
 * Management interface of a JMX compliant MBean representing a java class
 * method.
 *
 * The MethodMB MBean has the following attributes:
 *      - Name      : the name of the method that this MethodMB MBean represents
 *      - ClassName : the name of the class containing the method
 *      - Static    : tells if the method is static or not
 *      - Public    : tells if the method is public or not
 *      - NbParams  : the number of parameters of the method
 *      - Params    : an '_' separated string listing the method parameter types
 */

public interface  MethodMBMBean {

    /**
     * Getter for the "Name" attribute:
     * the name of the method that this MethodMB MBean represents.
     *
     * @return the current value of the "Name" attribute.
     */
    public String getName();

    /**
     * Getter for the "Params" attribute:
     * an '_' separated string listing the method parameter types.
     *
     * @return the current value of the "Params" attribute.
     */
    public String getParams();

    /**
     * Getter for the "NbParams" attribute:
     * the number of parameters of the method.
     *
     * @return the current value of the "NbParams" attribute.
     */
    public Integer getNbParams();

    /**
     * Getter for the "ClassName" attribute: 
     * the name of the class containing the method.
     *
     * @return the current value of the "ClassName" attribute.
     */
    public String getClassName();

    /**
     * Getter for the "Public" attribute:
     * tells if the method is public or not.
     *
     * @return the current value of the "Public" attribute.
     */
    public Boolean getPublic();

    /**
     * Getter for the "Static" attribute.:
     * tells if the method is static or not.
     *
     * @return the current value of the "Static" attribute.
     */
    public Boolean getStatic();
}
