/****************************************************************************
 *                 TELLABS PROPRIETARY AND CONFIDENTIAL
 *              UNPUBLISHED WORK COPYRIGHT 2009 TELLABS
 *                          ALL RIGHTS RESERVED
 *      NO PART OF THIS DOCUMENT MAY BE USED OR REPRODUCED WITHOUT
 *                   THE WRITTEN PERMISSION OF TELLABS.
 *  Last modifed on 1:00:24 PM Apr 4, 2014
 *
 *****************************************************************************
 */
package org.basic.concurrent.unsafe;

/**
 * Created on Apr 4, 2014, 1:00:24 PM
 */
public class UnsafeClass {
    public String hello(String guest) {
        System.out.println("hi," + guest + "\r\n\t hello called");
        return "byte";
    }

}
