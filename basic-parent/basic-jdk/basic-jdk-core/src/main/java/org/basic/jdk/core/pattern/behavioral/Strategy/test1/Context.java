package org.basic.jdk.core.pattern.behavioral.Strategy.test1;
/**
 *  The context user used
 */

public class Context  {
    private Strategy strategy = null;
    private int lineWidth;
    private int lineCount;
    private String text;
    
    public Context(int lineWidth, int lineCount) {
        this.lineWidth = lineWidth;
        this.lineCount = lineCount;
    }
    public void setStrategy(Strategy s) {
        if(s != null) {
            strategy = s;
        }
    }
    public void executeStrategy() {
        strategy.drawText(text, lineWidth, lineCount);
    }
    public void setText(String str) {
        text = str;
    }
    public void setLineWidth(int width) {
        lineWidth = width;
    }
    public void setLineCount(int count) {
        lineCount = count;
    }
    public String getText() {
        return text;
    }
}