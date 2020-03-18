package org.basic.jdk.core.pattern.creational.abstract_factory.test1.product;
/*
 * A concrete Door for Bed Room
 */
public class BedRoomDoor extends Door {
    private String doorName;
    public BedRoomDoor() {
        doorName = "BedRoomDoor";
    }
    public String getName() {
        return doorName;
    }
}