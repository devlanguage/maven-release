/**
 * Copyright reserved by Tellabs Communication Corp. LTD. The file
 * org.basic.jmx.examples.chat.BaseMBeanAbstract.java is created on 2008-1-7
 */
package org.basic.net.c20_jmx.jdmk.chat.user;

import javax.management.InstanceNotFoundException;
import javax.management.IntrospectionException;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.ReflectionException;

import org.basic.net.c20_jmx.MBeanUtil;

/**
 * 
 */
public abstract class BaseMBeanAbstract implements BaseMBean {

    protected ObjectName mBeanName;
    protected MBeanServer mBeanServer;

    protected BaseMBeanAbstract(MBeanServer mBeanServer, ObjectName mBeanName) {

        this.setMBeanName(mBeanName);
        this.setMBeanServer(mBeanServer);

    }

    public String printMBean() {

        String mBeanInfo = "";
        try {
            mBeanInfo = MBeanUtil
                    .generateMBeanInfo(this.getMBeanServer().getMBeanInfo(this.mBeanName));
        } catch (InstanceNotFoundException e) {
        } catch (IntrospectionException e) {
        } catch (ReflectionException e) {
        }
        System.out.println(mBeanInfo);
        return mBeanInfo;
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
