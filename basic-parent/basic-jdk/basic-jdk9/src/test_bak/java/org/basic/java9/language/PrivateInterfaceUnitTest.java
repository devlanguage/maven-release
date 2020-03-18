package org.basic.java9.language;

import org.basic.java9.language.PrivateInterface;
import org.junit.Test;

public class PrivateInterfaceUnitTest {

    @Test
    public void test() {
        PrivateInterface piClass = new PrivateInterface() {
        };
        piClass.check();
    }

}
