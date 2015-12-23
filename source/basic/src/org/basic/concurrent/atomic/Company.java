/****************************************************************************
 *                 TELLABS PROPRIETARY AND CONFIDENTIAL
 *              UNPUBLISHED WORK COPYRIGHT 2009 TELLABS
 *                          ALL RIGHTS RESERVED
 *      NO PART OF THIS DOCUMENT MAY BE USED OR REPRODUCED WITHOUT
 *                   THE WRITTEN PERMISSION OF TELLABS.
 *  Last modifed on 5:17:55 PM May 6, 2014
 *
 *****************************************************************************
 */
package org.basic.concurrent.atomic;

/**
 * Created on May 6, 2014, 5:17:55 PM
 */
// 8. 创建一个类，名为 并一定实现 Runnable 接口。这个类会模拟公司付款。
public class Company implements Runnable {
    // 9. 声明一个私有 Account 属性，名为 account。
    private Account account;

    // 10. 实现类的构造函数，初始化它的属性值。
    public Company(Account account) {
        this.account = account;
    }

    // 11. 实现任务的 run() 方法。 使用 account 的 addAmount()方法来让它的余额做10次的递增，递增额为1000。
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("Adding " + i);
            account.addAmount(1000);
        }
    }

}
