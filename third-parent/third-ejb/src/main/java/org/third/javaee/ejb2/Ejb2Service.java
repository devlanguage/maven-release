package org.third.javaee.ejb2;

public interface Ejb2Service extends javax.ejb.EJBObject, javax.ejb.EJBLocalObject {
    public String hello(String name) throws java.rmi.RemoteException;;
}
