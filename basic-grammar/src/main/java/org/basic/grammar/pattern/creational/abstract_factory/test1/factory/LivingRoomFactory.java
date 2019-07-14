package org.basic.grammar.pattern.creational.abstract_factory.test1.factory;

import org.basic.grammar.pattern.creational.abstract_factory.test1.product.Door;
import org.basic.grammar.pattern.creational.abstract_factory.test1.product.LivingRoomDoor;
import org.basic.grammar.pattern.creational.abstract_factory.test1.product.LivingRoomWall;
import org.basic.grammar.pattern.creational.abstract_factory.test1.product.Wall;

/*
 * A concrete Room - LivingRoom
 */
public class LivingRoomFactory extends RoomAbstractFactory {

    public LivingRoomFactory() {

        System.out.println("Initiated a living room factory!");
    }

    public Door makeDoor() {

        return new LivingRoomDoor();
    }

    public Wall makeWall() {

        return new LivingRoomWall();
    }
}