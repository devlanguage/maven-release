package org.basic.grammar.pattern.creational.abstract_factory.test1.factory;

import org.basic.grammar.pattern.creational.abstract_factory.test1.product.Door;
import org.basic.grammar.pattern.creational.abstract_factory.test1.product.Wall;

/*
 * AbstractFactory
 */
public abstract class RoomAbstractFactory {

    public abstract Wall makeWall();

    public abstract Door makeDoor();
}