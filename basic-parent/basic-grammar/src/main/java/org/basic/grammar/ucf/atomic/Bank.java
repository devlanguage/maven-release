package org.basic.grammar.ucf.atomic;


/**
 * Created on May 6, 2014, 5:19:04 PM
 */
// 12. 创建一个类，名为 Bank，并一定实现 Runnable 接口。这个类会模拟从一个账号提款。
public class Bank implements Runnable {

    // 13. 声明一个私有 Account 属性，名为 account
    private Account account;

    // 14. 实现类的构造函数，初始化它的属性值。
    public Bank(Account account) {
        this.account = account;
    }

    // 15. 实现任务的 run() 方法。使用 account 的 subtractAmount() 方法来让它的余额做10次的递减，递减额为1000。
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("Substracting " + i+", balance="+account.getBalance());
            account.subtractAmount(1000);
        }
    }
}