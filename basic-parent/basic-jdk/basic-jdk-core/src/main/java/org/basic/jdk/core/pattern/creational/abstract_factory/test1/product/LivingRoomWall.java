package org.basic.jdk.core.pattern.creational.abstract_factory.test1.product;


/*
 * A concrete Wall for Living Room
 */
public class LivingRoomWall  extends Wall {
    private String wallName;
    public LivingRoomWall() {
        wallName = "LivingRoomWall";
    }
    public String getName() {
        return wallName;
    }
}