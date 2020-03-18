package org.basic.jdk.jdk8.doublecolon.function;

import org.basic.jdk.jdk8.doublecolon.Computer;

@FunctionalInterface
public interface ComputerPredicate {

    boolean filter(Computer c);

}
