package org.basic.jdk.core.pattern.behavioral.State.test1;

/**
 * A test client
 */
public class TestStatePattern {
    public static void main(String[] args) {
        ShopContext myContext = new ShopContext();
        IShopState myShop = ShoppingState.getInstance();
        IShopState myGenBill = GenerateBillState.getInstance();
        IShopState myPay = PayingState.getInstance();

        myContext.changeState(myShop);
        myContext.shop();

        myContext.changeState(myGenBill);
        myContext.generateBill();

        myContext.changeState(myPay);
        myContext.pay();

        myShop.changeState(myContext, myPay);
        myContext.pay();

    }
}