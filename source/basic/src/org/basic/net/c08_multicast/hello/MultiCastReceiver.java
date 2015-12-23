package org.basic.net.c08_multicast.hello;
import static org.basic.net.c08_multicast.hello.MulticastSender.GROUP_IP;
import static org.basic.net.c08_multicast.hello.MulticastSender.LISTEN_PORT;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import javax.swing.JOptionPane;

// 监听组播，输出获取的信息
public class MultiCastReceiver {


    public static void main(String[] args) throws IOException {

        System.out.println("server start.......");
        MulticastSocket multicastSocket = new MulticastSocket(LISTEN_PORT);
        InetAddress group = InetAddress.getByName(GROUP_IP);
        multicastSocket.joinGroup(group);
        byte[] data = new byte[200]; // 未填满空间会被0填充，如果数据长度超出数组则超出的数据被忽略
        DatagramPacket packet = new DatagramPacket(data, data.length);
        while (true) {
            try {
                multicastSocket.receive(packet);
                System.out.println("MultiCastReceiver Received: "+new String(data) );
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "multicast error");
                System.exit(1);
            }
        }
    }
}
