
import org.third.orm.ibatis.account.dao.AccountDao;
import org.third.orm.ibatis.account.dao.AccountDaoLocator;
import org.third.orm.ibatis.account.domain.persistence.Account;

public class IbatisTet {

    public static void main(String[] args) {

        AccountDaoLocator locator = AccountDaoLocator.getInstance();
        Account accountConiditon = new Account();
        accountConiditon.setSortField("ID");
        accountConiditon.setAscending(false);
        AccountDao accountDao = locator.getAccountDao();
        for (Account account : accountDao.listAccounts(accountConiditon)) {
            System.out.println(account);
        }
    }

}
