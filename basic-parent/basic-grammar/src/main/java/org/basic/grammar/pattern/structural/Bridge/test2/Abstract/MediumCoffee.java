package org.basic.grammar.pattern.structural.Bridge.test2.Abstract;

import org.basic.grammar.pattern.structural.Bridge.test2.Coffee;
import org.basic.grammar.pattern.structural.Bridge.test2.CoffeeAction;

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