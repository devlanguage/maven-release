package org.basic.net.c20_jmx.jdmk.current.Monitor;
/*
 * @(#)file      Simple.java
 * @(#)author    Sun Microsystems, Inc.
 * @(#)version   4.5
 * @(#)lastedit  04/02/02
 *
 * Copyright 2004 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

public class Simple implements SimpleMBean {

    /*
     * ------------------------------------------
     *  PUBLIC METHODS
     * ------------------------------------------
     */

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

    public void reset() {
        nbChanges = 0;
    }

    /*
     * ------------------------------------------
     *  PRIVATE VARIABLES
     * ------------------------------------------
     */

    private String state = "initial state";
    private int nbChanges = 0;
}
