package org.basic.jdk.core.pattern.behavioral.Mediator.test1;
/**
 *  An abstract Mediator
 */
public interface Mediator  {
    public void register(Colleague c, String type);
    public void changed(String type);
}