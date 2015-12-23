package org.basic.net.c10_proxy.proxy;
public class Client1{
  public static void main(String args[]){
    HelloService helloService=new HelloServiceImpl();
    HelloService helloServiceProxy=new HelloServiceProxy(helloService);
    System.out.println(helloServiceProxy.echo("hello"));
  }
}


