package org.basic.jdk.core.pattern.behavioral.Mediator.test1;

/**
 * An abstract colleague
 */
public interface Colleague {

    public final static String COLLEAGUE_TYPE_A = "A";
    public final static String COLLEAGUE_TYPE_B = "B";
    public final static String COLLEAGUE_TYPE_C = "C";

    public void change();

    public void action();
}