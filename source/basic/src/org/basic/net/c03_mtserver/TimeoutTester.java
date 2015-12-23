package org.basic.net.c03_mtserver;
import java.io.*;
import java.net.*;

public class TimeoutTester{
  public static void main(String args[])throws IOException{
    ServerSocket serverSocket=new ServerSocket(8000);
    serverSocket.setSoTimeout(6000); //等待客户连接的时间不超过6秒
    Socket socket=serverSocket.accept(); 
    socket.close();
    System.out.println("服务器关闭");
  }
}


