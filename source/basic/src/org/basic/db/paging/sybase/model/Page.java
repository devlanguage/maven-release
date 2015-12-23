package org.basic.db.paging.sybase.model;

import java.util.List;

public class Page  {


	private static final Integer DEFAULT_PAGE_SIZE = 15;

	private List queryList;

	private String hql;

	/* 从多少行开始 */
	private Integer start;
	/* 每页多少行 */
	private Integer limit;
	/* 查询出的集合 */
	private List data;
	/* 查询出的总条数 */
	private Integer totalCount;
	/*针对转换Json串的对象中需要过滤字段的名称集合*/
	
	private List<String> nameListObj;
	public Page() {

	}

	public Page(List data, Integer totalCount) {
		this.data = data;
		this.totalCount = totalCount;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLimit() {
		if (this.limit == 0) {
			return DEFAULT_PAGE_SIZE;
		} else {
			return limit;
		}
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public List getData() {
		return data;
	}

	public void setData(List data) {
		this.data = data;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public List getQueryList() {
		return queryList;
	}

	public void setQueryList(List queryList) {
		this.queryList = queryList;
	}

	public String getHql() {
		return hql;
	}

	public void setHql(String hql) {
		this.hql = hql;
	}

}
