package org.basic.net.c16_corba.test.poa.fall;

import org.basic.net.c16_corba.poa.fall.FileInterface;
import org.basic.net.c16_corba.poa.fall.FileInterfaceHelper;
import org.basic.net.c16_corba.test.poa.OrbHelper_Poa;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

public class FileServer_PoaFall {

    public static void main(String[] args) {

        // create and initialize the ORB
        org.omg.CORBA.ORB orb = OrbHelper_Poa.getTnsNameservOrb23();
        // create an implementation and register it with the ORB
        FileServant_PoaFall poaFallImpl = new FileServant_PoaFall();

        try {
            // get reference to rootpoa & activate the POAManager
            POA rootpoa =  org.omg.PortableServer.POAHelper.narrow(orb.resolve_initial_references("RootPOA"));// Exception: InvalidName
            rootpoa.the_POAManager().activate();// Exception: AdapterInactive
            // get object reference from the servant
            org.omg.CORBA.Object fileRef = rootpoa.servant_to_reference(poaFallImpl);// Exception:
            // WrongPolicy,ServantNotActive
            FileInterface fileInterface = FileInterfaceHelper.narrow(fileRef);
            // get the root naming context
            // NameService invokes the name service
            org.omg.CORBA.Object namingServiceRef = orb.resolve_initial_references("NameService");
            // Use NamingContextExt which is part of the Interoperable
            // Naming Service (INS) specification.
            NamingContextExt ncRef = NamingContextExtHelper.narrow(namingServiceRef);

            // bind the Object Reference in Naming
            String name = "FileInterface";
            NameComponent path[] = ncRef.to_name(name);
            ncRef.rebind(path, fileInterface);

            System.out.println("AddServer   ready to add up your arrays ....");

            // wait for invocations from clients
            orb.run();
          } catch (Exception e) {
              System.err.println("ERROR: " + e);
              e.printStackTrace(System.out);

          }
          System.out.println("AddServer Exiting ....");

    }
}
