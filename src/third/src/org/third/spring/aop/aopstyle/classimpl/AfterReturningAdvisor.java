
import java.lang.reflect.Method;

import org.springframework.aop.AfterReturningAdvice;

/**
 * ��2������֪ͨ
 * 
 * ������ǰ��֪ͨ��bean,��ִ��ҵ�񷽷�ǰ������ִ��ǰ����������afterReturnning����
 * 
 * @author emerson
 * 
 */
public class AfterReturningAdvisor implements AfterReturningAdvice {

    public void afterReturning(Object obj, Method method, Object[] arg1, Object arg2) throws Throwable {

        String type = (String) arg1[0];
        System.out.println("Hello Thankyou to bye " + type);
    }

}
