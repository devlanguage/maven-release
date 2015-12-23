/**
 * Copyright reserved by Tellabs Communication Corp. LTD. The file
 * org.basic.threads.basic.bank.ATMTester.java is created on 2008-9-16
 */
package org.basic.concurrent.mutiple_method.unsynchronized;

/**
 * 
 */
public class ATMTester {

    private static final int NUM_OF_ATM = 10;

    public static void main(String[] args) {

        ATMTester tester = new ATMTester();

        final Thread thread[] = new Thread[NUM_OF_ATM];
        final ATM atm[] = new ATM[NUM_OF_ATM];
        for (int i = 0; i < NUM_OF_ATM; i++) {
            atm[i] = new ATM("ATM-" + (100 + i));
            thread[i] = new Thread(tester.new Runner(atm[i]));
            thread[i].start();
        }

    }

    class Runner implements Runnable {

        ATM atm;

        Runner(ATM atm) {

            this.atm = atm;
        }

        public void run() {

            String user = "John";
            atm.login(user);
            // 查询余额
            float bal = atm.getBalance();
            try {
                Thread.sleep(1); // 模拟人从查询到取款之间的间隔
            } catch (InterruptedException e) {
                // ignore it
            }

            try {
                System.out.println(user + "'s Balance is:" + bal + " from ATM " + atm);
                System.out.println(user + " withdraw:" + bal * 0.8f + " from ATM " + atm);
                atm.withdraw(bal * 0.8f);
                System.out.println("deposit:" + bal * 0.8f + " from ATM " + atm);
                atm.deposit(bal * 0.8f);
            } catch (InsufficientBalanceException e1) {
                System.out.println("余额不足� from ATM " + atm);
            } finally {
                atm.logout();
            }

        }
    }

}
