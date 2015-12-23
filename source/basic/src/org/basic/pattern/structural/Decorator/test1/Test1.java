package org.basic.pattern.structural.Decorator.test1;
/**
 *  A simple test
 */
public class Test1  {
    public static void main(String[] args) {
        Component myComponent = new ConcreteComponent();
        myComponent.printString("A test String");
        Decorator myDecorator = new ConcreteDecoratorA(myComponent);
        myDecorator.printString("A test String");
    }
}