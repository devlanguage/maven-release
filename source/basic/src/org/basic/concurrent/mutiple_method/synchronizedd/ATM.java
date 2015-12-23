package org.basic.concurrent.mutiple_method.synchronizedd;

public class ATM {

    BankAccount acc;
    String atmName;

    public ATM(String atmName) {

        this.atmName = atmName;
    }

    // 作为演示，省略了密码验证
    public synchronized boolean login(String name) {

        if (acc != null) {
            throw new IllegalArgumentException("Already logged in!");
        }
        acc = AccountFactory.getAccount(name);

        acc.lock();

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

        acc.unlock();

        acc = null;
    }

    @Override
    public String toString() {

        return atmName;
    }

}
