package org.basic.net.c16_corba.test.poa;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class OrbHelper_Poa {

    public final static String HOST_NAME = "localhost";
    public final static String PORT = "900";

    public final static org.omg.CORBA.ORB getTnsNameservOrb() {

        Properties properteis = new Properties();
        properteis.setProperty("org.omg.CORBA.ORBClass", "org.jacorb.orb.ORB");
        properteis.setProperty("org.omg.CORBA.ORBSingletonClass", "org.jacorb.orb.ORBSingleton"); 
        properteis.setProperty("ORBInitRef.NameService", "corbaloc::localhost:30000/NameService"); 
        
//        properteis.put("org.omg.CORBA.ORBInitialHost", HOST_NAME);
//        properteis.put("orb.omg.CORBA.ORBInitialPort", PORT);
        
        properteis.setProperty("com.sun.CORBA.codeset.charsets", "0x05010001, 0x00010109"); // UTF-8, UTF-16
        properteis.setProperty("com.sun.CORBA.codeset.wcharsets", "0x00010109, 0x05010001"); // UTF-16, UTF-8
        org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init(new String[0], properteis);
        return orb;
    }

    public final static org.omg.CORBA.ORB getTnsNameservOrb23() {

        Properties properteis = new Properties();
//==JACORB
        properteis.setProperty("org.omg.CORBA.ORBClass", "org.jacorb.orb.ORB");
        properteis.setProperty("org.omg.CORBA.ORBSingletonClass", "org.jacorb.orb.ORBSingleton"); 
        properteis.setProperty("ORBInitRef.NameService", "corbaloc::localhost:4711/NameService"); 
        properteis.setProperty("jacorb.native_char_codeset", "0x05010001"); // UTF-8, UTF-16
        properteis.setProperty("jacorb.native_wchar_codeset", "0x00010109"); // UTF-16, UTF-8
        
        
//        properteis.put("org.omg.CORBA.ORBInitialHost", HOST_NAME);
//        properteis.put("orb.omg.CORBA.ORBInitialPort", PORT);
        properteis.setProperty("com.sun.CORBA.codeset.charsets", "0x05010001, 0x00010109"); // UTF-8, UTF-16
        properteis.setProperty("com.sun.CORBA.codeset.wcharsets", "0x00010109, 0x05010001"); // UTF-16, UTF-8
        org.omg.CORBA.ORB orb = org.omg.CORBA_2_3.ORB.init(new String[0], properteis);
        return orb;
    }

    public final static java.sql.Connection getConnection() throws SQLException {

        return DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:NGX5500A", "ygong", "oracle");
    }
}
