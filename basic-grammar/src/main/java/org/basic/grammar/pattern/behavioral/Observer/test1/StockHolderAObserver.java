/**
 * Copyright reserved by Tellabs Communication Corp. LTD. The file
 * org.basic.pattern.Observer.test1.StockHolderAObserver.java is created on 2008-6-12
 */
package org.basic.grammar.pattern.behavioral.Observer.test1;

import java.util.Observable;

/**
 * 
 */
public class StockHolderAObserver implements java.util.Observer {

    public void update(Observable stockSubject, Object notifyObserversParam) {

        System.out.println("This is " + this.getClass().getSimpleName());
        System.out.println("notifyObserversParam: " + notifyObserversParam);

    }

}
