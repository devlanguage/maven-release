/**
 * Copyright reserved by Tellabs Communication Corp. LTD. The file
 * org.basic.pattern.Facade.src.test1.TestCarFacade.java is created on 2008-6-12
 */
package org.basic.grammar.pattern.structural.Facade.test1;

/**
 * <pre>
 * Facade外观模式，是一种结构型模式，它主要解决的问题是：
 *      组件的客户和组件中各种复杂的子系统有了过多的耦合，随着外部客户程序和各子系统的演化， 这种过多的耦合面临很多变化的挑战。
 * 在这里我想举一个例子： 
 *      比如，现在有一辆汽车，我们（客户程序）要启动它，那我们就要发动引擎（子系统1），使四个车轮（子系统2）转动。
 * 但是实际中我们并不需要用手推动车轮使其转动，我们踩下油门，此时汽车再根据一些其他的操作使车轮转动。油门就好比系统给我们留下的接口，
 * 不论汽车是以何种方式转动车轮，车轮变化成什么牌子的，我们要开走汽车所要做的还是踩下油门。
 * 
 * GoF《设计模式》中说道：为子系统中的一组接口提供一个一致的界面，Facade模式定义了一个高层接口(高层抽象)，这个接口使得这一子系统更加容易使用。
 * 
 * 正如上面所说：客户端代码（Program）不需要关心子系统，它只需要关心CarFacade所留下来的和外部交互的接口，而子系统是在CarFacade中聚合。
 * 
 * 
 * 
 * Façade模式的几个要点：
 * 
 * 1、从客户程序的角度看，Facade模式不仅简化了整个组件系统的接口，同时对于组件内部与外部客户程序来说，从某种程度上也达到了一种“解耦”的效果——内部子
 *   系统的任何变化不会影响到Facade接口的变化。
 * 2、Facade设计模式更注重从架构的层次去看整个系统，而不是单个类的层次。Facade很多时候更是一种架构设计模式。
 * </pre>
 * 
 */
public class TestCarFacade {

    public static void main(String[] args) {

        CarFacade car = new CarFacade();
        car.run();
        car.stop();
    }

}