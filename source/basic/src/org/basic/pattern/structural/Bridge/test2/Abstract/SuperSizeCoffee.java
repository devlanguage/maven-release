/**
 * Copyright reserved by Tellabs Communication Corp. LTD. The file
 * org.basic.pattern.Bridge.test2.SuperSizeCoffee.java is created on 2008-8-21
 */
package org.basic.pattern.structural.Bridge.test2.Abstract;

import org.basic.pattern.structural.Bridge.test2.Coffee;
import org.basic.pattern.structural.Bridge.test2.CoffeeAction;

// 大杯
public class SuperSizeCoffee extends Coffee {

    public SuperSizeCoffee(CoffeeAction coffeeAction) {

        super(coffeeAction);
    }

    public void pourCoffee() {

        // 我们以重复次数来说明是冲中杯还是大杯 ,重复5次是大杯
        System.err.println("大杯咖啡: ");
        for (int i = 0; i < 2; i++) {
            coffeeAction.pourCoffeeImp();
        }
    }
}