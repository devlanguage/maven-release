package org.third.orm.ibatis.account.dao;

import java.util.List;

import org.third.orm.ibatis.account.domain.persistence.Account;

import com.ibatis.sqlmap.client.event.RowHandler;

public interface AccountDao extends BaseDao {

    List<Account> listAccounts();

    public List<Account> listAccounts(int pageSize);
    public List<Account> listAccounts(Account condition);
    
    public void listAccountsWithRowHandler(RowHandler rowHandler);
}
