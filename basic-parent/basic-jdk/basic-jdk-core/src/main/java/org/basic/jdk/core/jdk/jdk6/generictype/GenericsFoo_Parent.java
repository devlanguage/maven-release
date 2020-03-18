package org.basic.jdk.core.jdk.jdk6.generictype;

import java.io.Serializable;

public class GenericsFoo_Parent<T extends Object & Serializable> {

    private T x;

    public T getX() {

        return x;
    }

    public void setX(T x) {

        this.x = x;
    }
}
