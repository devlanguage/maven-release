/**
 * Copyright reserved by Tellabs Communication Corp. LTD. The file
 * org.basic.pattern.Bridge.test2.FragrantCoffeeImp.java is created on 2008-8-21
 */
package org.basic.grammar.pattern.structural.Bridge.test2.action;

import org.basic.grammar.pattern.structural.Bridge.test2.CoffeeAction;

public class NoMilkCoffeeAction extends CoffeeAction {

    public NoMilkCoffeeAction() {

    }

    public void pourCoffeeImp() {

        System.out.println("\tNoMilkCoffeeAction: pourCoffeeImp");
    }
}