package org.basic.net.c16_corba.jacorb;

import java.util.Properties;

import org.omg.CORBA.ORB;

public class JacorbHelper {

    public final static String Remote_Name_Service_URL = "corbaloc::@localhost:29100/NameService";//corbaloc::1.2@localhost:29100/NameService

    public final static org.omg.CORBA.ORB getOrb() {

        Properties props = System.getProperties();
        props.put("org.omg.CORBA.ORBClass", "org.jacorb.orb.ORB");
        props.put("org.omg.CORBA.ORBSingletonClass", "org.jacorb.orb.ORBSingleton");

        ORB orb = ORB.init(new String[0], props);
        return orb;
    }
}
