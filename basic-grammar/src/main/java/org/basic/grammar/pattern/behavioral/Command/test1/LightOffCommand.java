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