package org.basic.grammar.ucf.atomic;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created on May 6, 2014, 5:17:47 PM
 */
public class Account {
    // 2. 声明一个私有 AtomicLong 属性，名为 balance，用来储存账号的余额。
    private AtomicLong balance;

    // 3. 实现类的构造函数，初始化它的属性值。
    public Account() {
        balance = new AtomicLong();
    }

    // 4. 实现一个方法，名为 getBalance()，用来返回余额属性值。
    public long getBalance() {
        return balance.get();
    }

    // 5. 实现一个方法，名为 setBalance()，用来设置余额属性值。
    public void setBalance(long balance) {
        this.balance.set(balance);
    }

    // 6. 实现一个方法，名为 addAmount()，来增加余额属性值。
    public void addAmount(long amount) {
        this.balance.getAndAdd(amount);
    }

    // 7. 实现一个方法，名为 substractAmount() 来减少余额属性值。
    public void subtractAmount(long amount) {
        this.balance.getAndAdd(-amount);
    }
}
