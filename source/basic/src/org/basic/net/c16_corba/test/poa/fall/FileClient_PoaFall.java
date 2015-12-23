package org.basic.net.c16_corba.test.poa.fall;

import org.basic.net.c16_corba.poa.fall.FileInterface;
import org.basic.net.c16_corba.poa.fall.FileInterfaceHelper;
import org.basic.net.c16_corba.test.poa.OrbHelper_Poa;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

public class FileClient_PoaFall {

    public static void main(String args[]) {

        try {
            // create and initialize the ORB
            ORB orb = OrbHelper_Poa.getTnsNameservOrb23();
            // get the root naming context
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService"); //TNameService: 获得暂时NameService
            // Use NamingContextExt instead of NamingContext. This is part of the Interoperable Naming Service.
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
            // resolve the Object Reference in Naming
            String name = "FileInterface";
            FileInterface impl = FileInterfaceHelper.narrow(ncRef.resolve_str(name));
            String response = impl.sayHi("客户端").stuName;
            System.out.println("Handle obtained on server object: " +response);
            System.out.println("Handle obtained on server object: " +new String(response.getBytes("ISO-8859-1")));
            System.out.println("Handle obtained on server object: " +new String(response.getBytes("UTF-8")));
            System.out.println("Handle obtained on server object: " +new String(response.getBytes("ISO-8859-1"),"GBK"));
            System.out.println("Handle obtained on server object: " +new String(response.getBytes("UTF-8"),"GBK"));
            System.out.println("Handle obtained on server object: " +new String(response.getBytes("ISO-8859-1"),"GBK"));
            

        } catch (Exception e) {

            System.out.println("ERROR : " + e);

            e.printStackTrace(System.out);

        }
    }

}
