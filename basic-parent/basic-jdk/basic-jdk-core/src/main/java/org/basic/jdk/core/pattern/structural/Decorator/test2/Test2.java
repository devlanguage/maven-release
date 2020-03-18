package org.basic.jdk.core.pattern.structural.Decorator.test2;

public class Test2 {

    public static void main(String[] args) {

        Work squarePeg = new SquarePeg();
        Work decorator = new WorkDecorator(squarePeg);
        decorator.insert();
    }

}