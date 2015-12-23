/****************************************************************************
 *                 TELLABS PROPRIETARY AND CONFIDENTIAL
 *              UNPUBLISHED WORK COPYRIGHT 2009 TELLABS
 *                          ALL RIGHTS RESERVED
 *      NO PART OF THIS DOCUMENT MAY BE USED OR REPRODUCED WITHOUT
 *                   THE WRITTEN PERMISSION OF TELLABS.
 *  Last modifed on 10:59:35 AM Sep 28, 2014
 *
 *****************************************************************************
 */
package org.third.cxf.service.teacher.dao;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created on Sep 28, 2014, 10:59:35 AM
 */
@XmlRootElement(name = "student")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "id", "name" })
public class Student implements Serializable {
    /**
     * @param id2
     * @param name2
     */
    public Student() {
    }

    public Student(long id2, String name2) {
        id = id2;
        name = name2;
    }

    private long id;
    private String name;

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

}