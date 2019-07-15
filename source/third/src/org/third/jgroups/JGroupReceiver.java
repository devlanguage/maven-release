package org.third.jgroups;

import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;

/**
 * Created on Mar 3, 2014, 2:30:08 PM
 */
public class JGroupReceiver extends ReceiverAdapter {
    JChannel channel;
    public static void main(String[] args) throws Exception {
        // 接收收据端
        new JGroupReceiver().start();
    }

    private void start() throws Exception {
        // 创建一个通道
        channel = new JChannel();
        
        String user_name = System.getProperty("user.name", "n/a");
        String PROTOCOL_STACK_UDP1 = "UDP(bind_addr=172.29.132.235";
        String PROTOCOL_STACK_UDP2 = ";mcast_port=8888";
        String PROTOCOL_STACK_UDP3 = ";mcast_addr=225.1.1.1";
        String PROTOCOL_STACK_UDP4 = ";tos=8;loopback=false;max_bundle_size=64000;"
                + "use_incoming_packet_handler=true;use_outgoing_packet_handler=false;ip_ttl=2;enable_bundling=true):"
                + "PING:MERGE2:FD_SOCK:FD:VERIFY_SUSPECT:" + "pbcast.NAKACK(gc_lag=50;max_xmit_size=50000;use_mcast_xmit=false;"
                + "retransmit_timeout=300,600,1200,2400,4800;discard_delivered_msgs=true):" + "UNICAST:pbcast.STABLE:VIEW_SYNC:"
                + "pbcast.GMS(print_local_addr=false;join_timeout=3000;" + "join_retry_timeout=2000;" + "shun=true;view_bundling=true):"
                + "FC(max_credits=2000000;min_threshold=0.10):FRAG2(frag_size=50000)";
        String PROTOCOL_STACK = PROTOCOL_STACK_UDP1 + PROTOCOL_STACK_UDP2 + PROTOCOL_STACK_UDP3 + PROTOCOL_STACK_UDP4;
//        channel = new JChannel(PROTOCOL_STACK);
        channel = new JChannel(JGroupReceiver.class.getResource("udp.xml"));
        
        // 创建一个接收器
        channel.setReceiver(this);
        // 加入一个群
        channel.connect("ChatCluster");
    }

    // 覆盖父类的方法
    @Override
    public void receive(Message msg) {
        // 具体参见msg的参数
        String receiveData = (String) msg.getObject();
        System.out.println("  发过来的数据是:  " + receiveData);
    }

    @Override
    public void viewAccepted(View new_view) {
        System.out.println("** view: " + new_view);
    }
}
