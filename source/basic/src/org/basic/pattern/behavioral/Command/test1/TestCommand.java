package org.basic.pattern.behavioral.Command.test1;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: The9.com
 * </p>
 * 
 * @author Jerry Shen
 * @version 0.5
 */

public class TestCommand {

    public TestCommand() {

    }

    public static void main(String[] args) {

Light testLight = new Light();
LightOnCommand testLOC = new LightOnCommand(testLight);
LightOffCommand testLFC = new LightOffCommand(testLight);
Switch testSwitch = new Switch(testLOC, testLFC);
testSwitch.flipUp();
testSwitch.flipDown();

Fan testFan = new Fan();
FanOnCommand foc = new FanOnCommand(testFan);
FanOffCommand ffc = new FanOffCommand(testFan);
Switch ts = new Switch(foc, ffc);
ts.flipUp();
ts.flipDown();
    }
}
