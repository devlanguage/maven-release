package org.basic.net.c11_rmi.activate;

import java.util.Date;
import java.rmi.*;

public interface HelloService extends Remote {
    public String echo(String msg) throws RemoteException;

    public Date getTime() throws RemoteException;
}
