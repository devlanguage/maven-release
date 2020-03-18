package org.basic.jdk.core.pattern.behavioral.Iterator.test1;
/**
 *  A vector iterator to print data reverse
 */
import java.util.Vector;

public class VectorIterator implements Iterator {
    private Vector data = new Vector();
    private int cursor = 0;

    public VectorIterator(Vector _data) {
        data = _data;
    }    
    public void first() {
        //cursor = 0;
        cursor = (data.size() - 1);
    }
    public void next() {
        //cursor++;
        cursor--;
    }
    public boolean isDone() {
        //return (cursor >= data.size());
        return (cursor < 0);
    }
    public void currentItem() {
        if(isDone()) {
            System.out.println("Reach the end of the vector");
        } else {
            System.out.println("Number " + cursor + ": " + data.get(cursor));
        }
    }
}