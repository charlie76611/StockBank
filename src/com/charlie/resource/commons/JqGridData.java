package com.charlie.resource.commons;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JqGridData<T> {
	
	/**
	 * 每頁有幾條紀錄
	 */
	private int size;
	/**
	 * 當前要查詢第幾頁jqgrid:currpage
	 */
	private int page;

	/**
	 * 誰要排序
	 */
	private String sort;
	/**
	 * 分頁的數據
	 */
	private String order;

	/**
	 * 總共有多少頁jqgrid:totalpages
	 */
	private int total;

	/**
	 * 總共有多少紀錄jqgrid:totalrecords
	 */
	private int records;

	/**
	 * jqgrid:root
	 */
	private List<T> rows;

	/**
	 * jqgrid:userdata
	 */
	private Map<String,Object> userdata = new HashMap<String,Object>();

	public JqGridData(int records, List<T> rows) {
		this.size = PagerContext.getSize();
		this.total = (records + (PagerContext.getSize() - 1) ) / PagerContext.getSize() ;
		this.page = PagerContext.getPage();
		this.sort = PagerContext.getSort();
		this.order = PagerContext.getOrder();
		this.records = records;
		this.rows = rows;
//		log.debug("[LOG]"+ToStringBuilder.reflectionToString(this));
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getRecords() {
		return records;
	}

	public void setRecords(int records) {
		this.records = records;		
	}

	
	
	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public Map<String, Object> getUserdata() {
		return userdata;
	}

	public void setUserdata(Map<String, Object> userdata) {
		this.userdata = userdata;
	}

}
