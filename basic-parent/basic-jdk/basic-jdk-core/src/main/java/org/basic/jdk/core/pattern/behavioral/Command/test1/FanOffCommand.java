package org.basic.jdk.core.pattern.behavioral.Command.test1;

/**
 * 
 */
public class FanOffCommand implements Command {

    private Fan myFan;

    public FanOffCommand(Fan F) {

        myFan = F;
    }

    public void execute() {

        myFan.stopRotate();
    }
}