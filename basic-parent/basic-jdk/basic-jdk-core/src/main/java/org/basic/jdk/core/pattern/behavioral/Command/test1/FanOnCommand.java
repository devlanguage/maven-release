package org.basic.jdk.core.pattern.behavioral.Command.test1;

/**
 * 
 */
public class FanOnCommand implements Command {

    private Fan myFan;

    public FanOnCommand(Fan F) {

        myFan = F;
    }

    public void execute() {

        myFan.startRotate();
    }
}
