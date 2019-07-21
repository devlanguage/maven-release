package org.basic.net.c16_corba.hello;

import org.basic.net.c16_corba.hello.hellostub.GoodDay;
import org.basic.net.c16_corba.hello.hellostub.GoodDayHelper;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

public class Client {
    static GoodDay goodDayImpl;

    public static void main(String args[]) {
        // args = new String[] { "Hello.ior" };
        // if (args.length != 1) {
        // System.out.println("Usage: jaco demo.hello.Client <ior_file>");
        // System.exit(1);
        // }
        args = new String[] { "-ORBInitialPort", "1050" };
        try {

            // 创建一个ORB实例
            ORB orb = ORB.init(args, null);
            // 获取根名称上下文
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            // Use NamingContextExt instead of NamingContext. This is
            // part of the Interoperable naming Service.
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
            // 从命名上下文中获取接口实现对象
            goodDayImpl = GoodDayHelper.narrow(ncRef.resolve_str(GoodDay.NAME_OBJECT));
            // 调用接口对象的方法
            System.out.println("Obtained a handle on server object: " + goodDayImpl);
            System.out.println(goodDayImpl.helloSimple());
            // goodDayImpl.shutdown();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
