/****************************************************************************
 *                 TELLABS PROPRIETARY AND CONFIDENTIAL
 *              UNPUBLISHED WORK COPYRIGHT 2009 TELLABS
 *                          ALL RIGHTS RESERVED
 *      NO PART OF THIS DOCUMENT MAY BE USED OR REPRODUCED WITHOUT
 *                   THE WRITTEN PERMISSION OF TELLABS.
 *  Last modifed on 10:25:12 AM Sep 28, 2014
 *
 *****************************************************************************
 */
package org.third.cxf.service.teacher.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

import org.basic.common.util.SystemUtil;

/**
 * Created on Sep 28, 2014, 10:25:12 AM
 */

@XmlRootElement(name = "teacher")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "name", "address", "students" })
@XmlSeeAlso(Student.class)
public class Teacher implements Serializable {
    @XmlElement(name = "id", required = true)
    private long id;
    @XmlElement(name = "name", required = true)
    private String name;
    @XmlElement(name = "address", required = true)
    private String address;
    @XmlElement(name = "students", required = true)
    private List<Student> students;

    public Teacher() {
        id = SystemUtil.nextId();
        name = "Test_" + id;
        address = "ADDR_" + id;
        students = new ArrayList<Student>();
        students.add(new Student(id, name));
    }

    public Teacher(long teacherId) {
        this.id = teacherId;
        name = "Test_" + id;
        address = "ADDR_" + id;
        students = new ArrayList<Student>();
        students.add(new Student(id, name));
    }

    /**
     * @param teacherId
     * @param name2
     */
    public Teacher(long teacherId, String name2) {
        this.id = teacherId;
        this.name = name2;
    }

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

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @return the students
     */
    public List<Student> getStudents() {
        return students;
    }
}
