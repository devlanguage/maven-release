package org.basic.grammar.pattern.structural.Bridge.test2.action;

import org.basic.grammar.pattern.structural.Bridge.test2.CoffeeAction;

public class NoMilkCoffeeAction extends CoffeeAction {

    public NoMilkCoffeeAction() {

    }

    public void pourCoffeeImp() {

        System.out.println("\tNoMilkCoffeeAction: pourCoffeeImp");
    }
}