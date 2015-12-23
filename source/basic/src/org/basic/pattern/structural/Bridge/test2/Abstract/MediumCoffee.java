/**
 * Copyright reserved by Tellabs Communication Corp. LTD. The file
 * org.basic.pattern.Bridge.test2.MediumCoffee.java is created on 2008-8-21
 */
package org.basic.pattern.structural.Bridge.test2.Abstract;

import org.basic.pattern.structural.Bridge.test2.Coffee;
import org.basic.pattern.structural.Bridge.test2.CoffeeAction;

public class MediumCoffee extends Coffee {

    public MediumCoffee(CoffeeAction coffeeAction) {

        super(coffeeAction);
    }

    public void pourCoffee() {

        System.err.println("Pour Coffee: ");
        for (int i = 0; i < 1; i++) {
            coffeeAction.pourCoffeeImp();
        }
    }
}