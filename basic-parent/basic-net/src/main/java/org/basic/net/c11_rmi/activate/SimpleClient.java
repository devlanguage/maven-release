package org.basic.net.c11_rmi.activate;
import java.rmi.*;
import javax.naming.*;

public class SimpleClient{
  public static void showRemoteObjects(Context namingContext)throws Exception{
    NamingEnumeration<NameClassPair> e=namingContext.list("rmi:");
    while(e.hasMore()) 
     System.out.println(e.next().getName());
  }

  public static void main( String args[] ){
    String url="rmi://localhost/";
    try{
      Context namingContext=new InitialContext();
      HelloService service1=(HelloService)namingContext.lookup(url+"HelloService1");
      HelloService service2=(HelloService)namingContext.lookup(url+"HelloService2"); 
      
      Class stubClass=service1.getClass();
      System.out.println("service1是"+stubClass.getName()+"的实例"); 
      Class[] interfaces=stubClass.getInterfaces();
      for(int i=0;i<interfaces.length;i++)  
        System.out.println("存根类实现了"+interfaces[i].getName()); 

      System.out.println(service1.echo("hello")); 
      System.out.println(service1.getTime()); 

      System.out.println(service2.echo("hello")); 
      System.out.println(service2.getTime()); 

      showRemoteObjects(namingContext);
    }catch( Exception e){
       e.printStackTrace();
    }
  }
}


