/**
 * Copyright reserved by Tellabs Communication Corp. LTD. The file
 * org.basic.pattern.Bridge.test2.CoffeeImpSingleton.java is created on 2008-8-21
 */
package org.basic.grammar.pattern.structural.Bridge.test2;

import org.basic.grammar.pattern.structural.Bridge.test2.action.MilkCoffeeAction;
import org.basic.grammar.pattern.structural.Bridge.test2.action.NoMilkCoffeeAction;

public class CoffeeActionFactory {

    public static CoffeeActionFactory instance;

    private CoffeeActionFactory() {

    }

    public synchronized final static CoffeeActionFactory getInstance() {

        if (instance == null) {
            instance = new CoffeeActionFactory();
        }
        return instance;
    }

    public final static int MILK_COFFEE = 1;
    public final static int NO_MILK_COFFEE = 2;

    public CoffeeAction createCoffeeImpl(int coffeeImplType) {

        CoffeeAction coffeeImpl;
        switch (coffeeImplType) {
            case MILK_COFFEE:
                coffeeImpl = new MilkCoffeeAction();
                break;

            default:
                coffeeImpl = new NoMilkCoffeeAction();
                break;
        }
        return coffeeImpl;
    }
}