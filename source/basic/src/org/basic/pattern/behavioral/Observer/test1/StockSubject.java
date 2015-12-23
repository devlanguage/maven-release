/**
 * Copyright reserved by Tellabs Communication Corp. LTD. The file
 * org.basic.pattern.Observer.test1.Stock.java is created on 2008-6-12
 */
package org.basic.pattern.behavioral.Observer.test1;

import java.util.Observer;


public class StockSubject extends java.util.Observable {

    private Integer price;

    public void addPrice(Integer priceIncrement) {

        this.price = Integer.valueOf(this.price.intValue() + priceIncrement.intValue());
        this.setChanged();
        this.notifyObservers(priceIncrement);
    }

    /**
     * @return get method for the field price
     */
    public Integer getPrice() {

        return this.price;
    }

    /**
     * @param price
     *            the price to set
     */
    public void setPrice(Integer price) {

        this.price = price;
        this.setChanged();
        this.notifyObservers("Prices Change: "+price);
    }

    @Override
    protected synchronized void setChanged() {
        super.setChanged();
    }
    @Override
    public void notifyObservers(Object arg) {
    
        super.notifyObservers(arg);
    }
    @Override
    public synchronized void addObserver(Observer o) {
        super.addObserver(o);
    }
    @Override
    public String toString() {

        return this.getClass().getSimpleName() + ":price=" + this.price;
    }
}
