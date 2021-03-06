package org.third.cxf.service.teacher.binding;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created on Aug 19, 2014, 2:08:38 PM
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class PageData<T> extends BaseRespObj {
	@XmlElement
	private PageRespObj pageinfo;

	@XmlElementWrapper(name = "pageData")
	public List<T> data;

	public PageRespObj getPageinfo() {
		return pageinfo;
	}

	public void setPageinfo(PageRespObj pageinfo) {
		this.pageinfo = pageinfo;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}
}