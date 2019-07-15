
import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.third.testdata.user.domain.UserDM;

public class UserDao_HibernateDaoSupport extends HibernateDaoSupport implements UserDao_Hibernate {

    // this.getHibernateTemplate();
    // this.getSessionFactory();
    // this.getSession();

    public List<UserDM> getAllUsers() {

        // TODO Auto-generated method stub
        return null;
    }

    public UserDM getUserById(int id) {
        // TODO Auto-generated method stub
        return null;
    }

    public void testTransaction(UserDM user) {

    }
}
