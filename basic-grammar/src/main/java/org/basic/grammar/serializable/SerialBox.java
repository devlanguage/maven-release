package org.basic.grammar.serializable;

import java.io.Serializable;

public class SerialBox implements Serializable {
    private int width;
    private int height;
    private transient String name = "serialBox";

    public SerialBox(String name, int width, int height) {
        this.name = name;
        this.width = width;
        this.height = height;
    }


    @Override
    public String toString() {
        return "[" + name + ": (" + width + ", " + height + ") ]";
    }
}
