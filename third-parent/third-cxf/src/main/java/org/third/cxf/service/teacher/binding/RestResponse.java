package org.third.cxf.service.teacher.binding;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created on Sep 28, 2014, 1:19:52 PM
 */
@XmlRootElement(name = "Response")
@XmlAccessorType(XmlAccessType.FIELD)
public class RestResponse {

    @XmlElement(name = "code")
    private RespCode respCode;
    private BaseRespObj rows;

    /**
     * @return the respCode
     */
    public RespCode getRespCode() {
        return respCode;
    }

    /**
     * @param respCode
     *            the respCode to set
     */
    public void setRespCode(RespCode respCode) {
        this.respCode = respCode;
    }

    /**
     * @return the data
     */
    public BaseRespObj getData() {
        return rows;
    }

    /**
     * @param data
     *            the data to set
     */
    public void setData(BaseRespObj data) {
        this.rows = data;
    }

    public RestResponse() {
        this.setCode(RespCode.NODEFINE);
    }

    /**
     * @param datas
     */
    public RestResponse(BaseRespObj datas) {
        this.setCode(RespCode.NODEFINE);
        this.rows = datas;
    }

    public void setCode(RespCode retdCodeType) {
        this.respCode = retdCodeType;
    }

    public int getCode() {
        return respCode.getCode();
    }
}
