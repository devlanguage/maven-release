package org.basic.jdk.core.pattern.creational.abstract_factory.test1.product;
/*
 * A concrete Door for Living Room
 */
public class LivingRoomDoor extends Door {
    private String doorName;
    public LivingRoomDoor() {
        doorName = "LivingRoomDoor";
    }
    public String getName() {
        return doorName;
    }
}