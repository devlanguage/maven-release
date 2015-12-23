/****************************************************************************
 *                 TELLABS PROPRIETARY AND CONFIDENTIAL
 *              UNPUBLISHED WORK COPYRIGHT 2009 TELLABS
 *                          ALL RIGHTS RESERVED
 *      NO PART OF THIS DOCUMENT MAY BE USED OR REPRODUCED WITHOUT
 *                   THE WRITTEN PERMISSION OF TELLABS.
 *  Last modifed on 9:45:33 AM Jun 9, 2014
 *
 *****************************************************************************
 */
package org.basic.grammar.serializable;

import java.io.Serializable;

public class SerialBox implements Serializable {
    private int width;
    private int height;
    private transient String name = "serialBox";

    public SerialBox(String name, int width, int height) {
        this.name = name;
        this.width = width;
        this.height = height;
    }


    @Override
    public String toString() {
        return "[" + name + ": (" + width + ", " + height + ") ]";
    }
}
