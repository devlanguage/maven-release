package org.basic.net.c13_jdbcpool;
public class StoreApp {
  public static void main(String args[])throws Exception{ 
    StoreModel model=new StoreModelImpl();
    StoreView view=new StoreViewImpl(model);
    StoreController ctrl=new StoreControllerImpl(model,view);
  }
}


