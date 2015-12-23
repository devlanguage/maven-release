package org.basic.net.c20_jmx.jdmk.legacy.Context;
/*
 * @(#)file      SimpleMBean.java
 * @(#)author    Sun Microsystems, Inc.
 * @(#)version   1.3
 * @(#)lastedit  04/01/19
 *
 * Copyright 2004 Sun Microsystems, Inc.  All rights reserved. Use is subject to license terms.
 */

/**
 * @version     1.3     01/19/04
 * @author      Sun Microsystems, Inc
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
        
    public void performReset();
}
