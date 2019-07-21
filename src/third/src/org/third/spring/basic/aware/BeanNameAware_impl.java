
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class BeanNameAware_impl implements BeanNameAware {

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.beans.factory.BeanNameAware#setBeanName(java.lang.String)
     */
    public void setBeanName(String arg0) {
        System.out.println("BeanNameAware_impl: setBeanName(" + arg0 + ")");

    }
}