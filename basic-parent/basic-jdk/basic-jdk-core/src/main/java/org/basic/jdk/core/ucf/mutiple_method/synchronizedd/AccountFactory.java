package org.basic.jdk.core.ucf.mutiple_method.synchronizedd;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class AccountFactory {

    private static Map<String, BankAccount> accounts = Collections
            .synchronizedMap(new HashMap<String, BankAccount>());

    public synchronized static BankAccount getAccount(String name) {

        if (accounts.get(name) == null) {
            accounts.put(name, new BankAccount(name));
        }
        return accounts.get(name);
    }

}
