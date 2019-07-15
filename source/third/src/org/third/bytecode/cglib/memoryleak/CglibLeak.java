
import java.lang.reflect.Method;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import net.sf.cglib.proxy.NoOp;

/**
 * 步骤方法拦截器.<br>
 * 
 */
public class CglibLeak {

    public <T> T newProxyInstance(Class<T> clazz) {
        return newProxyInstance(clazz, new MyInterceptor(), new MyFilter());
    }

    /**
     * 创建一个类动态代理.
     * 
     * @param <T>
     * @param superclass
     * @param methodCb
     * @param callbackFilter
     * @return
     */
    public static <T> T newProxyInstance(Class<T> superclass, Callback methodCb, CallbackFilter callbackFilter) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(superclass);
        enhancer.setCallbacks(new Callback[] { methodCb, NoOp.INSTANCE });
        enhancer.setCallbackFilter(callbackFilter);

        return (T) enhancer.create();
    }

    /**
     * 实现MethodInterceptor接口
     * 
     * @author l
     * 
     */
    class MyInterceptor implements MethodInterceptor {
        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
            return null;
        }
    }

    /**
     * 实现CallbackFilter接口
     * 
     * @author l
     */
    class MyFilter implements CallbackFilter {
        @Override
        public int accept(Method method) {
            // Do some thing
            return 1;
        }
    }

    /**
     * 测试代码
     * 
     * @param args
     * @throws InterruptedException
     */
    public static void main(String args[]) throws InterruptedException {
        CglibLeak leak = new CglibLeak();
        int count = 0;
        while (true) {
            leak.newProxyInstance(Object.class); // 为了测试缩写
            Thread.sleep(100);
            System.out.println(count++);
        }
    }
}
