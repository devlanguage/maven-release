package org.basic.grammar.pattern.behavioral.State;

/**
 * <pre>
 * 首先创建一个状态接口来定义一个方法，此方法需要被不同的具体状态类以及环境类实现。
 * 
 * </pre>
 */
public abstract interface TVState {
    public void doAction();

}