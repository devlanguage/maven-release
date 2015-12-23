package org.basic.net.c11_rmi.stock;
import java.rmi.*;
import javax.naming.*;

public class SimpleClient{

  public static void main( String args[] ){
    String url="rmi://localhost/";
    try{
      Context namingContext=new InitialContext();
      StockQuoteRegistry registry=
          (StockQuoteRegistry           )namingContext.lookup(url+"StockQuoteRegistry");
      
      StockQuote client=new StockQuoteImpl();
      registry.registerClient(client);    
  
    }catch( Exception e){
       e.printStackTrace();
    }
  }
}


