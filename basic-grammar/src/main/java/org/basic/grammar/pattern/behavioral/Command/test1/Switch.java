/**
 * Copyright reserved by Tellabs Communication Corp. LTD. The file
 * org.basic.pattern.Command.test1.Switch.java is created on 2008-6-12
 */
package org.basic.grammar.pattern.behavioral.Command.test1;

/**
 * 
 */
public class Switch {

    private Command upCommand, downCommand;

    public Switch(Command Up, Command Down) {

        upCommand = Up;
        downCommand = Down;
    }

    void flipUp() {

        upCommand.execute();
    }

    void flipDown() {

        downCommand.execute();
    }
}
