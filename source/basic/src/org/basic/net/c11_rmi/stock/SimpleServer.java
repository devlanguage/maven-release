package org.basic.net.c11_rmi.stock;

import java.rmi.*;
import javax.naming.*;

public class SimpleServer{
  public static void main( String args[] ){
    try{
       StockQuoteRegistryImpl registry=new StockQuoteRegistryImpl();
 
       Context namingContext=new InitialContext();
       namingContext.rebind( "rmi:StockQuoteRegistry", registry);
       System.out.println( "服务器注册了一个StockQuoteRegistry对象" );

       new Thread(registry).start();
    }catch(Exception e){
       e.printStackTrace();
    } 
  }
}





