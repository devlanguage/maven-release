/****************************************************************************
 *                 TELLABS PROPRIETARY AND CONFIDENTIAL
 *              UNPUBLISHED WORK COPYRIGHT 2009 TELLABS
 *                          ALL RIGHTS RESERVED
 *      NO PART OF THIS DOCUMENT MAY BE USED OR REPRODUCED WITHOUT
 *                   THE WRITTEN PERMISSION OF TELLABS.
 *  Last modifed on 5:19:59 PM Oct 31, 2014
 *
 *****************************************************************************
 */
package org.basic.grammar.referecne;

/**
 * Created on Oct 31, 2014, 5:19:59 PM
 */
public class JavaRefObject {
    String value;

    JavaRefObject(String _value) {
        this.value = _value;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return super.toString() + "@value=" + value;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#finalize()
     */
    @Override
    protected void finalize() throws Throwable {
        System.out.println(this + " finalize()");
        super.finalize();
    }
}
