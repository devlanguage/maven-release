package org.basic.net.c20_jmx.mbean.simple;
/*
 * @(#)file      SimpleStandardListener.java
 * @(#)author    Sun Microsystems, Inc.
 * @(#)version   1.2
 * @(#)lastedit  04/02/02
 *
 * Copyright 2004 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

// JMX imports
//
import javax.management.Notification;
import javax.management.NotificationListener;
import javax.management.AttributeChangeNotification;

public class SimpleStandardListener implements NotificationListener {

    /*
     * ------------------------------------------
     *  CONSTRUCTORS
     * ------------------------------------------
     */

    public SimpleStandardListener() {
    }

    /*
     * ------------------------------------------
     *  PUBLIC METHODS
     * ------------------------------------------
     */

    public void handleNotification(Notification n, Object h) {
        // Process the different types of notifications fired
        // by the Simple Standard MBean.
        //
        String type = n.getType();
        echo("\n\t>> SimpleStandardListener handles received notification:");
        echo("\t>> -----------------------------------------------------");
        try {
            if (type.equals(AttributeChangeNotification.ATTRIBUTE_CHANGE)) {
                echo("\t>> \"" +
                     ((AttributeChangeNotification) n).getAttributeName() +
                     "\" has changed");
                echo("\t>> Old value = " +
                     ((AttributeChangeNotification) n).getOldValue());
                echo("\t>> New value = " +
                     ((AttributeChangeNotification) n).getNewValue());
            } else {
                echo("\t>> Unknown event type (?)\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static void echo(String msg) {
        System.out.println(msg);
    }
}
