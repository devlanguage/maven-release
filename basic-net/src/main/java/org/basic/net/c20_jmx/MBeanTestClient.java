package org.basic.net.c20_jmx;

import static org.basic.net.c20_jmx.JmxUtil.echo;

import java.util.Set;

import javax.management.Attribute;
import javax.management.MBeanServerConnection;
import javax.management.Notification;
import javax.management.NotificationListener;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.basic.net.c20_jmx.mbean.hello.HelloWorldMBean;
import org.basic.net.c20_jmx.mbean.user.UserManagerMBean;

/**
 * Created on Feb 21, 2014, 9:53:47 AM
 */
class MBeanListener implements NotificationListener {
    @Override
    public void handleNotification(Notification notification, Object handback) {
        echo("\nReceived notification: " + notification);
    }
}

public class MBeanTestClient {
    public static void main(String[] args) throws Exception {
        JMXServiceURL url = new JMXServiceURL(JmxUtil.JMX_RMI_URL);
        JMXConnector jmxc = JMXConnectorFactory.connect(url, null);
        MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();
        JmxUtil.printAllMBeans(mbsc);

        System.out.println("----------UserManager Mbean Test----------");
        ObjectName userMBeanName = new ObjectName(JmxUtil.MBEAN_DOMAIN_JMXTEST_SIMPLE + ":" + "name=" + UserManagerMBean.MBEAN_NAME);
        // Getter
        System.out.println("getAttribute(UserName) = " + mbsc.getAttribute(userMBeanName, "UserName"));// 取值
        // 对name属性的操作（属性名的第一个字母要大写）
        mbsc.setAttribute(userMBeanName, new Attribute("UserName", "PANDA"));// 设值

        // Getter
        System.out.println("getAttribute(UserName) = " + mbsc.getAttribute(userMBeanName, "UserName"));// 取值
        // Call Method
        Object response = mbsc.invoke(userMBeanName, "sayHi", new Object[] { "Zhangsan" }, new String[] { String.class.getName() });
        System.out.println("Call method sayHi(Zhangsan)= " + response);

        // 得到proxy代理后直接调用的方式

        System.out.println("----------HelloWorld Mbean Test----------");
        ObjectName helloWorldMBean = new ObjectName(JmxUtil.MBEAN_DOMAIN_JMXTEST_SIMPLE + ":" + "name=" + HelloWorldMBean.MBEAN_NAME);
        
        echo("\nAdd notification listener...");
        mbsc.addNotificationListener(helloWorldMBean, new MBeanListener(), null, null);

        
        response = mbsc.invoke(helloWorldMBean, "printGreeting", new Object[] {}, new String[] {});
        mbsc.setAttribute(helloWorldMBean, new Attribute("Greeting", "asdf"));

   
        Set<ObjectInstance> mbeanSet = mbsc.queryMBeans(new ObjectName("java*:name=*"), null);
        System.out.println(mbeanSet);
        Set<ObjectName> mbeanNameSet = mbsc.queryNames(new ObjectName("domain*:id=*"), null);
        System.out.println(mbeanNameSet);
        Thread.sleep(10000);
    }
}
