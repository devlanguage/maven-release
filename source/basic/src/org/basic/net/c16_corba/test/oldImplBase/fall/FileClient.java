package org.basic.net.c16_corba.test.oldImplBase.fall;

import java.io.File;

import org.basic.net.c16_corba.oldImplBase.fall.FileInterfaceHelper;
import org.basic.net.c16_corba.oldImplBase.fall.FileInterfaceOperations;
import org.basic.net.c16_corba.test.oldImplBase.OrbHelper_oldImplBase;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContext;
import org.omg.CosNaming.NamingContextHelper;

public class FileClient {

    public static void main(String argv[]) {

        try {
            // create and initialize the ORB
            org.omg.CORBA.ORB orb = OrbHelper_oldImplBase.getTnsNameservOrb23();
            // get the root naming context
            org.omg.CORBA.Object rootNamingContext = orb.resolve_initial_references("NameService");
            NamingContext ncRef = NamingContextHelper.narrow(rootNamingContext);
            NameComponent nc = new NameComponent("FileTransfer", " ");
            // Resolve the object reference in naming
            NameComponent path[] = { nc };
            FileInterfaceOperations fileRef = FileInterfaceHelper.narrow(ncRef.resolve(path));

            org.basic.net.c16_corba.oldImplBase.fall.entity.Student stu = fileRef.sayHi(new String("张按".getBytes("UTF-8")));
            System.out.println(stu.stuName);
        } catch (Exception e) {
            System.out.println("FileClient Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
