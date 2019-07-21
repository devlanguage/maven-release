package org.basic.grammar.pattern.creational.Builder.test1;
/*
 *  This class is a Director
 */
public class HouseDirector  {
    public void CreateHouse(HouseBuilder concreteBuilder) {
        concreteBuilder.BuildRoom(1);
        concreteBuilder.BuildRoom(2);
        concreteBuilder.BuildDoor(1, 2);

        //return builder.getHouse();
    }
}