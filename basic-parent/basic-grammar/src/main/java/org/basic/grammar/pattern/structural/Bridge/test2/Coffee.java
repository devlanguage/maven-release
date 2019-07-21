package org.basic.grammar.pattern.structural.Bridge.test2;

public abstract class Coffee {

    protected CoffeeAction coffeeAction;

    protected Coffee(CoffeeAction coffeeAction) {

        this.coffeeAction = coffeeAction;
    }

    public void setCoffeeImp(CoffeeAction coffeeAction) {

        this.coffeeAction = coffeeAction;
    }

    public CoffeeAction getCoffeeImp() {

        return this.coffeeAction;
    }

    /**
     * Coffee related Action
     * 
     */
    public abstract void pourCoffee();
}