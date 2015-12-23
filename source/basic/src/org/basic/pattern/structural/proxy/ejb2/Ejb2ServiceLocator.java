package org.basic.pattern.structural.proxy.ejb2;

import java.util.Properties;

import javax.ejb.EJBException;
import javax.ejb.EJBHome;

public class Ejb2ServiceLocator {
    Properties _prop = new Properties();

    public javax.ejb.EJBHome getEJBHome(String jndiName) {
        try {
            javax.naming.InitialContext ctx = null;

            if (_prop != null)
                ctx = new javax.naming.InitialContext(_prop);
            else
                ctx = new javax.naming.InitialContext();

            Object home = ctx.lookup(jndiName);
            EJBHome obHome = (EJBHome) javax.rmi.PortableRemoteObject.narrow(home, EJBHome.class);
            return obHome;
        } catch (javax.naming.NamingException ne) {
            throw new EJBException(ne);
        }
    }

}
