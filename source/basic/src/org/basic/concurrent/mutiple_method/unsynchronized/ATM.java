/**
 * Copyright reserved by Tellabs Communication Corp. LTD. The file
 * org.basic.threads.basic.bank.ATM.java is created on 2008-9-16
 */
package org.basic.concurrent.mutiple_method.unsynchronized;

/**
 * 
 */
public class ATM {

    BankAccount acc;
    String atmName;
    
    java.util.concurrent.locks.Lock lock = new java.util.concurrent.locks.ReentrantLock();

    public ATM(String atmName) {

        this.atmName = atmName;
    }

    // 作为演示，省略了密码验证
    public synchronized boolean login(String user) {
        lock.lock();
        if (acc != null) {
            throw new IllegalArgumentException("Already logged in!");
        }
        acc = BankAccount.getBankAccount(user);
        System.out.println(user + " login in ATM " + this);
        return true;
    }

    public void deposit(float amt) {

        acc.deposit(amt);
    }

    public void withdraw(float amt) throws InsufficientBalanceException {

        acc.withdraw(amt);
    }

    public float getBalance() {

        return acc.getBalance();
    }

    public synchronized void logout() {

        acc = null;
        lock.unlock();
    }

    @Override
    public String toString() {

        return atmName;
    }
}
