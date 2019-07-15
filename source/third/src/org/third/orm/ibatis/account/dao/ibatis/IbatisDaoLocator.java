
import org.third.orm.ibatis.account.dao.AccountDao;
import org.third.orm.ibatis.account.dao.AccountDaoLocator;
import org.third.orm.ibatis.account.dao.SequenceDao;
import org.third.orm.ibatis.account.dao.UserDao;
import org.third.orm.ibatis.account.dao.UserInfoDao;

import com.ibatis.dao.client.DaoManager;

public class IbatisDaoLocator extends AccountDaoLocator {

    public IbatisDaoLocator() {

        super();

    }

    private DaoManager daoManager;

    @Override
    public AccountDao getAccountDao() {

        return (AccountDao) this.daoManager.getDao(AccountDao.class);
    }

    @Override
    public SequenceDao getSequenceDao() {

        return (SequenceDao) this.daoManager.getDao(SequenceDao.class);
    }

    @Override
    public UserDao getUserDao() {

        return (UserDao) this.daoManager.getDao(UserDao.class);
    }

    @Override
    public UserInfoDao getUserInfoDao() {

        return (UserInfoDao) this.daoManager.getDao(UserInfoDao.class);
    }



    /**
     * @return get method for the field daoManager
     */
    public DaoManager getDaoManager() {

        return this.daoManager;
    }

    /**
     * @param daoManager
     *            the daoManager to set
     */
    public void setDaoManager(DaoManager daoManager) {

        this.daoManager = daoManager;
    }

}
