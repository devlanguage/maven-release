package org.basic.grammar.pattern.behavioral.Iterator.test1;
/**
 *  The interface to create concrete iterator
 *  When create iterator, we can use Factory Method pattern
 */
public interface Aggregate  {
    public Iterator createIterator();
}