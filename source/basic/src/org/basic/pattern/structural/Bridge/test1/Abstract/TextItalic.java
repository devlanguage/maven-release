package org.basic.pattern.structural.Bridge.test1.Abstract;

import org.basic.pattern.structural.Bridge.test1.Text;
import org.basic.pattern.structural.Bridge.test1.TextImp;

/**
 * The RefinedAbstraction
 */
public class TextItalic extends Text {

    private TextImp imp;

    public TextItalic(Text.DrawerType type) {

        imp = getTextImp(type);
    }

    public void drawText(String text) {

        System.out.println(text);
        System.out.println("The text is italic text!");
        imp.drawTextImp();
    }
}