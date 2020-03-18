package org.basic.jdk.core.pattern.structural.Bridge.test1.action;

import org.basic.jdk.core.pattern.structural.Bridge.test1.TextImp;

/**
 *  The ConcreteImplementor
 */
public class TextImpMac implements TextImp {
    public TextImpMac() {
    }
    public void drawTextImp() {
        System.out.println("The text has a Mac style !");
    }
}