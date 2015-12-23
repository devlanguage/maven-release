package org.basic.net.c20_jmx.jdmk.current.Security.simple.client;
import javax.management.Notification;
import javax.management.NotificationListener;

public class ClientListener implements NotificationListener {
    public void handleNotification(Notification notification, Object handback) {
	System.out.println("\nReceived notification: " + notification);
    }
}
