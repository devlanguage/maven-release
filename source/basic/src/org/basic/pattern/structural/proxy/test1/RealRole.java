/**
 * Copyright reserved by Tellabs Communication Corp. LTD. The file
 * org.basic.pattern.StructureType.proxy.test1.RealRole.java is created on 2008-9-16
 */
package org.basic.pattern.structural.proxy.test1;

public class RealRole extends AbstractRole {

    public RealRole() {

    }

    public void request() {

        System.out.println("From real subject.");
    }

}