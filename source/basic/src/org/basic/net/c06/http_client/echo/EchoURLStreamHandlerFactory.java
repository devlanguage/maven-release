package org.basic.net.c06.http_client.echo;
import java.net.*;
import java.io.*;
public class EchoURLStreamHandlerFactory implements URLStreamHandlerFactory{
  public URLStreamHandler createURLStreamHandler(String protocol){
    if(protocol.equals("echo"))
      return new EchoURLStreamHandler();
    else
      return null;
  }
}


