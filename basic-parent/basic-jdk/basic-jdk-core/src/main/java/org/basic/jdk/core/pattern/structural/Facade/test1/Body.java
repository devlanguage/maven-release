package org.basic.jdk.core.pattern.structural.Facade.test1;

/**
 * 
 */
public class Body {

    public Wheel[] wheels = new Wheel[4];// 实例化Wheel
    public Engine engine = new Engine();// 实例化engine

    public Body() {

        for (int i = 0; i < wheels.length; i++) {

            wheels[i] = new Wheel();

        }

    }
}
