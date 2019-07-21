package org.basic.grammar.pattern.structural.Decorator.test1;

/**
 * The Concrete Decorator
 */

public class ConcreteDecoratorA extends Decorator {

    public ConcreteDecoratorA(Component c) {

        super(c);
    }

    public void printString(String s) {

        super.printString(s);
        printStringLen(s);
    }

    public void printStringLen(String s) {

        System.out.println("The length of string is:" + s.length());
    }
}