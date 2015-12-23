package org.basic.net.c13_jdbcpool;
public class StoreException extends Exception{
  public StoreException() {
    this("StoreException");
  }
  public StoreException(String msg) {
    super(msg);
  }
}


