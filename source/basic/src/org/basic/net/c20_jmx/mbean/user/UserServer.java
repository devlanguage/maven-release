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

import java.rmi.registry.LocateRegistry;
import java.util.List;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;

import org.basic.net.c20_jmx.JmxUtil;

//import com.sun.jdmk.comm.HtmlAdaptorServer;

/**
 * Created on Feb 20, 2014, 5:01:20 PM
 */
public class UserServer {
    public static void main(String[] args) throws Exception {
        LocateRegistry.createRegistry(JmxUtil.JMX_RMI_CONNECTOR_PORT);
        // 创建MBeanServer
        MBeanServer mbs = MBeanServerFactory.createMBeanServer("HelloAgent");
        // 新建MBean ObjectName, 在MBeanServer里标识注册的MBean
        // DomainNames:k1=v1,k2=v2..
        UserManager userMBean = new UserManager();
        ObjectName name = new ObjectName(JmxUtil.MBEAN_DOMAIN_JMXTEST_SIMPLE + ":" + "name=" + UserManagerMBean.MBEAN_NAME);
        mbs.registerMBean(userMBean, name);

        // 在MBeanServer里调用已注册的EchoMBean的print方法
        List<MBeanServer> mbeanServers = MBeanServerFactory.findMBeanServer(null);
        for(MBeanServer mbeanServer: mbeanServers){
            System.out.println(mbeanServer);
        }
        mbs.invoke(name, "sayHi", new Object[] { "haitao.tu" }, new String[] { "java.lang.String" });

//        HtmlAdaptorServer adapter = new HtmlAdaptorServer();
//        ObjectName adapterName = new ObjectName("HelloAgent:name=htmladapter,port=18002");
//        mbs.registerMBean(adapter, adapterName);
//        adapter.setPort(18002);
//        adapter.start();

        //  Create an RMI connector and start it    
        JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:18001/server");
        JMXConnectorServer cs = JMXConnectorServerFactory.newJMXConnectorServer(url, null, mbs);
        cs.start();
        System.out.println("rmi start.....");

        // 在Dos运行一个命令：rmiregistry 18001
        // 运行HelloAgent，然后再在dos下运行命令jconsole
        // 输入service:jmx:rmi:///jndi/rmi://localhost:18001/server
    }
}
