package org.basic.grammar.pattern.structural.Bridge.test1;

import org.basic.grammar.pattern.structural.Bridge.test1.action.TextImpLinux;
import org.basic.grammar.pattern.structural.Bridge.test1.action.TextImpMac;

/**
 * The Abstract of Text
 */
public abstract class Text {

    public enum DrawerType {
        Mac, Linux
    }

    public abstract void drawText(String text);

    protected TextImp getTextImp(DrawerType type) {

        if (type == DrawerType.Mac) {
            return new TextImpMac();
        } else if (type == DrawerType.Linux) {
            return new TextImpLinux();
        } else {
            return new TextImpMac();
        }
    }
}