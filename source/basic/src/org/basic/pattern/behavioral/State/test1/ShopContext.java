package org.basic.pattern.behavioral.State.test1;
/**
 *  The context for user useing
 */
public class ShopContext  {
    private IShopState currentState;

    public ShopContext() {
    }
    public void changeState(IShopState lastState) {
        System.out.println("\t ShopState is changed from "+currentState+ " to "+lastState);
        currentState = lastState;        
    }
    public void shop() {
        currentState.shop();
    }
    public void generateBill() {
        currentState.generateBill();
    }
    public void pay() {
        currentState.pay();
    }
}