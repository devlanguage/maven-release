package org.basic.jdk.core.pattern.structural.Bridge.test2.action;

import org.basic.jdk.core.pattern.structural.Bridge.test2.CoffeeAction;

// 加奶
public class MilkCoffeeAction extends CoffeeAction {

    public MilkCoffeeAction() {

    }

    public void pourCoffeeImp() {

        System.out.println("\tMilkCoffeeAction: pourCoffeeImp");
    }
}
