package org.basic.jdk.core.pattern.behavioral.Observer.test1;

/**
 * 
 */
public class StockTester {

    /**
     * @param args
     */
    public static void main(String[] args) {

        StockSubject stockSubject = new StockSubject();
        stockSubject.addObserver(new StockHolderAObserver());
        stockSubject.addObserver(new StockHolderBObserver());
        System.out.println("\tSet price:");
        stockSubject.setPrice(Integer.valueOf(100));
        System.out.println("\tChange price:");
        stockSubject.addPrice(Integer.valueOf(50));
    }

}
