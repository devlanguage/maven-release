package org.basic.jdk.core.pattern.behavioral.State;

/**
 * <pre>
 * 使用状态设计模式的优势就是实现多态性的过程是清晰可见的。
 * 状态的改变中产生的错误也较少，另外增加更多的状态以及行为变得容易且更具鲁棒性。
 * 此外状态模式也帮助避免if-else子句或者switch-case条件判定逻辑。
 * 
 * 状态模式类似于策略模式，请看Java中的策略模式。这就是全部的状态设计模式，希望你喜欢上它了。
 * 
 *  ，State模式下的多种状态对客户来说是透明的，
 * 而Strategy模式的不同算法通常对客户是不透明的。
 * 
 * 当对象的内部状态变迁，是由其自身决定的，而不是由客户决定的（或者客户并不知情），同时对象在不同状态下的行为也不一样时，则最好使用State模式；
 * 如果对象自身并不保存什么状态，而是由客户根据不同的情况去选择不同的算法，则最好使用Strategy模式。
 * 
 * 这样不同的算法对于客户来说都是可知的，并可以进行选择
 * 
 *  1. State模式的状态是可穷举的，一般数量不多，在Context初始化时就全部装载了，并在运行时产生变迁；
 *     Strategy模式的算法也是在Context初始化时装载的，但一般只装载一种算法，且在运行时不变话；
 *  2. State模式的ConcreteState有可能包含实例自身的状态信息，这种情况下State不能共享；如果State不包含实例自身的状态信息，则可以共享State（使用Flyweight模式）；
 *     Strategy模式的ConcreteStrategy一般不包含实例自身的状态信息，可以共享；
 *  3. State模式与Strategy模式将不同的分支情况，放入不同的类。虽然会增加程序的类数量，但如果处理得当，则可以更加清晰地表明代码意图，使新分支情况的增加更加容易。
 * 
 * </pre>
 */
public class TVState_Test {
    public static void main(String[] args) {
        TVContext context = new TVContext();

        TVState tvStartState = new TVState_Open();
        TVState tvStopState = new TVState_Off();

        context.setState(tvStartState);
        context.doAction();

        context.setState(tvStopState);
        context.doAction();

    }

}
