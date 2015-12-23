package org.basic.pattern.structural.Decorator.test2;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2006-6-29
 * Time: 15:05:22
 * 类功能说明:
 *     打方形桩实现类
 */
public class SquarePeg implements Work {

    public void insert(){
        System.out.println("打入方形木桩");
    }
}