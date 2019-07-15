
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.third.testdata.user.service.UserService;


public class AopTester {

    public static void main(String[] args) {

        new AopTester().test();
    }

    public void test() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext(
                new String[] { "/org/third/spring/aop/aopstyle/classimpl/spring_aop.xml" });
        BeanFactory bf = ctx;

        UserService userService = (UserService) ctx.getBean("userService");
        userService.createUser("userName=test1");

        UserService userServcieWithAdvice = (UserService) ctx.getBean("userServcieWithAdvice");
        userServcieWithAdvice.createUser("userName=test1");
        
    }
}
