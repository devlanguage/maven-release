
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class AroundAdvisor implements MethodInterceptor {

    public Object invoke(MethodInvocation invocation) throws Throwable {
        String str = (String) invocation.getArguments()[0];
        System.out.println("this is before" + str + " in MethodInterceptor");
        Object obj = invocation.proceed(); // ����Ŀ�귽�����粻���ã�Ŀ�귽��������ִ��
        System.out.println("this is after" + str + " in MethodInterceptor");
        System.out.println("returned value: " + obj);
        return null;
    }

}
