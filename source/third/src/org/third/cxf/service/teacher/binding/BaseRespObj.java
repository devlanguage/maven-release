package org.third.cxf.service.teacher.binding;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * Created on Aug 19, 2014, 2:06:30 PM
 */
@XmlRootElement(name = "base")
@XmlSeeAlso(value = { ListData.class, SimpleData.class, PageData.class, SingleData.class })
public class BaseRespObj<T> {

}