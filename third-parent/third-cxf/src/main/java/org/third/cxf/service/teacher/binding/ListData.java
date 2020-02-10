package org.third.cxf.service.teacher.binding;

import java.util.ArrayList;
import java.util.Collection;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import org.third.cxf.service.teacher.dao.Teacher;

/**
 * Created on Aug 19, 2014, 2:07:58 PM
 */
@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
@XmlSeeAlso(value = { Teacher.class })
public class ListData<T> extends BaseRespObj {

    @XmlElement(name = "row")
    private Collection<T> data;

    public Collection<T> getData() {
        if (data == null) {
            data = new ArrayList<T>();
        }
        return data;
    }

    public void setData(Collection<T> data) {
        this.data = data;
    }

}