package org.basic.net.c20_jmx.mbean.hello;

import javax.management.Notification;
import javax.management.NotificationListener;

public class HelloNotificationListener implements NotificationListener {

    public void handleNotification(Notification notif, Object handback) {

        // 可以在这里顺便看下notification 结构体的参数。

        System.out.println("Receiving notification...");
        System.out.println(notif.getType() + "\t" + notif.getSource() + "\t" + notif.getMessage());
    }

}
