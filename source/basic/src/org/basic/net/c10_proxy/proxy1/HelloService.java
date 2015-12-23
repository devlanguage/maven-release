package org.basic.net.c10_proxy.proxy1;
import java.util.Date;
public interface HelloService{
  public String echo(String msg)throws RemoteException;
  public Date getTime()throws RemoteException;
} 


