package org.basic.grammar.pattern.structural.FlyWeight.test1;

/**
 * A Flyweight Factory
 */
import java.util.*;

public class FontFactory {

    private Hashtable<String, Font> charHashTable = new Hashtable<String, Font>();

    public FontFactory() {

    }

    public Font getFlyWeight(String s) {

        if (charHashTable.get(s) != null) {
            return charHashTable.get(s);
        } else {
            Font tmp = new ConcreteFont(s);
            charHashTable.put(s, tmp);
            return tmp;
        }
    }

    public Hashtable getFactory() {

        return charHashTable;
    }
}