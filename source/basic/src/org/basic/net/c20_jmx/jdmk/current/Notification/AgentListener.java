package org.basic.net.c20_jmx.jdmk.current.Notification;
/*
 * @(#)file      AgentListener.java
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
import javax.management.MBeanServerNotification;

public class AgentListener implements NotificationListener {

    /*
     * ------------------------------------------
     *  CONSTRUCTORS
     * ------------------------------------------
     */

    public AgentListener() {
    }

    /*
     * ------------------------------------------
     *  PUBLIC METHODS
     * ------------------------------------------
     */

    public void handleNotification(Notification n, Object h) {
        // Process the different types of notifications fired
        // by the MBean server delegate.
        //
        String type = n.getType();
        echo("\n\t>> AgentListener handles received notification:");
        echo("\t>> --------------------------------------------");
        try {
            if (type.equals(
                MBeanServerNotification.REGISTRATION_NOTIFICATION)) {
                echo("\t>> \"" +
                     ((MBeanServerNotification) n).getMBeanName() +
                     "\" has been registered in the server");
            } else if (type.equals(
                       MBeanServerNotification.UNREGISTRATION_NOTIFICATION)) {
                echo("\t>> \"" +
                     ((MBeanServerNotification) n).getMBeanName() +
                     "\" has been unregistered from the server");
            } else {
                echo("\t>> Unknown event type (?)");
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
