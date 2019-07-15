
import java.io.Reader;

import org.third.orm.ibatis.account.dao.AccountDaoLocator;
import org.third.orm.ibatis.account.dao.DaoConfiguration;
import org.third.orm.ibatis.account.dao.DaoLoader;

import com.ibatis.common.resources.Resources;
import com.ibatis.dao.client.DaoManager;
import com.ibatis.dao.client.DaoManagerBuilder;

public class IbatisDaoLoader extends DaoLoader {

    @Override
    public AccountDaoLocator getDaoLocator() {

        return super.accountDaoLocator;
    }

    @Override
    public void init(DaoConfiguration daoConfig, AccountDaoLocator locator) {


        IbatisDaoLocator daoLocator = (IbatisDaoLocator) locator;
        System.out.println("Init the IBatis DAO Implementation in ibatis style dao");
        System.out.println("Ibatis Dao Configuration: " + daoConfig.getConfigFile());
        try {
            Reader reader = Resources.getResourceAsReader(daoConfig.getConfigFile());
            DaoManager daoManager = DaoManagerBuilder.buildDaoManager(reader, null);
            daoLocator.setDaoManager(daoManager);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
