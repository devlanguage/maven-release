package org.basic.net.c16_corba.test.oldImplBase.fall;

import org.basic.net.c16_corba.test.oldImplBase.OrbHelper_oldImplBase;




public class FileServer {
    // tnameserv -ORBInitialPort 900
    //java FileServer -ORBInitialPort 900
//    java FileClient hello.txt -ORBInitialPort 900 
//     java FileClient hello.txt -ORBInitialHost gosling -ORBInitialPort 900 
    
//    Properties props = new Properties(); 
//    props.put("org.omg.CORBA.ORBInitialHost", "localhost"); 
//    props.put("orb.omg.CORBA.ORBInitialPort", "900"); 
//    ORB orb = ORB.init(args, props); 

    public static java.util.Properties tnameserv_param = new java.util.Properties();
    static {
        tnameserv_param.setProperty("org.omg.CORBA.ORBInitialHost", "localhost");
        tnameserv_param.setProperty("orb.omg.CORBA.ORBInitialPort", "900");
        tnameserv_param.setProperty("com.sun.CORBA.codeset.charsets", "0x05010001, 0x00010109"); // UTF-8, UTF-16
//        tnameserv_param.setProperty("com.sun.CORBA.codeset.wcharsets", "0x00010109, 0x05010001"); // UTF-16, UTF-8

//        tnameserv_param.put("org.omg.CORBA.ORBClass", "SomeORBImplementation");
    }
    
    public static void main(String args[]) {

        try {
            // create and initialize the ORB
            org.omg.CORBA.ORB orb = OrbHelper_oldImplBase.getTnsNameservOrb23();
            // create the servant and register it with the ORB
            FileServant_OldImplBase fileRef = new FileServant_OldImplBase();
            orb.connect(fileRef);
            // get the root naming context
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            org.omg.CosNaming.NamingContext ncRef = org.omg.CosNaming.NamingContextHelper.narrow(objRef);
            // Bind the object reference in naming
            org.omg.CosNaming.NameComponent path[] = { new  org.omg.CosNaming.NameComponent("FileTransfer", " ") };
            ncRef.rebind(path, fileRef);
            System.out.println("Server started....");
            // Wait for invocations from clients
            java.lang.Object sync = new java.lang.Object();
            synchronized (sync) {
                sync.wait();
            }
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace(System.out);
        }
    }
}