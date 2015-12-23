/**
 * Copyright reserved by Tellabs Communication Corp. LTD. The file
 * org.basic.pattern.Command.test1.FanOnCommand.java is created on 2008-6-12
 */
package org.basic.pattern.behavioral.Command.test1;

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
