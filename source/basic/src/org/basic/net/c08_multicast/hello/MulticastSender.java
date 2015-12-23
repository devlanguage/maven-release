package org.basic.net.c08_multicast.hello;

import java.io.IOException;
import java.net.InetAddress;

import javax.swing.JOptionPane;

public class MulticastSender {

    // 224.0.0.0～224.0.0.255为预留的组播地址（永久组地址），地址224.0.0.0保留不做分配，其它地址供路由协议使用；
    // 224.0.1.0～238.255.255.255为用户可用的组播地址（临时组地址），全网范围内有效；
    // 239.0.0.0～239.255.255.255为本地管理组播地址，仅在特定的本地范围内有效。

    public static String GROUP_IP = "224.0.1.0";
    public static int LISTEN_PORT = 12345;

    public static void main(String[] args) throws IOException, InterruptedException {

        System.out.println("client start.......");
        java.net.MulticastSocket multicastSocket = new java.net.MulticastSocket();// 其实这里使用DatagramSocket发送packet就行
        InetAddress group = InetAddress.getByName(GROUP_IP);
        int i = 1000000;
        while (true) {
            String msg = "MulticastSender发送的数据_" + i++;
            byte[] data = msg.getBytes();
            java.net.DatagramPacket packet = new java.net.DatagramPacket(data, data.length, group, LISTEN_PORT);
            try {
                multicastSocket.send(packet);
                System.out.println("MulticastSender send: " + msg);
                Thread.sleep(1);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "multicast error");
                System.exit(1);
            }
        }
    }

}
