/****************************************************************************
 *                 TELLABS PROPRIETARY AND CONFIDENTIAL
 *              UNPUBLISHED WORK COPYRIGHT 2009 TELLABS
 *                          ALL RIGHTS RESERVED
 *      NO PART OF THIS DOCUMENT MAY BE USED OR REPRODUCED WITHOUT
 *                   THE WRITTEN PERMISSION OF TELLABS.
 *  Last modifed on 2:06:55 PM Aug 19, 2014
 *
 *****************************************************************************
 */
package org.third.cxf.service.teacher.binding;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created on Aug 19, 2014, 2:06:55 PM
 */
@XmlRootElement  
@XmlAccessorType(XmlAccessType.FIELD)  
public class SimpleData<T> extends BaseRespObj{  
      
    @XmlElement  
    private T result;  
  
    public T getResult() {  
        return result;  
    }  
  
    public void setResult(T result) {  
        this.result = result;  
    }  
}  