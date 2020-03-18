package org.basic.jdk.core.pattern.creational.abstract_factory.test1.factory;

import org.basic.jdk.core.pattern.creational.abstract_factory.test1.product.Door;
import org.basic.jdk.core.pattern.creational.abstract_factory.test1.product.Wall;

/*
 * AbstractFactory
 */
public abstract class RoomAbstractFactory {

    public abstract Wall makeWall();

    public abstract Door makeDoor();
}