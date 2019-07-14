package org.third.javaee.ejb2;

import java.rmi.RemoteException;

import javax.ejb.EJBException;
import javax.ejb.MessageDrivenContext;
import javax.ejb.SessionContext;

public class Ejb2ServiceBean implements javax.ejb.SessionBean, javax.ejb.MessageDrivenBean {
    private static final long serialVersionUID = -4089878466362341388L;
    SessionContext sessionContext;
    MessageDrivenContext messageDrivenContext;

    public String hello(String name) {
        sessionContext.isCallerInRole("");
        return "Hello:" + name;
    }

    @Override
    public void ejbActivate() throws EJBException, RemoteException {
        // TODO Auto-generated method stub

    }

    @Override
    public void ejbPassivate() throws EJBException, RemoteException {
        // TODO Auto-generated method stub

    }

    @Override
    public void ejbRemove() throws EJBException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setSessionContext(SessionContext sessioncontext) throws EJBException, RemoteException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setMessageDrivenContext(MessageDrivenContext messagedrivencontext) throws EJBException {
        // TODO Auto-generated method stub

    }

}
