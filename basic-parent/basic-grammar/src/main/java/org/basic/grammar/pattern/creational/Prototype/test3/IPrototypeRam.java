package org.basic.grammar.pattern.creational.Prototype.test3;

import java.io.Serializable;

public interface IPrototypeRam extends Cloneable, Serializable {

    /**
     * * 用于bean *
     * 
     * @return String
     */
    public String getName();

    /**
     * * 用于bean *
     * 
     * @param name
     *            String
     */

    public void setName(String name);

}
