package org.basic.net.c11_rmi.sync;

import java.rmi.*;
import javax.naming.*;

public class SimpleServer{
  public static void main( String args[] ){
    try{
       Stack stack = new StackImpl("a stack");

       Context namingContext=new InitialContext();
       namingContext.rebind( "rmi:MyStack", stack );
        
       System.out.println( "服务器注册了一个Stack对象" );
    }catch(Exception e){
       e.printStackTrace();
    } 
  }
}





