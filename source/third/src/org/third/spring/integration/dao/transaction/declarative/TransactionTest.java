
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.third.testdata.user.service.UserService;

public class TransactionTest {

    public static void main(String[] args) {

        new TransactionTest().test();
    }

    public void test() {

        String prefix = "/" + (this.getClass().getPackage().getName().replaceAll("\\.", "/")) + "/";
        ApplicationContext ctx = new ClassPathXmlApplicationContext(new String[] { prefix + "spring_declarative_transaction.xml" });
        BeanFactory bf = ctx;

        org.third.testdata.user.service.UserService userService = (UserService) ctx.getBean("userService");
        userService.createUser("userName=test1");

    }
}