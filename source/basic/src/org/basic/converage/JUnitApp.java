package org.basic.converage;

import junit.framework.TestCase;

public class JUnitApp extends TestCase {

    public void testA1() {

    }

    public void testA2() {
        int i =1;
        if (i > 0) {
            System.out.println("Positive: i=" + i);
        } else if (i == 0) {
            System.out.println("Zero: i=" + i);
        } else {
            System.out.println("Negative: i=" + i);
        }
    }
}
