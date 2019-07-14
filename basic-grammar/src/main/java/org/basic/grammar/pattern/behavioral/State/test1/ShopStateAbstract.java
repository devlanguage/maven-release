package org.basic.grammar.pattern.behavioral.State.test1;
/**
 *  The parent class of state
 */
public abstract class ShopStateAbstract implements IShopState { 
    public void shop() { }
    public void generateBill() { }
    public void pay() { }
    public void changeState(ShopContext c, IShopState s) {
        c.changeState(s);
    }
    @Override
    public String toString() {

        return "[IShopState: " + this.getClass().getSimpleName()+"]";
    }
}