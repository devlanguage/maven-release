package org.basic.jdk.core.pattern.creational.Singleton.test1;

/**
 * 
 This solution is thread-safe without requiring special language constructs, but it may lack the laziness of the one
 * below. The INSTANCE is created as soon as the Singleton class is initialized. That might even be long before
 * getInstance() is called. It might be (for example) when some static method of the class is used. If laziness is not
 * needed or the instance needs to be created early in the application's execution, or your class has no other static
 * members or methods that could prompt early initialization (and thus creation of the instance), this (slightly)
 * simpler solution can be used:
 * 
 * 
 */
public class Singleton2_Eager {

    static private Singleton2_Eager instance = new Singleton2_Eager();

    private Singleton2_Eager() {

        System.out.println("Singleton2_Eager is construting");
    }

    public static Singleton2_Eager getInstance() {

        return instance;
    }

    public static void main(String[] args) {

        Singleton2_Eager instanc1 = Singleton2_Eager.getInstance();
        Singleton2_Eager instanc2 = Singleton2_Eager.getInstance();
        Singleton2_Eager instanc3 = Singleton2_Eager.getInstance();
        System.out.println(instanc1 == instanc2);
        System.out.println(instanc2 == instanc3);
    }
}