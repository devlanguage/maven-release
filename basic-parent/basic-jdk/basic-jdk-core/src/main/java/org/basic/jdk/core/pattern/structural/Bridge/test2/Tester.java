package org.basic.jdk.core.pattern.structural.Bridge.test2;

import org.basic.jdk.core.pattern.structural.Bridge.test2.Abstract.MediumCoffee;
import org.basic.jdk.core.pattern.structural.Bridge.test2.Abstract.SuperSizeCoffee;

public class Tester {

    // 注意: Bridge模式的执行类如CoffeeImp和Coffee是一对一的关系, 正确创建CoffeeImp是该模式的关键,
    public static void main(String[] args) {

        // 看看中杯加奶 和大杯加奶 是怎么出来的:

        // 拿出牛奶
        CoffeeActionFactory coffeeImplFactory = CoffeeActionFactory.getInstance();

        // 中杯加奶
        MediumCoffee mediumCoffee = new MediumCoffee(coffeeImplFactory
                .createCoffeeImpl(CoffeeActionFactory.MILK_COFFEE));
        mediumCoffee.pourCoffee();

        // 大杯加奶
        SuperSizeCoffee superSizeCoffee = new SuperSizeCoffee(coffeeImplFactory
                .createCoffeeImpl(CoffeeActionFactory.NO_MILK_COFFEE));
        superSizeCoffee.pourCoffee();

    }
}
