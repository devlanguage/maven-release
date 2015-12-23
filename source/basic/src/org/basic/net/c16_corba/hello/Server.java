package org.basic.net.c16_corba.hello;

import org.basic.net.c16_corba.hello.hellostub.GoodDay;
import org.basic.net.c16_corba.hello.hellostub.GoodDayHelper;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

public class Server {
    public static void main(String[] args) {
        // args = new String[] { "Hello.ref" };
        // if (args.length != 1) {
        // System.out.println("Usage: jaco demo.hello.Server <ior_file>");
        // System.exit(1);
        // }

        args = new String[] { "-ORBInitialPort", "1050" };
        try {
            // init ORB
            ORB orb = ORB.init(args, null);
            // 得到一个RootPOA的引用，并激活POAManager
            POA rootPoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootPoa.the_POAManager().activate();

            // create servant and register it with the ORB
            GoodDayImpl goodDayImpl = new GoodDayImpl("Somewhere");

            // 从服务中得到对象的引用
            org.omg.CORBA.Object ref = rootPoa.servant_to_reference(goodDayImpl);
            GoodDay href = GoodDayHelper.narrow(ref);
            // 得到一个根名称的上下文
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            // Use NamingContextExt which is part of the Interoperable Naming Service (INS) specification.
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            // 在命名上下文中绑定这个对象
            NameComponent path[] = ncRef.to_name(GoodDay.NAME_OBJECT);
            ncRef.rebind(path, href);
            System.out.println("HelloServer ready and waiting ...");

            // 启动线程服务，等待客户端的调用
            orb.run();
            // PrintWriter pw = new PrintWriter(new FileWriter(args[0]));
            //
            // // print stringified object reference to file
            // pw.println(orb.object_to_string(obj));
            //
            // pw.flush();
            // pw.close();

            // wait for requests
            orb.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
