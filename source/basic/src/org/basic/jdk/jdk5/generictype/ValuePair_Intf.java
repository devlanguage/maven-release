package org.basic.jdk.jdk5.generictype;

public interface ValuePair_Intf<A, B> {

    public A getA();

    public B getB();

    public <T> A genericMethod(T v);

    public String toString();
}