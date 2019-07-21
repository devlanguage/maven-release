package org.basic.grammar.pattern.behavioral.Observer.test1;

import java.util.Observable;

public class StockHolderBObserver implements java.util.Observer {

    public void update(Observable stockSubject, Object arg) {

        System.out.println(stockSubject);
        System.out.println(arg);
    }

}
