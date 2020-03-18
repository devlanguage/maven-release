package org.basic.jdk.core.pattern.structural.Facade.test1;

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
