package org.basic.net.c20_jmx.jdmk.current.Monitor;
/*
 * @(#)file      SimpleMBean.java
 * @(#)author    Sun Microsystems, Inc.
 * @(#)version   4.5
 * @(#)lastedit  04/02/02
 *
 * Copyright 2004 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

public interface SimpleMBean {

    /*
     * ------------------------------------------
     *  PUBLIC METHODS
     * ------------------------------------------
     */

    public String getState();

    public void setState(String s);

    public int getNbChanges();

    public void reset();
}
