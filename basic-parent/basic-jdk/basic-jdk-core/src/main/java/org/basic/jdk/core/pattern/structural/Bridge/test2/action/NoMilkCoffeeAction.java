package org.basic.jdk.core.pattern.structural.Bridge.test2.action;

import org.basic.jdk.core.pattern.structural.Bridge.test2.CoffeeAction;

public class NoMilkCoffeeAction extends CoffeeAction {

    public NoMilkCoffeeAction() {

    }

    public void pourCoffeeImp() {

        System.out.println("\tNoMilkCoffeeAction: pourCoffeeImp");
    }
}