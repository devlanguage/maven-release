package org.basic.net.c20_jmx.jdmk.current.MLet;
/*
 * @(#)file      EquilateralTriangle.java
 * @(#)author    Sun Microsystems, Inc.
 * @(#)version   1.4
 * @(#)lastedit  04/02/02
 *
 * Copyright 2004 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

/**
 * Simple definition of a standard MBean, named "EquilateralTriangle".
 *
 * The "EquilateralTriangle" standard MBean shows how to expose
 * attributes and operations for management by implementing its
 * corresponding "EquilateralTriangleMBean" management interface.
 *
 * This MBean has one attribute exposed for management by a JMX agent:
 *      - the read/write "SideLength" attribute.
 */

public class EquilateralTriangle implements EquilateralTriangleMBean {

    //
    // -----------------------------------------------------
    // CONSTRUCTORS
    // -----------------------------------------------------
    //

    /**
     * Creates a new 5cm-side-length equilateral triangle object.
     */
    public EquilateralTriangle() {
    }

    /**
     * Creates a new equilateral triangle object with a given side length
     * (in cm).
     *
     * @param <VAR>sideLength</VAR> the new value of the "SideLength" attribute.
     */
    public EquilateralTriangle(Integer sideLength) {
	this.sideLength = sideLength;
    }

    //
    // --------------------------------------------------------
    // IMPLEMENTATION OF THE EquilateralTriangleMBean INTERFACE
    // --------------------------------------------------------
    //

    /**
     * Getter: get the "SideLength" attribute of the "EquilateralTriangleMBean"
     * standard MBean.
     *
     * @return the current value of the "SideLength" attribute.
     */
    public Integer getSideLength() {
        return sideLength;
    }

    /** 
     * Setter: set the "SideLength" attribute of the "EquilateralTriangleMBean"
     * standard MBean.
     *
     * @param <VAR>sideLength</VAR> the new value of the "SideLength" attribute.
     */
    public void setSideLength(Integer sideLength) {
        this.sideLength = sideLength;
    }

    //
    // ---------------------------------------------------
    // ATTRIBUTES ACCESSIBLE FOR MANAGEMENT BY A JMX AGENT
    // ---------------------------------------------------
    //

    /**
     * Default side length is 5 cm.
     */
    private Integer sideLength = new Integer(5);
}
