package org.basic.grammar.pattern.behavioral.State.test1;
/**
 *  A concrete state for customer shopping
 */

public class ShoppingState extends ShopStateAbstract {
    public static boolean instanceFlag = false; //true if have 1 instance
    private ShoppingState() {
    }
    public static ShoppingState getInstance() {
        if(! instanceFlag) {
            instanceFlag = true;
            return new ShoppingState();
        }
        return null;
    }
    public void shop() {
        System.out.println("The state is shopping now !");
    }
}