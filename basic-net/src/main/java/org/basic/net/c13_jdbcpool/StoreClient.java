package org.basic.net.c13_jdbcpool;
import javax.naming.*;
import java.rmi.*;

public class StoreClient {
  public static void main( String args[] ){
    System.setProperty("java.security.policy",StoreClient.class.getResource("secure.policy").toString());
    System.setSecurityManager(new RMISecurityManager());
   
    String url="rmi://localhost/";
    try{
      StoreModel model;
      StoreView view;
      StoreController ctrl;
            
      Context namingContext=new InitialContext();
      model=(StoreModel)namingContext.lookup(url+"storeModel");
      view=new StoreViewImpl(model);
      ctrl=new StoreControllerImpl(model,view);
    }catch( Exception e ){
       e.printStackTrace();
    }
  }
}


