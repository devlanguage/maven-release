/**
 * Copyright reserved by Tellabs Communication Corp. LTD. The file
 * org.basic.pattern.Facade.src.test1.CarFacade.java is created on 2008-6-12
 */
package org.basic.grammar.pattern.structural.Facade.test1;

/**
 * 
 */
public class CarFacade {

    Body body = new Body();

    public void run() {

        System.out.println(body.engine.EngineWork());

        for (int i = 0; i < body.wheels.length; i++) {

            System.out.println(body.wheels[i].WheelCircumrotate());

        }
    }

    public void stop() {

        System.out.println(body.engine.EngineStop());

        for (int i = 0; i < body.wheels.length; i++) {
            System.out.println(body.wheels[i].WheelStop());
        }
    }
}
