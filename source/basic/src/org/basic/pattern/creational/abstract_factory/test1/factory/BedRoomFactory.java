package org.basic.pattern.creational.abstract_factory.test1.factory;

import org.basic.pattern.creational.abstract_factory.test1.product.BedRoomDoor;
import org.basic.pattern.creational.abstract_factory.test1.product.BedRoomWall;
import org.basic.pattern.creational.abstract_factory.test1.product.Door;
import org.basic.pattern.creational.abstract_factory.test1.product.Wall;

/*
 * A concrete Room - BedRoom
 */
public class BedRoomFactory extends RoomAbstractFactory {

    public BedRoomFactory() {

        System.out.println("Initiated a bedroom factory!");
    }

    public Door makeDoor() {

        return new BedRoomDoor();
    }

    public Wall makeWall() {

        return new BedRoomWall();
    }
}