package org.basic.jdk.core.pattern.behavioral.Iterator.test1;

import java.io.InputStream;

/**
 * 
 */

public class TestIteratorPattern {

    private static final String DATA_FILE = "data.txt";

    public static void main(String[] args) {

        InputStream ins = TestIteratorPattern.class.getResourceAsStream(DATA_FILE);
        DataVector dataVector = new DataVector(ins);
        Iterator iVector = dataVector.createIterator();
        for (iVector.first(); !iVector.isDone(); iVector.next()) {
            iVector.currentItem();
        }
    }
}