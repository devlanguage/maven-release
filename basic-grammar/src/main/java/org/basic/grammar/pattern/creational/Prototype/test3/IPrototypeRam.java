/**
 * Copyright reserved by Tellabs Communication Corp. LTD. The file
 * org.basic.pattern.创建型.Prototype.test3.IPrototypeRam.java is created on 2008-8-21
 */
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
