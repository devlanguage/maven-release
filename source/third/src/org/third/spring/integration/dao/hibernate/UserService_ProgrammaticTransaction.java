
import java.util.List;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.third.testdata.user.domain.UserDM;



public class UserService_ProgrammaticTransaction implements UserService {

    private TransactionTemplate transactionTemplate;
    private UserDao_NoSpringHibernatePlugin userDao;

    public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }

    public void setUserDao(UserDao_NoSpringHibernatePlugin userDao) {
        this.userDao = userDao;
    }

    public void createUser(final UserDM user) {
        this.transactionTemplate.execute(new TransactionCallbackWithoutResult() {

            public void doInTransactionWithoutResult(TransactionStatus status) {
                userDao.testTransaction(user);
            }
        });

    }

}
