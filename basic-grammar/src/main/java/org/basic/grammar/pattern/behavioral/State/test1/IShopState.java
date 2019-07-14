package org.basic.grammar.pattern.behavioral.State.test1;

/**
 * A state interface
 */
public interface IShopState {
    void shop();

    void generateBill();

    void pay();

    void changeState(ShopContext c, IShopState i);
}