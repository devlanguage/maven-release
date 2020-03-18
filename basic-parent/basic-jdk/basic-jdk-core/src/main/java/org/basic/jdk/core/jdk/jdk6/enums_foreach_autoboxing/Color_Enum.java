package org.basic.jdk.core.jdk.jdk6.enums_foreach_autoboxing;

public enum Color_Enum {

    RED, ORANGE, YELLOW, GREEN, BLUE, INDIGO, PURPLE;

    private int id;
    private String value;

    private Color_Enum() {
        this(-1, "Green");
    }

    private Color_Enum(int id, String value) {
        this.id = id;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

}
