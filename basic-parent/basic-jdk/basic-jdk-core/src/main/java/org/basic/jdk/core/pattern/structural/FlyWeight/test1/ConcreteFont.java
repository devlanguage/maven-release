package org.basic.jdk.core.pattern.structural.FlyWeight.test1;

/**
 * A shared ConcreteFlyweight
 */

public class ConcreteFont implements Font {

    private String color;
    private int size;
    private String str;

    public ConcreteFont(String s) {

        str = s;
        // id = "The char is: " + s;
    }

    public void setFont(String _color, int _size) {

        color = _color;
        size = _size;
    }

    public void getFont() {

        System.out.println("String :" + str + "--- color is:" + color + "--- size is:" + size);
    }

    @Override
    public String toString() {

        return new StringBuffer("Font:[name=").append(str).append(", size=").append(size).append(
                ", color=").append(color).append("]").toString();
    }
}