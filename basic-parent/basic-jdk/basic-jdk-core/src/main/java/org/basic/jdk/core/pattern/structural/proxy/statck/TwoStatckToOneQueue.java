package org.basic.jdk.core.pattern.structural.proxy.statck;

import java.util.ArrayList;

/**
 * 
 */
public class TwoStatckToOneQueue {

    private static ArrayList al;

    public TwoStatckToOneQueue() {

        if (al == null)
            al = new ArrayList();

    }

    public void put(Object o) {

        al.add(o);
    }

    public Object top() {

        int size = al.size();
        if (al != null) {
            if (size != 0) {
                System.out.println("StatckOne");
                return al.get(size - 1);
            } else
                System.out.println("栈中没有对象");
        } else
            System.out.println("没有初始�");
        
        System.out.println("StatckOne");
        return null;
    }

}
