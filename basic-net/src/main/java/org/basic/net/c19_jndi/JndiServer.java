package org.basic.net.c19_jndi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Date;
import java.util.Hashtable;

import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingEnumeration;

public class JndiServer {

    public final static void startJndiRmiServer() throws Exception {

        Registry registry = LocateRegistry.createRegistry(11099);
        Hashtable env = new Hashtable();

        System.setProperty(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.rmi.registry.RegistryContextFactory");
        System.setProperty(Context.PROVIDER_URL, "rmi://localhost:11099");
        
//        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.fscontext.FSContextFactory");
//        env.put(Context.PROVIDER_URL, "file:///c:/nms");

        InitialContext ctx = new InitialContext(env);
        class RemoteDate extends Date implements java.rmi.Remote {
        }
        ctx.bind("java:comp/env/systemStartTime", new RemoteDate());

        ctx.close();
    }

    public static void main(String[] args) {

        try {
            startJndiRmiServer();
            InitialContext ctx = new InitialContext();
            // Date startTime = (Date) ctx.lookup("java:comp/env/systemStartTime");
            // System.out.println(startTime);
            NamingEnumeration ne = ctx.listBindings("");
            while (ne.hasMore()) {
                Binding b = (Binding) ne.next();
                System.out.println(b.getName() + " " + b.getObject());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
