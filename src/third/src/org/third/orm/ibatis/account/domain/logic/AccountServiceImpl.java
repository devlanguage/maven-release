package org.third.orm.ibatis.account.domain.logic;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.third.orm.ibatis.account.dao.AccountDao;
import org.third.orm.ibatis.account.dao.AccountDaoLocator;
import org.third.orm.ibatis.account.dao.SequenceDao;
import org.third.orm.ibatis.account.dao.UserDao;
import org.third.orm.ibatis.account.dao.UserInfoDao;
import org.third.orm.ibatis.account.domain.persistence.Account;
import org.third.orm.ibatis.account.domain.persistence.User;
import org.third.orm.ibatis.account.domain.persistence.UserInfo;

public class AccountServiceImpl implements AccountService, AccountServiceFacade {

    private UserDao userDao;
    private SequenceDao sequenceDao;
    private UserInfoDao userInfoDao;
    private AccountDao accountDao;

    private AccountDaoLocator daoLocator;
    static Logger logger = Logger.getLogger(AccountServiceImpl.class);

    public AccountServiceImpl() {
        daoLocator = AccountDaoLocator.getInstance();

        // accountDaoManager = AccountDaoLocator.getInstance().getDaoManager();
        initDaos();
    }

    private void initDaos() {

        this.userDao = daoLocator.getUserDao();
        this.sequenceDao = daoLocator.getSequenceDao();
        this.userInfoDao = daoLocator.getUserInfoDao();
        this.accountDao = daoLocator.getAccountDao();
    }

    public void addNewUser(User user) {

        Integer id = sequenceDao.getSequenceUserId();
        user.setUserId(id);
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(id);
        userInfo.setLastLogDate(null);
        userInfo.setLogTimes(new Integer(0));
        userInfo.setRegDate(new Date());

        userDao.insertUser(user);
        userInfoDao.insertUserInfo(userInfo);
    }

    public boolean existUserAlias(String userAlias) {

        List users = userDao.getUser(userAlias);
        if (users != null && users.size() > 0)
            return true;
        return false;
    }

    public User existUser(String userAlias, String password) {

        List users = userDao.getUser(userAlias, password);
        if (users != null && users.size() > 0)
            return (User) users.get(0);
        return null;
    }

    public boolean existEmail(String email) {

        List users = userDao.getUserByEmail(email);
        if (users != null && users.size() > 0)
            return true;
        return false;
    }

    public boolean existUserId(Integer userId) {

        if (userId.intValue() == 0)
            return false;
        return userDao.existUserId(userId);
    }

    public List<User> listUsers() {

        return userDao.listUsers();
    }

    public List<Account> listAccounts() {

        return accountDao.listAccounts();
    }

    public List<Account> listAccounts(Account condition) {

        return accountDao.listAccounts(condition);
    }

}
