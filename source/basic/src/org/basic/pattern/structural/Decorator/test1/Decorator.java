package org.basic.pattern.structural.Decorator.test1;
/**
 *  The Decorator
 */
public class Decorator implements Component {
    private Component component;
    public Decorator(Component c) {
        component = c;
    }
    public void printString(String s) {
        component.printString(s);
    }
}