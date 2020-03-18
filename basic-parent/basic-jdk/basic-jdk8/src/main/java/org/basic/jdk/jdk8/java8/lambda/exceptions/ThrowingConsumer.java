package org.basic.jdk.jdk8.java8.lambda.exceptions;

@FunctionalInterface
public interface ThrowingConsumer<T, E extends Exception> {

    void accept(T t) throws E;

}