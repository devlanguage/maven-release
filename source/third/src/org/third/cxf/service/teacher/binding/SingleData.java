package org.third.cxf.service.teacher.binding;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created on Aug 19, 2014, 2:07:29 PM
 */
@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class SingleData<RealObject> extends BaseRespObj {

    @XmlElement
    private RealObject data;

    public RealObject getData() {
        return data;
    }

    public void setData(RealObject data) {
        this.data = data;
    }

}