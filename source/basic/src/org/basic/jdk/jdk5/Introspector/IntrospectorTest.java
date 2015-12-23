package org.basic.jdk.jdk5.Introspector;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.util.Arrays;
import java.util.function.Consumer;

public class IntrospectorTest {
    static class User {
        private String userName = "test01";
    }

    public static void main(String[] args) {
        try {
            User s = new User();
            BeanInfo info = java.beans.Introspector.getBeanInfo(User.class);
            
            java.beans.PropertyDescriptor[] pds = info.getPropertyDescriptors();
            java.beans.MethodDescriptor[] mds = info.getMethodDescriptors();
            
            Consumer<Object> c1 =(x)->{
                System.out.println(x);
            };
            
            Arrays.stream(pds).forEach(c1);
            Arrays.stream(mds).forEach(c1);
        } catch (IntrospectionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
