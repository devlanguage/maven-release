package org.basic.jdk.core.pattern.behavioral.Strategy.test1;

/**
 * A test client
 */
public class Test {

    public static void main(String[] args) {
        int lineCount = 4;
        int lineWidth = 12;
        
        Context myContext = new Context(Strategy.DEFAULT_LINE_WIDTH, Strategy.DEFAULT_LINE_COUNT);
        StrategyA strategyA = new StrategyA();
        StrategyB strategyB = new StrategyB();
        String s = ",101,102,103,104,105" +
                ",106,107,108,109,110" +
                ",111,112,113,114,115" +
                ",116,117,118,119,120";
        myContext.setText(s);
        
        myContext.setLineWidth(lineWidth);        
        myContext.setStrategy(strategyA);
        myContext.executeStrategy();

        myContext.setLineCount(lineCount);
        myContext.setStrategy(strategyB);
        myContext.executeStrategy();
   
    }
}