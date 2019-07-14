/**
 * Copyright reserved by Tellabs Communication Corp. LTD. The file
 * org.basic.pattern.Facade.src.test1.Body.java is created on 2008-6-12
 */
package org.basic.grammar.pattern.structural.Facade.test1;

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
