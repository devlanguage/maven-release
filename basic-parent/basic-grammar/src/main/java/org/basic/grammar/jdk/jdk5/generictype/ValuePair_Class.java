package org.basic.grammar.jdk.jdk5.generictype;

public class ValuePair_Class<A, B> implements ValuePair_Intf<A, B> {

    public final A first;
    public final B second;

    public ValuePair_Class(A a, B b) {

        first = a;
        second = b;
    }

    public <T> A genericMethod(T v) {

        String str = v.getClass().getName() + "=" + v.toString();
        System.out.println(str);

        return first;
    }

    public A getA() {

        return first;
    }

    public B getB() {

        return second;
    }

    public String toString() {

        return "(" + first + ", " + second + ")";
    }
}
