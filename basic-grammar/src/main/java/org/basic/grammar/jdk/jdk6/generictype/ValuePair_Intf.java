package org.basic.grammar.jdk.jdk6.generictype;

public interface ValuePair_Intf<A, B> {

    public A getA();

    public B getB();

    public <T> A genericMethod(T v);

    public String toString();
}