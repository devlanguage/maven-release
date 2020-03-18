package org.basic.jdk.core.pattern.creational.Singleton.test1;
/**
 *  A test for SingletonA
 */
public class TestA  {
    public static void main(String[] args) {
        // Can not create a instance !
        Singleton1_Lazy instance1 = Singleton1_Lazy.getInstance_BySyncMethod();
        Singleton1_Lazy instance2 = Singleton1_Lazy.getInstance_BySyncMethod();
        Singleton1_Lazy instance3 = Singleton1_Lazy.getInstance_BySyncMethod();
        
        System.out.println(instance1 == instance2);
        System.out.println(instance1 == instance3);
    }
}