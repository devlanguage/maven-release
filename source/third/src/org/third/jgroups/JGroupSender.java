package org.third.jgroups;

import org.jgroups.JChannel;
import org.jgroups.Message;

/**
 * Created on Mar 3, 2014, 2:30:08 PM
 */
public class JGroupSender {
    JChannel channel;
    // 得到本机电脑的 用户名字
    String user_name = System.getProperty("user.name", "n/a");

    private void start() throws Exception {
        /**
         * 参数里指定Channel使用的协议栈，如果是空的，则使用默认的协议栈， 位于JGroups包里的udp.xml。参数可以是一个以冒号分隔的字符串， 或是一个XML文件，在XML文件里定义协议栈。
         */
        // 创建一个通道
        channel = new JChannel(JGroupSender.class.getResource("udp.xml"));
        // 加入一个群
        channel.connect("ChatCluster");
        // 发送事件
        sendEvent();
        // 关闭通道
        channel.close();
    }

    /**
     * 主要发送事件
     */
    private void sendEvent() {
        try {
            String str = "chenhaibin";// 发送的字符串
            // 这里的Message的第一个参数是发送端地址
            // 第二个是接收端地址
            // 第三个是发送的字符串
            // 具体参见jgroup send API
            // 发送
            for (int i = 0; i < 10000; ++i) {
                Message msg = new Message(null, null, str+i);
                channel.send(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        // 开始发送事件
        new JGroupSender().start();
    }
}
