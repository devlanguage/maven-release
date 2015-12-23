package org.basic.net.c16_corba;

import java.util.Properties;

import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.IdAssignmentPolicyValue;
import org.omg.PortableServer.LifespanPolicyValue;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.ServantRetentionPolicyValue;

public class ORBHelper {

    private org.omg.CORBA.ORB orb;
    private POA rootpoa;
    private NamingContextExt ncRef;
    private String clientName;
    private String listenerEndPoint = null;
    private String JacOrbHomeLib = null;
    private String JacORB_NS = null;
    private POA persistentPOA = null;

    private boolean orb_init = false;

    /**
     * 
     * @param props
     * @return int
     */
    public int init(Properties props) {
//        props.put("java.endorsed.dirs", "E:/JacORB/lib");
//        props.put("org.omg.CORBA.ORBClass", "org.jacorb.orb.ORB");
//        props.put("org.omg.CORBA.ORBSingletonClass", "org.jacorb.orb.ORBSingleton");
//        props.put("ORBInitRef.NameService", "corbaloc::192.168.100.221:5060/NameService");
//        props.put("jacorb.user_imr", "on");
//        props.put("jacorb.use_imr_endpoint", "on");
//        props.put("jacorb.implname", "TEST");
//        props.put("ORBDottedDecimalAddresses", 1);
//        props.put("OAAddress", "iiop://192.168.100.100:10025");
//
//        OSPORBHelper orbHelper = new OSPORBHelper();
//        int ret = orbHelper.init(props);        
    
//        props.put("org.omg.CORBA.ORBInitialHost", "localhost");
//        props.put("orb.omg.CORBA.ORBInitialPort", "900");
//        props.setProperty("com.sun.CORBA.codeset.charsets", "0x05010001, 0x00010109"); // UTF-8, UTF-16
//        props.setProperty("com.sun.CORBA.codeset.wcharsets", "0x00010109, 0x05010001"); // UTF-16, UTF-8
        try {
            orb = org.omg.CORBA.ORB.init(new String[0], props);
        } catch (Exception e) {
            return -1;
        }
        try {
            resolveRootPoa();
        } catch (Exception e) {
            return -2;
        }
        try {
            resolve_naming_service();
        } catch (Exception e) {
            return -3;
        }

        orb_init = true;
        return 0;
    }

    public void close() {

        if (getNcRef() != null) {
            try {
                NameComponent nc = new NameComponent(clientName, " ");
                NameComponent path[] = new NameComponent[] { nc };
                getNcRef().unbind(path);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            try {
                NameComponent nc = new NameComponent(clientName, "");
                NameComponent path[] = new NameComponent[] { nc };
                getNcRef().unbind(path);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            try {
                getOrb().shutdown(false);
                getOrb().destroy();
                clearRootPOA();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        setOrbInit(false);

    }

    /**
     * 获取POA
     */
    private void resolveRootPoa() throws Exception {

        rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
        /* create policies */
        org.omg.CORBA.Policy[] policies = new org.omg.CORBA.Policy[3];
        policies[0] = rootpoa.create_id_assignment_policy(IdAssignmentPolicyValue.USER_ID);
        policies[1] = rootpoa.create_lifespan_policy(LifespanPolicyValue.PERSISTENT);
        policies[2] = rootpoa.create_servant_retention_policy(ServantRetentionPolicyValue.RETAIN);
        /* create POA */
        persistentPOA = rootpoa.create_POA("PersistentPOA", rootpoa.the_POAManager(), policies);
        // 激活该对象，否则还处于“保持状态”无法处理任何请求
        rootpoa.the_POAManager().activate();
        policies[0].destroy();
        policies[1].destroy();
        policies[2].destroy();
        policies = null;
    }

    /**
     * 获得NameServices的一个引用 客户端与服务端都需要再获取根名称索引后才能启动
     */
    private void resolve_naming_service() throws Exception {

        org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
        // Use NamingContextExt instead of NamingContext,
        ncRef = NamingContextExtHelper.narrow(objRef);
    }

    private void init_orb() {

    }

    public org.omg.CORBA.ORB getOrb() {

        return orb;
    }

    public POA getRootpoa() {

        return rootpoa;
    }

    public NamingContextExt getNcRef() {

        return ncRef;
    }

    public String getClientName() {

        return clientName;
    }

    public String getListenerEndPoint() {

        return listenerEndPoint;
    }

    public String getJacOrbHomeLib() {

        return JacOrbHomeLib;
    }

    public String getJacORB_NS() {

        return JacORB_NS;
    }

    public POA getPersistentPOA() {

        return persistentPOA;
    }

    public void clearRootPOA() {

        this.rootpoa = null;
    }

    /**
     * 查看orb是否初始化
     * 
     * @return boolean
     */
    public synchronized boolean getOrbInit() {

        return orb_init;
    }

    public synchronized void setOrbInit(boolean b) {

        this.orb_init = b;
    }

}
