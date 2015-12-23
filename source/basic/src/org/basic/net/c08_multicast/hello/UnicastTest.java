package org.basic.net.c08_multicast.hello;

import java.io.IOException;
import java.net.InetAddress;

import javax.swing.JOptionPane;

public class UnicastTest {
    public static String GROUP_IP = "224.0.1.0";
    public static int LISTEN_PORT = 12345;

    public static void main(String[] args) throws IOException, InterruptedException {

        System.out.println("client start.......");
        java.net.MulticastSocket multicastSocket = new java.net.MulticastSocket();// 其实这里使用DatagramSocket发送packet就行
        InetAddress multicastAddress = InetAddress.getByName(GROUP_IP);
        int i = 1000000;
        while (true) {
            String msg = "MulticastSender发送的数据_" + i++;
            byte[] data = msg.getBytes();
            java.net.DatagramPacket packet = new java.net.DatagramPacket(data, data.length, multicastAddress, LISTEN_PORT);
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
