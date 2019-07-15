package org.basic.grammar.pattern.structural.proxy.statck;

import java.util.ArrayList;

/**
 * 
 */
public class StatckTwo {

    private static ArrayList al;

    public StatckTwo() {

        al = new ArrayList();

    }

    public void put(Object o) {

        if (al != null)
            al.add(o);
        else
            System.out.println("没有初始化");
    }

    public Object top() {

        int size = al.size();
        if (al != null) {
            if (size != 0) {
                System.out.println("StatckTwo");
                return al.get(size - 1);
            } else {
                TwoStatckToOneQueue so = new TwoStatckToOneQueue();
                return so.top();
            }
        } else
            System.out.println("没有初始化");

        return null;

    }

}
