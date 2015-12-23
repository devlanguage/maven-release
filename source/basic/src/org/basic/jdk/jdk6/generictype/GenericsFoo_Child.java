package org.basic.jdk.jdk6.generictype;

import java.io.Serializable;

public class GenericsFoo_Child<T, B extends Object & Serializable> {

    private T x;

    public T getX() {

        return x;
    }

    public void setX(T x) {

        this.x = x;
    }
}
