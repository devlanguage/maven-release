package org.basic.jdk.core.pattern.structural.Bridge.test1;

import org.basic.jdk.core.pattern.structural.Bridge.test1.Abstract.TextBold;
import org.basic.jdk.core.pattern.structural.Bridge.test1.Abstract.TextItalic;

/**
 * A test client
 */
public class BridgeTester {

    public BridgeTester() {

    }

    public static void main(String[] args) {

        Text myText = new TextBold(Text.DrawerType.Mac);
        myText.drawText("=== A test String ===");

        myText = new TextBold(Text.DrawerType.Linux);
        myText.drawText("=== A test String ===");

        System.out.println("------------------------------------------");

        myText = new TextItalic(Text.DrawerType.Mac);
        myText.drawText("=== A test String ===");

        myText = new TextItalic(Text.DrawerType.Linux);
        myText.drawText("=== A test String ===");
    }
}