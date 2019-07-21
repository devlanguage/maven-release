package org.basic.net.c20_jmx.jdmk.chat.notification;

import javax.management.Notification;
import javax.management.NotificationListener;

/**
 * 
 */
public class PrintUserMessageNotificationListener implements NotificationListener {

    /*
     * (non-Javadoc)
     * 
     * @see javax.management.NotificationListener#handleNotification(javax.management.Notification,
     *      java.lang.Object)
     */
    public void handleNotification(Notification notification, Object handback) {

        System.err.println("Received the Notification: " + this.getClass().getName() + ":"
                + notification);
    }

}
