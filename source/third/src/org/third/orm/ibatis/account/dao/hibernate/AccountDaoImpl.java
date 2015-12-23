package org.third.orm.ibatis.account.dao.hibernate;

import java.util.List;

import org.hibernate.Session;
import org.third.orm.ibatis.account.dao.AccountDao;
import org.third.orm.ibatis.account.domain.persistence.Account;

import com.ibatis.sqlmap.client.event.RowHandler;

/**
 * 
 */
public class AccountDaoImpl extends BaseHibernateDao implements AccountDao {

    public AccountDaoImpl(Session session) {

        super(session);

    }

    private final static String SQL_LIST_ACCCOUNTS = "selectAllAccounts";
    private final static String SQL_GET_ACCOUNTS_BY_ACCOUNT = "selectAccountsByAccount";
    private final static String SQL_GET_XML_ACCOUNT_BY_ID = "selectAccountById";
    private final static String SQL_UPDATE_ACCOUNT_EMAIL = "updateAccountEmail";

    public List<Account> listAccounts() {

        // return super.find(SQL_LIST_ACCCOUNTS);
        return session.createQuery("select * from account").list();

    }

    public List<Account> listAccounts(Account condition) {

        // return this.find(SQL_LIST_ACCCOUNTS, condition);

        return null;
    }

    public int updateAccount(Account account) {

        int updateRowCount = 0;

        return updateRowCount;

    }

    public void listAccountsWithRowHandler(RowHandler rowHandler) {

        Account account = new Account();
        account.setId(1);
        account.setFirstName("yong%");
        account.setLastName("chen%");
        account.setEmailAddress("yongjie_g8100@126.com");

        this.session.update("updateAccountEmail", account);
    }

    public List<Account> listAccounts(int pageSize) {

        return null;
    }

}
