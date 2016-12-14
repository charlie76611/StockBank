package com.charlie.resource.commons;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class QueryConditionModel<T> implements Serializable {

	public static final int DEFAULT_PAGESIZE = 10;
	public static final int PAGENO_QUERY_ALL = 0;
	public static final String SORT_TYPE_ASC = " ASC ";
	public static final String SORT_TYPE_DESC = " DESC ";

	private String sortname = "sysid";
	private String ordertype = SORT_TYPE_ASC;
	private int pageno = 1;
	private int startidx = 0;
	private int pagesize = DEFAULT_PAGESIZE;
	private int totalCount = 0;
	private int totalPage = 0;
	private String isQuery = "N";

	private List<ConditionModel> conditions;
	private T model;
	private List<T> models;

	@Override
	public String toString() {
		return "QueryConditionModel [sortname=" + sortname + ", ordertype=" + ordertype + ", pageno=" + pageno + ", startidx=" + startidx
				+ ", pagesize=" + pagesize + ", totalCount=" + totalCount + ", totalPage=" + totalPage + ", isQuery=" + isQuery
				+ ", conditions=" + conditions + ", model=" + model + ", models=" + models + "]";
	}

	public static QueryConditionModel<?> createQueryAllConditionModel() {
		QueryConditionModel<?> result = new QueryConditionModel<Object>();
		result.setPageno(PAGENO_QUERY_ALL);
		return result;
	}

	public String getSortname() {
		return sortname;
	}

	public void setSortname(String sortname) {
		this.sortname = sortname;
	}

	public String getOrdertype() {
		return ordertype;
	}

	public void setOrdertype(String ordertype) {
		this.ordertype = ordertype;
	}

	public int getPageno() {
		return pageno;
	}

	public void setPageno(int pageno) {
		this.pageno = pageno;
	}

	public int getStartidx() {
		this.startidx = (pageno - 1) * pagesize;
		return startidx;
	}

	// public void setStartidx(int startidx) {
	// this.startidx = startidx;
	// }
	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

	public void addCondition(ConditionModel condition) {
		if (conditions == null) {
			conditions = new ArrayList<ConditionModel>();
		}
		conditions.add(condition);
	}

	public List<ConditionModel> getConditions() {
		if (conditions == null) {
			conditions = new ArrayList<ConditionModel>();
		}
		return conditions;
	}

	public void setConditions(List<ConditionModel> conditions) {
		this.conditions = conditions;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public String getIsQuery() {
		return isQuery;
	}

	public void setIsQuery(String isQuery) {
		this.isQuery = isQuery;
	}

	public T getModel() {
		return model;
	}

	public void setModel(T model) {
		this.model = model;
	}

	public List<T> getModels() {
		return models;
	}

	public void setModels(List<T> models) {
		this.models = models;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

}
