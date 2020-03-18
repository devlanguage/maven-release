package org.basic.jdk.core.pattern.creational.Prototype.test1;

/*
 * A Graphic Interface ( A prototype interface )
 */

public interface IGraphic extends Cloneable {

    public String getName();

    public void setName(String gName);
}