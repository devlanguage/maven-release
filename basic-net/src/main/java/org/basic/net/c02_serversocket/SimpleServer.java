package org.basic.net.c02_serversocket;
import java.io.*;
import java.net.*;
public class SimpleServer {
  public static void main(String args[])throws Exception {
    ServerSocket serverSocket = new ServerSocket(8000,2);  //连接请求队列的长度为2
    Thread.sleep(360000);   //睡眠6分钟
  }
}


