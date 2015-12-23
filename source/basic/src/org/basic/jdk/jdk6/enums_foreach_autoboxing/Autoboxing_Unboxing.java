package org.basic.jdk.jdk6.enums_foreach_autoboxing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Set;

public class Autoboxing_Unboxing {

    /**
     * @param args
     */
    public static void main(String[] args) {
        int a = 3;
        Collection<Integer> c = new ArrayList<Integer>();
        c.add(a);// �Զ�ת����Integer.

        Integer b = new Integer(2);
        c.add(b + 2);

        for (Integer int1 : c) {
            System.out.println(int1);
        }

        Set<Color_Enum> s = Collections.synchronizedSet(EnumSet.noneOf(Color_Enum.class));
        s.add(Color_Enum.BLUE);

        EnumMap<Color_Enum, String> colorMap = new EnumMap<Color_Enum, String>(Color_Enum.class);
        colorMap.put(Color_Enum.RED, "red");
        colorMap.put(Color_Enum.ORANGE, "orange");
        colorMap.put(Color_Enum.YELLOW, "yellow");

    }

}
