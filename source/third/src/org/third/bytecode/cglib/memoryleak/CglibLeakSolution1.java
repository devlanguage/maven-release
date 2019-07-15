
import java.lang.reflect.Method;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import net.sf.cglib.proxy.NoOp;

/**
 *Make CallbackFilter static Class
 */
public class CglibLeakSolution1 {


    public static <T> T newProxyInstance(Class<T> clazz) {
        return newProxyInstance(clazz, myInterceptor, myFilter);
    }
    
    private static MethodInterceptor myInterceptor = new MethodInterceptor() {
        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
            // do some things
            return null;
        }
    };
    // 创建实例
    private static CallbackFilter myFilter = new MyFilter();
    static class MyFilter implements CallbackFilter {
        @Override
        public int accept(Method method) {
            // Do some thing
            return 1;
        }
    }
    private static <T> T newProxyInstance(Class<T> superclass, Callback methodCb, CallbackFilter callbackFilter) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(superclass);
        enhancer.setCallbacks(new Callback[] { methodCb, NoOp.INSTANCE });
        enhancer.setCallbackFilter(callbackFilter);

        return (T) enhancer.create();
    }

    /**
     * 测试代码
     * 
     * @param args
     * @throws InterruptedException
     */
    public static void main(String args[]) throws InterruptedException {
        int count = 0;
        while (true) {
            newProxyInstance(Object.class); // 为了测试缩写
            Thread.sleep(100);
            System.out.println(count++);
        }
    }
}
