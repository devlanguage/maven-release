/**
 * Copyright reserved by Tellabs Communication Corp. LTD. The file
 * org.basic.pattern.Command.test1.LightOnCommand.java is created on 2008-6-12
 */
package org.basic.pattern.behavioral.Command.test1;

/**
 * 
 */
public class LightOnCommand implements Command {

    private Light myLight;

    public LightOnCommand(Light L) {

        myLight = L;
    }

    public void execute() {

        myLight.turnOn();
    }
}