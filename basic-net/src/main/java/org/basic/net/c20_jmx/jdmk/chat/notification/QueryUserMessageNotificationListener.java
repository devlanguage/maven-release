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
