package org.basic.net.c19_jndi;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;

/**
 * NoInitialContextException是因为无法从System.properties中获得必要的JNDI参数，在服务器环境下，服务器启动时就把这些参数放到System.properties中了，于是直接new
 * InitialContext()就搞定了
 * 
 * 但是在单机环境下，可没有JNDI服务在运行，那就手动启动一个JNDI服务。我在JDK 5的rt.jar中一共找到了4种SUN自带的JNDI实现：
 * 
 * LDAP，CORBA，RMI，DNS。
 * 
 * 这4种JNDI要正常运行还需要底层的相应服务。 一般我们没有LDAP或CORBA服务器，也就无法启动这两种JNDI服务，DNS用于查域名的，以后再研究，唯一可以在main()中启动的就是基于RMI的JNDI服务
 */
public class JndiTest {

    public final static void startJndiRmiServer() throws Exception {

        Registry registry = LocateRegistry.createRegistry(1099);
        // System.setProperty(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.rmi.registry.RegistryContextFactory");
        // System.setProperty(Context.PROVIDER_URL, "rmi://localhost:1099");
        // InitialContext ctx = new InitialContext();
        class RemoteDate extends Date implements java.rmi.Remote {
        }
        // ctx.bind("java:comp/env/systemStartTime", new RemoteDate());
        // ctx.close();
        // registry.bind("java:comp/env/systemStartTime", new RemoteDate());
        Naming.bind("java:comp/env/systemStartTime", new RemoteDate());
    }

    // 注意，我直接把JNDI的相关参数放入了System.properties中，这样，后面的代码如果要查JNDI，直接new InitialContext()就可以了，否则，你又得写类似下面的代码
    // Hashtable env = new Hashtable();
    // env.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
    // env.put(Context.PROVIDER_URL,"t3://localhost:7001");
    // InitialContext ctx = new InitialContext(env);
    //
    // 在RMI中绑JNDI的限制是，绑定的对象必须是Remote类型，所以就自己扩展一个。
    //
    // 其实JNDI还有两个Context.SECURITY_PRINCIPAL和Context.SECURITY_CREDENTIAL，如果访问JNDI需要用户名和口令，这两个也要提供，不过一般用不上。
    //
    // 在后面的代码中查询就简单了：
    //
    // Java代码
    // InitialContext ctx = new InitialContext();
    // Date startTime = (Date) ctx.lookup("java:comp/env/systemStartTime");
    //
    // InitialContext ctx = new InitialContext();
    // Date startTime = (Date) ctx.lookup("java:comp/env/systemStartTime");
    //
    // 在SUN 的JNDI tutorial中的例子用的com.sun.jndi.fscontext.RefFSContextFactory类，但是我死活在JDK
    // 5中没有找到这个类，也就是NoClassDefFoundError，他也不说用的哪个扩展包，我也懒得找了。
    public static void main(String[] args) {

        try {
            startJndiRmiServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // InitialContext ctx = new InitialContext();
        // Date startTime = (Date) ctx.lookup("java:comp/env/systemStartTime");
    }
}
