/**
 * Copyright reserved by Tellabs Communication Corp. LTD. The file
 * org.basic.pattern.Command.test1.LightOffCommand.java is created on 2008-6-12
 */
package org.basic.grammar.pattern.behavioral.Command.test1;

/**
 * 
 */
public class LightOffCommand implements Command {

    private Light myLight;

    public LightOffCommand(Light L) {

        myLight = L;
    }

    public void execute() {

        myLight.turnOff();
    }
}