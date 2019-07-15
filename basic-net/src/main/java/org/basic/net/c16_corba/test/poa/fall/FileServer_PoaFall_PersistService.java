package org.basic.net.c16_corba.test.poa.fall;

import org.basic.net.c16_corba.test.poa.OrbHelper_Poa;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Policy;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.LifespanPolicyValue;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

public class FileServer_PoaFall_PersistService {

    public static void main(String args[]) {
        try {
          // create and initialize the ORB
          ORB orb = OrbHelper_Poa.getTnsNameservOrb23();

          // create servant and instantiate it
          FileServant_PoaFall servant = new FileServant_PoaFall();
          // get reference to rootpoa and activate the POAManager
          POA rootpoa = org.omg.PortableServer.POAHelper.narrow( orb.resolve_initial_references("RootPOA"));
          // Create the Persistent Policy
          Policy[] policy = new Policy[1];
          policy[0] = rootpoa.create_lifespan_policy( LifespanPolicyValue.PERSISTENT);

          // Create a persistent POA by passing the policy
          POA poa = rootpoa.create_POA("childPOA", rootpoa.the_POAManager(), policy ); 

          // Activate PersistentPOA's POAManager. Without this
          // all calls to persistent server will hang  because POAManager will be in the 'HOLD' state.
          poa.the_POAManager().activate( );

          // Associate the servant with PersistentPOA
          poa.activate_object( servant );

          // Resolve RootNaming context and bind a name for the servant.
          // "NameService" is used here....persistent name service.
          org.omg.CORBA.Object obj =  orb.resolve_initial_references("NameService" );
          NamingContextExt rootContext =  NamingContextExtHelper.narrow(obj);

          //bind the object reference in the naming context
          NameComponent[] nc = rootContext.to_name("FileInterface_Persist");

          rootContext.rebind(nc, poa.servant_to_reference(servant));
          // wait for client requests
          orb.run();
        } catch (Exception e) {
           System.err.println("Exception  in AddServer3 Startup " + e);
        }
      }
}
