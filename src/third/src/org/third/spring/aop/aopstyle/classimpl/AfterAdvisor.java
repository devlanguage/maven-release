
import java.lang.reflect.Method;

import org.springframework.aop.AfterAdvice;

public class AfterAdvisor implements AfterAdvice {

    public void after(Object obj, Method method, Object[] arg1, Object arg2) throws Throwable {

        String type = (String) arg1[0];
        System.out.println("Hello Thankyou to bye " + type);
    }

}
