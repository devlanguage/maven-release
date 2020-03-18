package org.basic.jdk.core.pattern.behavioral.State.test1;

/**
 * A concrete state for generating bill
 */

public class GenerateBillState extends ShopStateAbstract {

    public static boolean instanceFlag = false; // true if have 1 instance

    private GenerateBillState() {

    }

    public static GenerateBillState getInstance() {

        if (!instanceFlag) {
            instanceFlag = true;
            return new GenerateBillState();
        }
        return null;
    }

    public void generateBill() {

        System.out.println("The state is generating bill now !");
    }
}
