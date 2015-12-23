/****************************************************************************
 *                 TELLABS PROPRIETARY AND CONFIDENTIAL
 *              UNPUBLISHED WORK COPYRIGHT 2009 TELLABS
 *                          ALL RIGHTS RESERVED
 *      NO PART OF THIS DOCUMENT MAY BE USED OR REPRODUCED WITHOUT
 *                   THE WRITTEN PERMISSION OF TELLABS.
 *  Last modifed on 2:13:14 PM Apr 29, 2014
 *
 *****************************************************************************
 */
package org.basic.security.jaas.secret;

import java.io.File;

import javax.security.auth.Subject;

import org.apache.activemq.filter.function.makeListFunction;

/**
 * Created on Apr 29, 2014, 2:13:14 PM
 */
public class CountFilesAction<T extends Integer> implements java.security.PrivilegedAction<T> {

    /*
     * (non-Javadoc)
     * 
     * @see java.security.PrivilegedAction#run()
     */
    public T run() {
        File f = new File(".");
        File[] files = f.listFiles();
        System.out.println("Filecount: "+files.length);
        return (T) new Integer(files.length);
    }
    
}
