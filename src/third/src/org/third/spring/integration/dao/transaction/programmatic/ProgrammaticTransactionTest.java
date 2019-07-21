
import org.third.spring.SpringTest;
import org.third.testdata.user.service.UserService;

public class ProgrammaticTransactionTest extends SpringTest {

    public ProgrammaticTransactionTest(String configFile) {
        super(configFile);
    }

    public static void main(String[] args) {

        new ProgrammaticTransactionTest("spring_programmatic_transaction.xml").test();
    }

    public void test() {
        UserService userService = (UserService) ctx.getBean(UserService.BEAN_NAME_USER_SERVICE);
        userService.createUser("ZhangSan");
    }

}
