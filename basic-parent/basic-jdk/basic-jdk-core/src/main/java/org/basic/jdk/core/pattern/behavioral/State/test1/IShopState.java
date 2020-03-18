package org.basic.jdk.core.pattern.behavioral.State.test1;

/**
 * A state interface
 */
public interface IShopState {
    void shop();

    void generateBill();

    void pay();

    void changeState(ShopContext c, IShopState i);
}