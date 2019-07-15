package org.basic.net.c01_socket.simple;

import static org.basic.net.c01_socket.simple.ChatServer.SERVER_PORT;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.Date;

public class ChatClient {

  public final static String printSocket(Socket s) throws SocketException {
    StringBuilder sb = new StringBuilder();
    sb.append("keepAlive=").append(s.getKeepAlive());
    sb.append(",localPort=").append(s.getLocalPort());
    sb.append(",port=").append(s.getPort());
    sb.append(",OOBInline=").append(s.getOOBInline());
    sb.append(",receiveBuffer=").append(s.getReceiveBufferSize());
    sb.append(",sendBuffer=").append(s.getSendBufferSize());
    sb.append(",reuseAddress=").append(s.getReuseAddress());
    sb.append(",soLinger=").append(s.getSoLinger());
    sb.append(",soTimeOut=").append(s.getSoTimeout());
    sb.append(",trafficClass=").append(s.getTrafficClass());
    sb.append(",localSocketAddresse=").append(s.getLocalSocketAddress());
    sb.append(",remoteSocketAddress=").append(s.getRemoteSocketAddress());
    return sb.toString();

  }

  public static void main(String[] args) throws IOException {

    // InetAddress addr = InetAddress.getByName("127.0.0.1");
    InetAddress addr = InetAddress.getByName("svarog3");
    System.out.println(new Date());
    System.out.println("地址=" + addr);
    Socket socket = new Socket(addr, SERVER_PORT);
    // socket.setKeepAlive(true);
    try {
      socket.setSoTimeout(5);
      System.out.println("socket=" + printSocket(socket));
      BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

      PrintWriter messageSender = new PrintWriter(socket.getOutputStream(), true);
      BufferedReader socketInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      while (true) {
        // Read from console and send message to server
        System.out.print("Read from console and send message to server: ");
        String input = console.readLine().trim();
        messageSender.println(input);
        messageSender.flush();

        // waiting for responses from server
        String str = socketInput.readLine();
        System.out.println("对方说：" + str);
      }

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      System.out.println("关闭");
      socket.close();
    }
  }
}
