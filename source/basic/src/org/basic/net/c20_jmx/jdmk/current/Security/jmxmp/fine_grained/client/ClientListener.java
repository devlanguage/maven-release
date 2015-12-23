package org.basic.net.c20_jmx.jdmk.current.Security.jmxmp.fine_grained.client;
/*
 * @(#)file      ClientListener.java
 * @(#)author    Sun Microsystems, Inc.
 * @(#)version   1.1
 * @(#)lastedit  04/02/05
 *
 * Copyright 2004 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

import javax.management.Notification;
import javax.management.NotificationListener;

public class ClientListener implements NotificationListener {
    public void handleNotification(Notification notification, Object handback) {
	System.out.println("\nReceived notification: " + notification);
    }
}
