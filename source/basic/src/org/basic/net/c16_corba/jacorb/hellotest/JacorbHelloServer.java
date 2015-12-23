package org.basic.net.c16_corba.jacorb.hellotest;

import java.util.Properties;

import org.basic.net.c16_corba.jacorb.JacorbHelper;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

public class JacorbHelloServer {
    public static void main(String[] args) {
        try {

            ORB  orb = JacorbHelper.getOrb();
            
            POA poa = POAHelper.narrow(orb.resolve_initial_references("RootPOA")); // 初始化 POA
            poa.the_POAManager().activate();

            // 创建一个 GoodDay 对象
            GoodDayImpl goodDayImpl = new GoodDayImpl();

            // 创建 GoodDay 对象的引用
            org.omg.CORBA.Object obj = poa.servant_to_reference(goodDayImpl);

            // 使用 naming service
//            Local call
            NamingContextExt ncLocal = NamingContextExtHelper.narrow(orb.resolve_initial_references("NameService"));
            ncLocal.bind(ncLocal.to_name("hello.goodDay"), obj);
//            
//          Remote call            
//            org.omg.CORBA.Object ncobj = orb.string_to_object(JacorbHelper.Remote_Name_Service_URL);
//            NamingContextExt ncRemote = NamingContextExtHelper.narrow(ncobj);
//            ncRemote.rebind(ncRemote.to_name("hello.goodDay"), obj); // 绑定对象

            orb.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
