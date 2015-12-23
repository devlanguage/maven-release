package org.basic.net.c20_jmx.jdmk.legacy.Notification;
/*
 * @(#)file      NotificationEmitter.java
 * @(#)author    Sun Microsystems, Inc.
 * @(#)version   1.6
 * @(#)lastedit  04/01/19
 *
 * Copyright 2004 Sun Microsystems, Inc.  All rights reserved. Use is subject to license terms.
 */

import javax.management.MBeanNotificationInfo;
import javax.management.NotificationBroadcasterSupport;
import javax.management.Notification;

public class NotificationEmitter extends NotificationBroadcasterSupport implements NotificationEmitterMBean {
	public NotificationEmitter() {
		super();
	}

    /**    
     * Returns a NotificationInfo object containing the name of the Java class of the notification
     * and the notification types sent by this notification broadcaster.  
     */
    public MBeanNotificationInfo[] getNotificationInfo() {
        
        MBeanNotificationInfo[] ntfInfoArray  = new MBeanNotificationInfo[1];
        
        String[] ntfTypes = new String[1];
        ntfTypes[0] = myType;
        
        ntfInfoArray[0] = new MBeanNotificationInfo(ntfTypes,
                                                    "javax.management.Notification", 
                                                    "Notifications sent by the NotificationEmitter");
        return ntfInfoArray;
    }  
	
    /**
	 * Send a Notification object with the specified times.
	 * The sequence number will be from zero to times-1.
	 *
	 * @param nb The number of notifications to send
	 */
	public void sendNotifications(Integer nb) {
		for (int i=1; i<=nb.intValue(); i++) {
			sendNotification(new Notification(myType, this, i));
		}
	}
    
    private String myType = "notification.my_notification";
}
