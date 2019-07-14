package org.basic.grammar.pattern.structural.Decorator.test1;

/**
 * A Concrete Component
 */

public class ConcreteComponent implements Component {

    public ConcreteComponent() {

    }

    public void printString(String s) {

        System.out.println("Input String is:" + s);
    }
}