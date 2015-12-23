package org.basic.pattern.behavioral.Strategy.test1;

/**
 * The public interface to support varies arithmetic
 */
public interface Strategy {

    public final static int DEFAULT_LINE_COUNT = 0;
    public final static int DEFAULT_LINE_WIDTH = 10;

    public void drawText(String s, int lineWidth, int lineCount);
}