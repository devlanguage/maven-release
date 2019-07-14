package org.basic.grammar.pattern.structural.Bridge.test1.Abstract;

import org.basic.grammar.pattern.structural.Bridge.test1.Text;
import org.basic.grammar.pattern.structural.Bridge.test1.TextImp;

/**
 * The RefinedAbstraction
 */

public class TextBold extends Text {

    private TextImp imp;

    public TextBold(Text.DrawerType type) {

        imp = getTextImp(type);
    }

    public void drawText(String text) {

        System.out.println(text);
        System.out.println("The text is bold text!");
        imp.drawTextImp();

    }
}