package org.basic.jdk.core.pattern.structural.Bridge.test1.action;

import org.basic.jdk.core.pattern.structural.Bridge.test1.TextImp;

/**
 *  The ConcreteImplementor
 */
public class TextImpLinux implements TextImp {
    public TextImpLinux() {
    }
    public void drawTextImp() {
        System.out.println("The text has a Linux style !");
    }
}