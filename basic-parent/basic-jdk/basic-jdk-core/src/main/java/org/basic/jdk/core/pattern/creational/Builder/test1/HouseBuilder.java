package org.basic.jdk.core.pattern.creational.Builder.test1;
/*
 *  An abstract Builder
 */
public abstract class HouseBuilder  {
    public abstract void BuildRoom(int roomNo);
    public abstract void BuildDoor(int room1, int room2);
    public abstract House getHouse();
}