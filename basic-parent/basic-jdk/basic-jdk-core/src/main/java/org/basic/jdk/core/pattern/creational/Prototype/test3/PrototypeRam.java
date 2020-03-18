package org.basic.jdk.core.pattern.creational.Prototype.test3;

/**
 * * *
 * <p>
 * Title:design in java
 * </p> * *
 * <p>
 * Description: 用Prototype来创建一批clone产品
 * </p> *
 * <p>
 * 有关clone的知识请参考另一个blog
 * </p> * *
 * <p>
 * Copyright: Copyright (c) 2005
 * </p> * *
 * <p>
 * Company: www.hhzskj.com
 * </p> * *
 * 
 * @author meconsea *
 * @version 1.0
 */

public class PrototypeRam implements IPrototypeRam {

    private String name;

    public String getName() {

        return this.name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public PrototypeRam() {

        this.name = " this is propotype!";
    }

    /**
     * * 覆写clone方法 *
     * 
     * @return Object
     */
    public Object clone() {

        Object o = null;
        try {
            o = super.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println(" PrototypeRam is not cloneable");
        }
        return o;
    }
}
