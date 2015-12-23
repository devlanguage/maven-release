package org.basic.net.c16_corba.jacorb.hellotest;

import org.basic.net.c16_corba.jacorb.JacorbHelper;
import org.basic.net.c16_corba.jacorb.hello.GoodDay;
import org.basic.net.c16_corba.jacorb.hello.GoodDayHelper;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

public class JacorbHelloClient {

    public static void main(String[] args) {

        try {
            

            ORB  orb = JacorbHelper.getOrb();
            GoodDay goodDay;

            // 使用 naming service
//            Local Call
//            NamingContextExt nc = NamingContextExtHelper.narrow(orb.resolve_initial_references("NameService"));
//            org.omg.CORBA.Object obj = nc.resolve(nc.to_name("hello.goodDay")); 
//
//            Remote call
            org.omg.CORBA.Object ncobj = orb.string_to_object(JacorbHelper.Remote_Name_Service_URL);
            NamingContextExt nc = NamingContextExtHelper.narrow(ncobj); 
            
            org.omg.CORBA.Object obj = nc.resolve(nc.to_name("hello.goodDay")); //解析对象
            goodDay = GoodDayHelper.narrow(obj); // 转换
            // GoodDay 接口调用
            System.out.println(goodDay.hello_latin1());
            System.out.println(goodDay.hello_chinese());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
