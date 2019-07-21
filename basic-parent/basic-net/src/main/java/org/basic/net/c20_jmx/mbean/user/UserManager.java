package org.basic.net.c20_jmx.mbean.user;

import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;

import org.basic.net.c20_jmx.mbean.hello.HelloNotificationListener;

public class UserManager extends NotificationBroadcasterSupport implements UserManagerMBean {

    String userName = "default_user";

    public UserManager() {

        this.addNotificationListener(new HelloNotificationListener(), null, null);
        this.addNotificationListener(new HelloNotificationListener(), null, null);
    }

    public String getUserName() {

        Notification notification = new Notification("UserManager.test", this, -1, System.currentTimeMillis(), userName);

        super.sendNotification(notification);
        return userName;
    }

    public void setUserName(String user) {

        System.err.println("new user: " + user);
        this.userName = user;
    }

    public String sayHi(String user) {
        System.out.println("hi," + user);

        return "Response: " + user;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.basic.jmx.mbean.BaseMBean#getMBanName()
     */
    @Override
    public String getMBeanName() {
        return MBEAN_NAME;
    }

}
