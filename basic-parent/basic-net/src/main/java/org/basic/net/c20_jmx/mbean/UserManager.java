package org.basic.net.c20_jmx.mbean;

import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;

import org.basic.net.c20_jmx.mbean.hello.HelloNotificationListener;

public class UserManager extends NotificationBroadcasterSupport implements UserManagerMBean {

    String user = "sdfadf";

    public UserManager() {

        this.addNotificationListener(new HelloNotificationListener(), null, null);
        this.addNotificationListener(new HelloNotificationListener(), null, null);
    }

    public String getUser() {

        Notification notification = new Notification("UserManager.test", this, -1, System
                .currentTimeMillis(), user);

        sendNotification(notification);
        return user;
    }

    public void setUser(String user) {

        System.err.println("new user: " + user);
        this.user = user;
    }

}
