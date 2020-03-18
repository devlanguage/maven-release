package org.basic.jdk.core.pattern.behavioral.Command.test1;

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
