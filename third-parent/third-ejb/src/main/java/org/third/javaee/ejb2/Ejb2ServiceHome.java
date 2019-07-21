package org.third.javaee.ejb2;

import java.rmi.RemoteException;

import javax.ejb.EJBException;
import javax.ejb.EJBMetaData;
import javax.ejb.Handle;
import javax.ejb.HomeHandle;
import javax.ejb.RemoveException;

public class Ejb2ServiceHome implements javax.ejb.EJBHome, javax.ejb.EJBLocalHome {

    @Override
    public void remove(Handle handle) throws RemoteException, RemoveException {
        // TODO Auto-generated method stub

    }

    @Override
    public void remove(Object obj) throws EJBException, RemoveException {
        // TODO Auto-generated method stub

    }

    @Override
    public EJBMetaData getEJBMetaData() throws RemoteException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public HomeHandle getHomeHandle() throws RemoteException {
        // TODO Auto-generated method stub
        return null;
    }

}
