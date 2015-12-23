package org.basic.grammar;

public class BasicFunctionTest {
    int testFinally() {
        int testInt = 0;

        try {
            ++testInt;
            return testInt;
        } finally {
            ++testInt;
            testInt=2;
        }

    }

    public static void main(String[] args) {
        BasicFunctionTest t = new BasicFunctionTest();
        System.out.println("Output: i= " + t.testFinally());

    }
}
