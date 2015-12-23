package org.basic.net.c11_rmi.stock;
import java.rmi.*;
/** 客户端的远程对象接口 */
public interface StockQuote extends Remote{
    public void quote(String stockSymbol, double price)throws RemoteException;
}


