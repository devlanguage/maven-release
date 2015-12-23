/**
 * Copyright reserved by Tellabs Communication Corp. LTD. The file
 * org.basic.threads.basic.bank.BankAccount.java is created on 2008-9-16
 */
package org.basic.concurrent.mutiple_method.unsynchronized;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 
 */
public class BankAccount {

    String name;
    // float amount;

    // 使用�?��Map模拟持久存储
    static Map<String, Float> BANK_STORAGE = new HashMap<String, Float>();
    static Map<String, BankAccount> LOGON_ACCOUNTS = new HashMap<String, BankAccount>();

    static {
        BANK_STORAGE.put("John", new Float(1000.0f));
        BANK_STORAGE.put("Mike", new Float(800.0f));

        Collections.synchronizedMap(LOGON_ACCOUNTS);
    }

    public synchronized final static BankAccount getBankAccount(String userName) {

        BankAccount account = LOGON_ACCOUNTS.get(userName);
        if (account == null) {
            account = new BankAccount(userName);
            LOGON_ACCOUNTS.put(userName, account);
        }
        return account;
    }

    private BankAccount(String name) {

        // System.out.println("new account:" + name);
        this.name = name;
        // this.amount = ((Float)storage.get(name)).floatValue();
    }

    public synchronized void deposit(float amt) {

        float amount = BANK_STORAGE.get(name).floatValue();
        BANK_STORAGE.put(name, new Float(amount + amt));
    }

    public synchronized void withdraw(float amt) throws InsufficientBalanceException {

        float amount = BANK_STORAGE.get(name).floatValue();
        if (amount >= amt) {
            amount -= amt;
        } else {
            throw new InsufficientBalanceException();
        }

        BANK_STORAGE.put(name, new Float(amount));
    }

    public float getBalance() {

        float amount = BANK_STORAGE.get(name).floatValue();
        return amount;
    }

}
