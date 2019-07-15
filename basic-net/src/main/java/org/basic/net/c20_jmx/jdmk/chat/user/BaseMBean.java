package org.basic.net.c20_jmx.jdmk.chat.user;

import javax.management.MBeanServer;
import javax.management.ObjectName;

/**
 * 
 */
public interface BaseMBean {

    /**
     * @return get method for the field MBeanName
     */
    public abstract ObjectName getMBeanName();

    /**
     * @param MBeanName
     *            the MBeanName to set
     */
    public abstract void setMBeanName(ObjectName MBeanName);

    /**
     * @return get method for the field MBeanServer
     */
    public abstract MBeanServer getMBeanServer();

    /**
     * @param MBeanServer
     *            the MBeanServer to set
     */
    public abstract void setMBeanServer(MBeanServer MBeanServer);

    /**
     * Print all the detail of MBean.
     * 
     */
    public String printMBean();
}
