/****************************************************************************
 *                 TELLABS PROPRIETARY AND CONFIDENTIAL
 *              UNPUBLISHED WORK COPYRIGHT 2009 TELLABS
 *                          ALL RIGHTS RESERVED
 *      NO PART OF THIS DOCUMENT MAY BE USED OR REPRODUCED WITHOUT
 *                   THE WRITTEN PERMISSION OF TELLABS.
 *  Last modifed on 4:44:14 PM May 6, 2014
 *
 *****************************************************************************
 */
package org.basic.io.nio;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Created on May 6, 2014, 4:44:14 PM
 */
public class Messages {
    private static final String BUNDLE_NAME = "org.basic.io.nio.messages"; //$NON-NLS-1$

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

    private Messages() {
    }

    public static String getString(String key) {
        try {
            return RESOURCE_BUNDLE.getString(key);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }
}
