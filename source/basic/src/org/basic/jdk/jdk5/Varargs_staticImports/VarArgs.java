package org.basic.jdk.jdk5.Varargs_staticImports;

import static org.basic.jdk.jdk6.enums_foreach_autoboxing.Color_Enum.BLUE;
import static org.basic.jdk.jdk6.enums_foreach_autoboxing.Color_Enum.GREEN;
import static org.basic.jdk.jdk6.enums_foreach_autoboxing.Color_Enum.ORANGE;
import static org.basic.jdk.jdk6.enums_foreach_autoboxing.Color_Enum.RED;

import java.util.EnumMap;
import java.util.EnumSet;

import org.basic.jdk.jdk6.enums_foreach_autoboxing.Color_Enum;

public class VarArgs {

    public final static void printColors(Color_Enum... colors) {
        EnumSet<Color_Enum> colorSet = EnumSet.noneOf(Color_Enum.class);
        colorSet.addAll(colorSet);

        EnumMap<Color_Enum, String> colorMap = new EnumMap<Color_Enum, String>(Color_Enum.class);
        colorMap.put(BLUE, "blue_value");
        colorMap.put(ORANGE, "orange_value");

        for (Color_Enum color : colors) {

            System.out.println(color);
        }
    }

    public static void main(String[] args) {
        printColors(RED, BLUE, GREEN, ORANGE);
    }
}
