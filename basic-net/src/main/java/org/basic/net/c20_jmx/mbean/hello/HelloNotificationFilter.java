package org.basic.net.c20_jmx.mbean.hello;

import javax.management.AttributeChangeNotification;
import javax.management.Notification;
import javax.management.NotificationFilter;

@SuppressWarnings("serial")
public class HelloNotificationFilter implements NotificationFilter {

    public boolean isNotificationEnabled(Notification notification) {
        return notification instanceof AttributeChangeNotification;
    }
}
