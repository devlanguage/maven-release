package org.basic.jdk.core.pattern.creational.Prototype.test1;

/*
 * An Abstract Graphic Class ( Prototype )
 */

public abstract class Graphic implements IGraphic {

    private String name;

    public Object clone() {

        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println("Do not support clone !!!");
            throw new InternalError();
        }
    }

    public String getName() {

        return name;
    }

    public void setName(String gName) {

        name = gName;
    }

    public abstract void doSomething();
}