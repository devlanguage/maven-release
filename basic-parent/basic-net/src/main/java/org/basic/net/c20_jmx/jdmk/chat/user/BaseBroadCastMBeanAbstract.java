package org.basic.net.c20_jmx.jdmk.chat.user;

import javax.management.MBeanInfo;
import javax.management.MBeanServer;
import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;
import javax.management.ObjectName;

import org.basic.net.c20_jmx.MBeanUtil;

public abstract class BaseBroadCastMBeanAbstract extends NotificationBroadcasterSupport implements
        BaseMBean {

    protected ObjectName mBeanName;
    protected MBeanServer mBeanServer;

    protected BaseBroadCastMBeanAbstract(MBeanServer mBeanServer, ObjectName mBeanName) {

        this.setMBeanName(mBeanName);
        this.setMBeanServer(mBeanServer);

    }

    public String printMBean() {

        StringBuffer sb = new StringBuffer();
        try {
            MBeanInfo MBeanInfo = this.getMBeanServer().getMBeanInfo(this.getMBeanName());

            sb.append("\n\nMBean Info: ");
            sb.append("\nClass: " + MBeanInfo.getClassName());
            sb.append("\nDescription: " + MBeanInfo.getDescription());
            sb.append("\nDescriptor: " + MBeanInfo.getDescriptor());

            sb.append("\n\nMBean Constructor Info: ");
            sb.append(MBeanUtil.generateMBeanConstructors(MBeanInfo.getConstructors()));

            sb.append("\n\nMBean Attribute Info: ");
            sb.append(MBeanUtil.generateMBeanAttributes(MBeanInfo.getAttributes()));

            sb.append("\n\nMBean Operation Info: ");
            sb.append(MBeanUtil.generateMBeanOperations(MBeanInfo.getOperations()));

            sb.append("\n\nMBean Notification Info: ");
            sb.append(MBeanUtil.generateMBeanNotifications(MBeanInfo.getNotifications()));

        } catch (Exception e) {
            e.printStackTrace();
        }

        Notification notification = new Notification("generateMBean", "UserMessage", 1, sb.toString());
        this.sendNotification(notification);

        System.out.println(sb);
        return sb.toString();
    }

    /**
     * @return get method for the field mBeanName
     */
    public ObjectName getMBeanName() {

        return this.mBeanName;
    }

    /**
     * @param beanName
     *            the mBeanName to set
     */
    public void setMBeanName(ObjectName beanName) {

        mBeanName = beanName;
    }

    /**
     * @return get method for the field mBeanServer
     */
    public MBeanServer getMBeanServer() {

        return this.mBeanServer;
    }

    /**
     * @param beanServer
     *            the mBeanServer to set
     */
    public void setMBeanServer(MBeanServer beanServer) {

        mBeanServer = beanServer;
    }

}
