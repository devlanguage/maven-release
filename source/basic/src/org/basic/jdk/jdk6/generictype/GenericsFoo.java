package org.basic.jdk.jdk6.generictype;

import java.io.Serializable;

public class GenericsFoo<T extends Object & Serializable> extends GenericsFoo_Parent<T> {

    private T x;

    public T getX() {

        return x;
    }

    public void setX(T x) {

        this.x = x;
    }
}
