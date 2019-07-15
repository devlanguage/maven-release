package org.third.orm.ibatis.account.client;

import java.util.List;
import java.util.Scanner;

import org.third.orm.ibatis.account.domain.logic.AccountService;
import org.third.orm.ibatis.account.domain.logic.AccountServiceImpl;
import org.third.orm.ibatis.account.domain.persistence.Account;

/**
 * 
 */
public class AccountServiceTester {

    private AccountService accountService = new AccountServiceImpl();
    private java.util.Scanner scanner = new Scanner(System.in);

    /**
     * @param args
     */
    public static void main(String[] args) {

        AccountServiceTester tester = new AccountServiceTester();
        // tester.testPaginatedList();
         tester.testSortedList();
//        tester.testRowHandler();
    }

    private void testSortedList() {

        Account condition = new Account();
        condition.setSortField("id");
        condition.setAscending(false);
        List<Account> accounts = accountService.listAccounts(condition);
        for (Account account : accounts) {
            System.out.println(account);
        }
    }

    private void testPaginatedList() {

        // System.out.print("Input the pageSize[pageSize=2]:");
        // int pageSize = scanner.nextInt();
        // PaginatedList accounts = accountService.listAccounts(pageSize);
        // int total = 0;
        // while (accounts.nextPage()) {
        // System.out.println("page " + accounts.getPageIndex() + ": ");
        // for (int i = 0; i < accounts.size(); ++i) {
        // System.out.println(accounts.get(i));
        // ++total;
        // }
        //
        // }
        // System.out.println("Total=" + total);
    }

    private void testRowHandler() {

        //
        // accountService.listAccountsWithRowHandler(new RowHandler() {
        //
        // public void handleRow(Object valueObject) {
        //
        // // Account account = (Account) valueObject;
        // System.out.println(valueObject);
        // }
        // });
    }

}
