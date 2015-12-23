/****************************************************************************
 *                 TELLABS PROPRIETARY AND CONFIDENTIAL
 *              UNPUBLISHED WORK COPYRIGHT 2009 TELLABS
 *                          ALL RIGHTS RESERVED
 *      NO PART OF THIS DOCUMENT MAY BE USED OR REPRODUCED WITHOUT
 *                   THE WRITTEN PERMISSION OF TELLABS.
 *  Last modifed on 5:01:20 PM Feb 20, 2014
 *
 *****************************************************************************
 */
package org.basic.net.c20_jmx.mbean.user;

import javax.management.Attribute;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.basic.net.c20_jmx.JmxUtil;

/**
 * Created on Feb 20, 2014, 5:01:20 PM
 */
public class UserClient {
    public static void main(String[] args) throws Exception {
        JMXServiceURL url = new JMXServiceURL(JmxUtil.JMX_RMI_URL);
        JMXConnector jmxc = JMXConnectorFactory.connect(url, null);
        MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();
        JmxUtil.printAllMBeans(mbsc);

        ObjectName mbeanName = new ObjectName(JmxUtil.MBEAN_DOMAIN_JMXTEST_SIMPLE + ":" + "name=" + UserManagerMBean.MBEAN_NAME);
        System.out.println("----------UserManager Mbean Test----------");

        // Getter
        System.out.println("getAttribute(UserName) = " + mbsc.getAttribute(mbeanName, "UserName"));// 取值
        // 对name属性的操作（属性名的第一个字母要大写）
        mbsc.setAttribute(mbeanName, new Attribute("UserName", "PANDA"));// 设值

        // Getter
        System.out.println("getAttribute(UserName) = " + mbsc.getAttribute(mbeanName, "UserName"));// 取值
        // Call Method
        Object response = mbsc.invoke(mbeanName, "sayHi", new Object[] { "Zhangsan" }, new String[] { String.class.getName() });
        System.out.println("Call method sayHi(Zhangsan)= " + response);

        // 得到proxy代理后直接调用的方式
    }
}
