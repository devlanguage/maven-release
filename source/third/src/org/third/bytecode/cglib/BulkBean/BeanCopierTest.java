
import java.math.BigInteger;

import net.sf.cglib.beans.BeanCopier;
import net.sf.cglib.core.DebuggingClassWriter;

public class BeanCopierTest extends CglibPerformanceTest {

    public static void main(String args[]) {
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "c:\\temp");

        final BeanCopier beanCopier = BeanCopier.create(SourceBean.class, TargetBean.class, false);
        final TargetBean beanCopierTarget = new TargetBean();// new一次，避免new对象产生的代价影响测试结果
        SourceBean soourceBean = new SourceBean();
        int testCount = 1000 * 1000;
        testTemplate(new TestCallback() {

            public String getName() {
                return "BeanCopier";
            }

            public TargetBean call(SourceBean source) {
                beanCopier.copy(source, beanCopierTarget, null);
                return beanCopierTarget;
            }
        }, soourceBean, testCount);
    }
}

class BigIntConverter implements net.sf.cglib.core.Converter {

    @Override
    public Object convert(Object value, Class target, Object context) {
        System.out.println(value.getClass() + " " + value); // from类中的value对象
        System.out.println(target); // to类中的定义的参数对象
        System.out.println(context.getClass() + " " + context); // String对象,具体的方法名
        if (target.isAssignableFrom(BigInteger.class)) {
            return new BigInteger(value.toString());
        } else {
            return value;
        }
    }

}
