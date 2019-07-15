package org.third.orm.ibatis.account.domain.logic;

import java.util.List;

import org.third.orm.ibatis.account.domain.persistence.Account;
import org.third.orm.ibatis.account.domain.persistence.User;

public abstract interface AccountService {

    public void addNewUser(User user);

    public boolean existUserAlias(String userAlias);

    public User existUser(String userAlias, String password);

    public boolean existEmail(String email);

    public boolean existUserId(Integer userId);

    public List<User> listUsers();

    public List<Account> listAccounts();

    public List<Account> listAccounts(Account condition);

}
