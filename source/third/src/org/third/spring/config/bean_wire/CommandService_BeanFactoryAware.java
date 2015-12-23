package org.third.spring.config.bean_wire;

import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.InitializingBean;

/**
 */
public class CommandService_BeanFactoryAware implements BeanFactoryAware {

    private BeanFactory beanFactory;

    public Object businessMethod(Map commandState) {
        System.out.println(this.getClass().getSimpleName() + ".businessMethod(Map commandState() is called");

        // grab a new instance of the appropriate Command
        Command command = createCommand();
        // set the state on the (hopefully brand new) Command instance
        command.setState(commandState);
        return command.execute();
    }

    // the Command returned here could be an implementation that executes
    // asynchronously, or whatever
    protected Command createCommand() {
        // notice the Spring API dependency
        return (Command) this.beanFactory.getBean("command");
    }

    /**
     * Callback that supplies the owning factory to a bean instance.
     * <p>
     * Invoked after the population of normal bean properties but before an initialization callback such as
     * {@link InitializingBean#afterPropertiesSet()} or a custom init-method.
     * 
     * @param beanFactory
     *            owning BeanFactory (never <code>null</code>). The bean can immediately call methods on the factory.
     * @throws BeansException
     *             in case of initialization errors
     * @see BeanInitializationException
     */
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {

        this.beanFactory = beanFactory;
    }
}