/**
 * Copyright reserved by Tellabs Communication Corp. LTD. The file
 * org.basic.jmx.examples.chat.notification.QueryUserMessageNotificationListener.java is created on
 * 2008-1-7
 */
package org.basic.net.c20_jmx.jdmk.chat.notification;

import javax.management.Notification;
import javax.management.NotificationListener;

/**
 * 
 */
public class QueryUserMessageNotificationListener implements NotificationListener {

    public void handleNotification(Notification notification, Object handback) {

        System.err.println("Received the Notification: " + this.getClass().getName() + ":"
                + notification);
    }

}
