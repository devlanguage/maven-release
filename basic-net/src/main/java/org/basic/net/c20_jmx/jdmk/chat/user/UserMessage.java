package org.basic.net.c20_jmx.jdmk.chat.user;

import java.util.Set;

import javax.management.ListenerNotFoundException;
import javax.management.MBeanNotificationInfo;
import javax.management.MBeanServer;
import javax.management.Notification;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;
import javax.management.ObjectInstance;
import javax.management.ObjectName;

import org.basic.net.c20_jmx.jdmk.chat.notification.QueryUserMessageNotificationListener;

/**
 * 
 */
public class UserMessage extends BaseBroadCastMBeanAbstract implements UserMessageMBean /* ,NotificationBroadcaster */{

    private String content;
    private QueryUserMessageNotificationListener messageListener;

    public UserMessage(MBeanServer mBeanServer, ObjectName userMessageMBeanName) {

        super(mBeanServer, userMessageMBeanName);
        messageListener = new QueryUserMessageNotificationListener();
        this.addNotificationListener(messageListener, null, null);
    }

    /**
     * @return get method for the field content
     */
    public String getContent() {

        return this.content;
    }

    /**
     * @param content
     *            the content to set
     */
    public void setContent(String content) {

        this.content = content;
    }

    public Set<ObjectInstance> queryMBean(String expression) {

        Notification notification = new Notification("queryMBean", "UserMessage", 2,
                "[QueryExpression=" + expression + "]");
        this.sendNotification(notification);

        System.out.println(expression);
        return null;
    }

    public void addNotificationListener(NotificationListener listener, NotificationFilter filter,
            Object handback) throws IllegalArgumentException {

        super.addNotificationListener(listener, filter, handback);
    }

    public MBeanNotificationInfo[] getNotificationInfo() {

        return super.getNotificationInfo();
    }

    public void removeNotificationListener(NotificationListener listener)
            throws ListenerNotFoundException {

        super.removeNotificationListener(listener);
    }

    public String sayHello(String hello) {

        System.err.println("Client:" + hello);
        return "Hello, I am server";
    }

}
