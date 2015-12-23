/****************************************************************************
 *                 TELLABS PROPRIETARY AND CONFIDENTIAL
 *              UNPUBLISHED WORK COPYRIGHT 2009 TELLABS
 *                          ALL RIGHTS RESERVED
 *      NO PART OF THIS DOCUMENT MAY BE USED OR REPRODUCED WITHOUT
 *                   THE WRITTEN PERMISSION OF TELLABS.
 *  Last modifed on 9:53:47 AM Feb 21, 2014
 *
 *****************************************************************************
 */
package org.basic.net.c20_jmx;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.rmi.registry.LocateRegistry;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServer;
import javax.management.MBeanServerDelegate;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;

import org.basic.net.c20_jmx.Descriptors.QueueSampler;
import org.basic.net.c20_jmx.Descriptors.QueueSamplerMXBean;
import org.basic.net.c20_jmx.mbean.hello.HelloDynamicMBean;
import org.basic.net.c20_jmx.mbean.hello.HelloWordOpenMBean;
import org.basic.net.c20_jmx.mbean.hello.HelloWorld;
import org.basic.net.c20_jmx.mbean.hello.HelloWorldMBean;
import org.basic.net.c20_jmx.mbean.hello.HelloWorldModelMBean;
import org.basic.net.c20_jmx.mbean.user.UserManager;

//import com.sun.jdmk.comm.HtmlAdaptorServer;

/**
 * Created on Feb 21, 2014, 9:53:47 AM
 * 
 * <pre>
 * java -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.port=18000 -Djava.rmi.server.hostname=localhost -jar
 * demo/jfc/Java2D/Java2Demo.jar
 * 
 * 
 * #指定hostname 一般情况需要重新指定hostname,否则连接不成功
 * -Djava.rmi.server.hostname=192.168.0.147
 * #指定hostname 指定端口默认：1099
 * -Dcom.sun.management.jmxremote.port=8899
 * #禁止ssl连接
 * com.sun.management.jmxremote.ssl=false
 * 
 * #开启用户认证
 * com.sun.management.jmxremote.authenticate=true
 * 
 * #认证用户名密码
 * -Dcom.sun.management.jmxremote.password.file=/opt/home/lichengwu/jvm/management/jmxremote.password
 * #访问模式
 * -Dcom.sun.management.jmxremote.access.file=/opt/home/lichengwu/jvm/management/jmxremote.access
 * 注意：  jmxremote.password和jmxremote.access文件只允许启动用户名对该文件拥有读写权限 ，我们服务用root启动 所以
 * 否则： Error: Password file read access must be restricted: D:\software\dev\jdk\jdk1.7.0_51_x64\jre\lib\management\jmxremote.password
 * </pre>
 */
public class MBeanTestServer {
    static final MBeanServer startMBeanServerIndependently() throws IOException {
        LocateRegistry.createRegistry(JmxUtil.JMX_RMI_CONNECTOR_PORT); // 在Dos运行一个命令：rmiregistry 18001
        // 创建MBeanServer 新建MBean ObjectName, 在MBeanServer里标识注册的MBean
        // DomainNames:k1=v1,k2=v2..
        MBeanServer mbs = MBeanServerFactory.createMBeanServer("HelloAgent");
        //  Create an RMI connector and start it    
        // JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:18001/jmxtest");
        JMXServiceURL url = new JMXServiceURL(JmxUtil.JMX_RMI_URL);
        JMXConnectorServer jmxRmiConnector = JMXConnectorServerFactory.newJMXConnectorServer(url, null, mbs);
        jmxRmiConnector.start();
        System.out.println("JMX RMI connector (" + JmxUtil.JMX_RMI_CONNECTOR_PORT + ") start .....");
        return mbs;
    }

    static final MBeanServer startMBeanServerWithInPlatformMBeanServer() {
        MBeanServer jvmMgrMBeanServer = ManagementFactory.getPlatformMBeanServer();
        // JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:18001/jmxrmi");
        System.out.println("JMX RMI connector (" + System.getProperty("com.sun.management.jmxremote.port") + ") start.....");
        return jvmMgrMBeanServer;
    }

    void printMBean(MBeanServer mbeanServer) throws AttributeNotFoundException, InstanceNotFoundException, MBeanException,
            ReflectionException {
        /**
         * <pre>
         * Name                    Value                        Type    Display Name    Update Interval Description
         * ImplementationName      JMX                         String  Implementation Name Default The JMX implementation name.
         * ImplementationVendor    Oracle Corporation          String  Implementation Vendor   Default The JMX implementation vendor.
         * ImplementationVersion   1.7.0_51-b13                String  Implementation Version  Default The JMX implementation version.
         * MBeanServerId           cnshygongd1_1392888648650   String  MBean Server ID Default The MBean server agent identity.
         * SpecificationName       Java Management Extensions  String  Specification Name  Default The full name of the implemented JMX specification.
         * SpecificationVendor     Oracle Corporation          String  JMX Specification Vendor    Default The name of the JMX specification vendor.
         * SpecificationVersion    1.4                         String  JMX Specification Version   Default The version of the JMX specification.
         * 
         * </pre>
         */
        String agentId = (String) mbeanServer.getAttribute(MBeanServerDelegate.DELEGATE_NAME, "MBeanServerId");
        // List<MBeanServer> mbeanServers = MBeanServerFactory.findMBeanServer(agentId);
        System.out.println(agentId);
    }

    public static void main(String[] args) throws Exception {
        // System.setProperty("com.sun.management.jmxremote.ssl", "false");
        // System.setProperty("java.rmi.server.hostname", "127.0.0.1");
        // System.setProperty("com.sun.management.jmxremote.port", JmxUtil.JMX_RMI_CONNECTOR_PORT+"");
        // System.setProperty("com.sun.management.jmxremote.authenticate","false");
        // -Dcom.sun.management.jmxremote.ssl=false -Djava.rmi.server.hostname=localhost
        // -Dcom.sun.management.jmxremote.port=18000 -Dcom.sun.management.jmxremote.authenticate=false

        MBeanServer mbeanServer = null;
        // mbs = startMBeanServerIndependently();
        mbeanServer = startMBeanServerWithInPlatformMBeanServer();

//        HtmlAdaptorServer adapter = new HtmlAdaptorServer();
//        ObjectName adapterName = new ObjectName("HelloAgent:name=htmladapter,port=18002");
//        mbeanServer.registerMBean(adapter, adapterName);
//        adapter.setPort(JmxUtil.JMX_HTML_ADAPTER_PORT);
//        adapter.start();

        UserManager userMBean = new UserManager();
        ObjectName userMBeanName = new ObjectName(JmxUtil.MBEAN_DOMAIN_JMXTEST_SIMPLE + ":" + "name=" + userMBean.getMBeanName());
        mbeanServer.registerMBean(userMBean, userMBeanName);

        HelloWorldMBean helloMBean = new HelloWorld();
        ObjectName helloMBeanName = new ObjectName(JmxUtil.MBEAN_DOMAIN_JMXTEST_SIMPLE + ":" + "name=" + helloMBean.getMBeanName());
        mbeanServer.registerMBean(helloMBean, helloMBeanName);

        HelloDynamicMBean dynMBean = new HelloDynamicMBean();
        ObjectName dynMBeanName = new ObjectName(JmxUtil.MBEAN_DOMAIN_JMXTEST_SIMPLE + ":" + "name=" + dynMBean.getMBeanName());
        mbeanServer.registerMBean(dynMBean, dynMBeanName);

        HelloWorldModelMBean modelMBean = new HelloWorldModelMBean();
        ObjectName modelMBeanName = new ObjectName(JmxUtil.MBEAN_DOMAIN_JMXTEST_SIMPLE + ":" + "name=" + modelMBean.getMBeanName());
        mbeanServer.registerMBean(modelMBean, modelMBeanName);

        HelloWordOpenMBean openMBean = new HelloWordOpenMBean();
        ObjectName openMBeanName = new ObjectName(JmxUtil.MBEAN_DOMAIN_JMXTEST_SIMPLE + ":" + "name=" + openMBean.getMBeanName());
        mbeanServer.registerMBean(openMBean, openMBeanName);

        // Construct the ObjectName for the MBean we will register
        ObjectName mbeanDescriptor = new ObjectName("com.example.mxbeans:type=QueueSampler");
        // Create the Queue Sampler MXBean
        Queue<String> queue = new ArrayBlockingQueue<String>(10);
        queue.add("Request-1");
        queue.add("Request-2");
        queue.add("Request-3");
        QueueSamplerMXBean mxbean = new QueueSampler(queue);

        // Register the Queue Sampler MXBean
        mbeanServer.registerMBean(mxbean, mbeanDescriptor);

    }
}
