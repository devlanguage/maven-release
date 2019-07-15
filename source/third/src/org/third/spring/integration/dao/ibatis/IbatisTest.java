
import java.util.List;

import org.third.spring.SpringTest;
import org.third.testdata.user.domain.UserDM;

public class IbatisTest extends SpringTest {

    public IbatisTest(String configFile) {
        super(configFile);
    }

    public static void main(String[] args) {
        // InputStream in = 
        // ClassLoader.getSystemResourceAsStream("org/springtest/integration/dao/ibatis/User.xml");

        new IbatisTest("spring_ibatis.xml").test();
    }

    /**
     * 
     */
    public void test() {

        System.out.println("===========getAllUser independent of spring ibatis plugin============");
        UserDao_Ibatis userDao01 = (UserDao_Ibatis) ctx.getBean(UserDao_Ibatis.BEAN_NAME_userDao01);
        List<UserDM> users01 = userDao01.getAllUsers();
        for (UserDM u : users01) {
            System.out.println(u);
        }

        System.out.println("===========getAllUser with the spring ibatis plugin============");
        UserDao_Ibatis userDao02 = (UserDao_Ibatis) ctx.getBean(UserDao_Ibatis.BEAN_NAME_userDao02);
        List<UserDM> users02 = userDao02.getAllUsers();
        for (UserDM u : users02) {
            System.out.println(u);
        }

        System.out.println("===========getUserById============");
        System.out.println(userDao01.getUserById(1));
    }
}