package org.basic.net.c20_jmx.jdmk.current.ModelMBean;
/*
 * @(#)file      TestBeanAttributeChangeListener.java
 * @(#)author    Sun Microsystems, Inc.
 * @(#)version   1.3
 * @(#)lastedit  04/02/02
 *
 * Copyright 2004 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

// JMX imports
//
import javax.management.AttributeChangeNotification;
import javax.management.Notification;
import javax.management.NotificationListener;

/**
* TestBeanAttributeChangeListener implements the NotificationListener interface.
* Listens for and receives AttributeChangeNotifications from ModelMBean for the
* TestBean managed resource.
*
* Notification information is echoed to the java console.
*/

public class TestBeanAttributeChangeListener implements NotificationListener {
    public void handleNotification(Notification acn, Object handback) {
        echo("\n\tTestBeanAttributeChangeListener received " +
             "Attribute ChangeNotification");
        AttributeChangeNotification myacn = (AttributeChangeNotification) acn;
        echo("\t\tEvent: " + acn.getType());
        echo("\t\tAttribute: " + myacn.getAttributeName());
        echo("\t\tAttribute type: " + myacn.getAttributeType());
        echo("\t\tOld value: " + myacn.getOldValue());
        echo("\t\tNew value: " + myacn.getNewValue());
    }

    private static void echo(String outstr) {
        System.out.println(outstr);
    }
}
