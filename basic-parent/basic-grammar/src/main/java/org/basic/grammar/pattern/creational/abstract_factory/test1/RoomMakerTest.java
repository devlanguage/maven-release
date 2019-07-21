package org.basic.grammar.pattern.creational.abstract_factory.test1;

import org.basic.grammar.pattern.creational.abstract_factory.test1.factory.BedRoomFactory;
import org.basic.grammar.pattern.creational.abstract_factory.test1.factory.LivingRoomFactory;
import org.basic.grammar.pattern.creational.abstract_factory.test1.factory.RoomAbstractFactory;
import org.basic.grammar.pattern.creational.abstract_factory.test1.product.Door;
import org.basic.grammar.pattern.creational.abstract_factory.test1.product.Wall;

/*
 * A Room Maker to test our simple Room Abstract Factory
 */
public class RoomMakerTest {

    public RoomAbstractFactory createRoomFactory(String roomType) {

        if (roomType.equals("LivingRoom")) {
            return new LivingRoomFactory();
        } else if (roomType.equals("BedRoom")) {
            return new BedRoomFactory();
        } else {
            return new LivingRoomFactory();
        }
    }

    public static void main(String[] args) {

        RoomMakerTest myMaker = new RoomMakerTest();
        // ----- Create Living Room
        RoomAbstractFactory livingRoomFactory = myMaker.createRoomFactory("LivingRoom");
        // ----- Create a door in living room
        Door livingDoor = livingRoomFactory.makeDoor();
        System.out.println("Living room door name is:" + livingDoor.getName());
        // ----- Create a wall in living room
        Wall livingWall = livingRoomFactory.makeWall();
        System.out.println("Living room wall name is:" + livingWall.getName());

        // ----- Create Bed Room
        RoomAbstractFactory bedRoomFactory = myMaker.createRoomFactory("BedRoom");
        // ----- Create a door in bedroom
        Door BedDoor = bedRoomFactory.makeDoor();
        System.out.println("Bed room door name is:" + BedDoor.getName());
        // ----- Create a wall in bedroom
        Wall BedWall = bedRoomFactory.makeWall();
        System.out.println("Bed room wall name is:" + BedWall.getName());

    }
}