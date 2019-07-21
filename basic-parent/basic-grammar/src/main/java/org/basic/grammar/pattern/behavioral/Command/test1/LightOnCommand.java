package org.basic.grammar.pattern.behavioral.Command.test1;

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