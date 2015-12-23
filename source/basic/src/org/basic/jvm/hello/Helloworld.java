/**
 * Copyright reserved by Tellabs Communication Corp. LTD. The file
 * org.insidejvm.hello.Helloworld.java is created on 2008-2-25
 */
package org.basic.jvm.hello;

/**
 * 
 */
public class Helloworld {

    public static void main(String[] args) {

        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();//
        ClassLoader appClassLoader = Helloworld.class.getClassLoader();//same to systemClassLoader
        ClassLoader extClassLoader = Helloworld.class.getClassLoader().getParent();
        ClassLoader bootstrapClassLoader = Helloworld.class.getClassLoader().getParent()
                .getParent();

        System.out.println(systemClassLoader);
        System.out.println(appClassLoader);
        System.out.println(extClassLoader);
        System.out.println(bootstrapClassLoader);

        System.out.println(String.class.getClassLoader());
        // try {
        // Object o = Class.forName("org.insidejvm.hello.User").newInstance();
        // System.out.println(o);
        // System.out.println(o.getClass()
        // .getClassLoader());
        // } catch (InstantiationException e) {
        // e.printStackTrace();
        // } catch (IllegalAccessException e) {
        // e.printStackTrace();
        // } catch (ClassNotFoundException e) {
        // e.printStackTrace();
        // }

        HelloworldClassLoader1 helloworldClassLoader = new HelloworldClassLoader1(bootstrapClassLoader,
                "C:/Software/eclipse3.2.2/workspace/jvm/resources");
        // HelloworldClassLoader1 helloworldClassLoader = new HelloworldClassLoader1(extClassLoader,
        // "C:/Software/eclipse3.2.2/workspace/jvm/resources");
        // HelloworldClassLoader1 helloworldClassLoader = new HelloworldClassLoader1(appClassLoader,
        // "C:/Software/eclipse3.2.2/workspace/jvm/resources");
        
        try {
            Class clazz = helloworldClassLoader.loadClass("org.insidejvm.hello.User");
            System.out.println(clazz.newInstance());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

}
