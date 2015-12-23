package org.third.spring.config;

import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

public class CommandManager implements BeanFactoryAware {

    private BeanFactory beanFactory;

    public Object process(Map commandState) {

        // grab a new instance of the appropriate Command
        Command command = createCommand();
        // set the state on the (hopefully brand new) Command instance
        command.setState(commandState);
        return command.execute();
    }

    // the Command returned here could be an implementation that executes asynchronously, or
    // whatever
    protected Command createCommand() {

        return (Command) this.beanFactory.getBean("command"); // notice the Spring API dependency
    }

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {

        this.beanFactory = beanFactory;
    }
}