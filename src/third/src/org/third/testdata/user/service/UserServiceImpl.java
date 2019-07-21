
import org.third.spring.SpringTestRuntimeException;
import org.third.testdata.user.dao.UserDao;
import org.third.testdata.user.domain.UserDM;

public class UserServiceImpl implements UserService {

    private UserDao userDao;

    public UserDM createUser(String userName) throws SpringTestRuntimeException {
        System.out.println("UserService: createUser");
        return userDao.createUser();
    }

    public void printUser() {
        System.out.println("UserService.printUser()");

    }

    public UserDM getUser(String UserName) throws SpringTestRuntimeException {
        // do something
        return null;
    }

    // these settings have precedence for this method
    // @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void updateUser(UserDM User) throws SpringTestRuntimeException {
        // do something

    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

}
