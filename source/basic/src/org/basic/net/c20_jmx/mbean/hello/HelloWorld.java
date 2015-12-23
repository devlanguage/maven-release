package org.basic.net.c20_jmx.mbean.hello;

import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;

public class HelloWorld extends NotificationBroadcasterSupport implements HelloWorldMBean {

    private String greeting;

    public HelloWorld() {
        this.addNotificationListener(new HelloNotificationListener(), null, null);
        this.greeting = "Hello World! I am a Standard MBean";
    }

    public HelloWorld(String greeting) {

        this.greeting = greeting;

    }

    public void setGreeting(String greeting) {

        // 更改属性这里增加了notification
        // 只要更改属性就可以看到。

        this.greeting = greeting;

        Notification notification = new Notification("Attribute GreetingChange", this, -1, System.currentTimeMillis(), greeting);
        sendNotification(notification);
    }

    public String getGreeting() {

        return greeting;

    }

    public void printGreeting() {

        System.out.println("greeting=" + greeting);
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
