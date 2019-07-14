/**
 * Copyright reserved by Tellabs Communication Corp. LTD. The file
 * org.basic.pattern.Bridge.test2.MilkCoffeeImp.java is created on 2008-8-21
 */
package org.basic.grammar.pattern.structural.Bridge.test2.action;

import org.basic.grammar.pattern.structural.Bridge.test2.CoffeeAction;

// 加奶
public class MilkCoffeeAction extends CoffeeAction {

    public MilkCoffeeAction() {

    }

    public void pourCoffeeImp() {

        System.out.println("\tMilkCoffeeAction: pourCoffeeImp");
    }
}
