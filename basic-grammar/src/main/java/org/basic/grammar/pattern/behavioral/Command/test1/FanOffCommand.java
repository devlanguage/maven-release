/**
 * Copyright reserved by Tellabs Communication Corp. LTD. The file
 * org.basic.pattern.Command.test1.FanOffCommand.java is created on 2008-6-12
 */
package org.basic.grammar.pattern.behavioral.Command.test1;

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