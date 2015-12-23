package org.basic.net.c03_mtserver;
import java.io.*;
import java.net.*;

public class RandomPort{
  public static void main(String args[])throws IOException{
    ServerSocket serverSocket=new ServerSocket(0);
    System.out.println("监听的端口为:"+serverSocket.getLocalPort());
  }
}


