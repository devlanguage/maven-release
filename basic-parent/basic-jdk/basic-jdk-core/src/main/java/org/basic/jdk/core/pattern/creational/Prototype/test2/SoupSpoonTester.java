package org.basic.jdk.core.pattern.creational.Prototype.test2;

public class SoupSpoonTester {

    public static void main(String[] args) {

        AbstractSpoon spoon1 = new SoupSpoon();
        AbstractSpoon spoon2 = (AbstractSpoon) spoon1.clone();
        AbstractSpoon spoon3 = (AbstractSpoon) spoon1.clone();
        System.out.println(spoon1);
        System.out.println(spoon2);
        System.out.println(spoon3);
    }
}
