package org.basic.pattern.behavioral.State.test1;
/**
 *  A concrete state for customer shopping
 */

public class PayingState extends ShopStateAbstract {
    public static boolean instanceFlag = false; //true if have 1 instance
    private PayingState() {
    }
    public static PayingState getInstance() {
        if(! instanceFlag) {
            instanceFlag = true;
            return new PayingState();
        }
        return null;
    }
    public void pay() {
        System.out.println("The state is pay now !");
    }
   
}