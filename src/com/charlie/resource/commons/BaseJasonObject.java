package com.charlie.resource.commons;

import java.util.List;
import java.util.Map;

public class BaseJasonObject<T> {

	private boolean success = true;
	private String msg = "ajax success";
	private T row;
	private List<T> rows;
	private Map<String, T> maps;
	private List<String> errors;
	private Map<String, List<T>> mapList;

	public BaseJasonObject() {
		
	}
	public BaseJasonObject(String successMsg) {
		this.msg = successMsg;
	}
	public BaseJasonObject(boolean success ,String msg) {
		this.success = success;
		this.msg = msg;
	}

	public BaseJasonObject(T row, String successMsg) {
		this.row = row;
		this.msg = successMsg;
		if (row == null) {
			this.success = false;
			this.msg = "NO DATA FOUND";
		}
	}
	
	public BaseJasonObject(List<T> rows, String successMsg, String noDataMsg) {
		this.rows = rows;
		this.msg = successMsg;
		if (rows == null || rows.size() == 0) {
			this.success = false;
			this.msg = noDataMsg;
		}
	}

	public BaseJasonObject(Map<String, T> maps, String successMsg,
			String noDataMsg) {
		this.maps = maps;
		this.msg = successMsg;
		if (maps == null || maps.size() == 0) {
			this.success = false;
			this.msg = noDataMsg;
		}
	}
	
	public BaseJasonObject(Map<String, List<T>> mapList, String successMsg) {
		this.mapList = mapList;
		this.msg = successMsg;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	public T getRow() {
		return row;
	}

	public void setRow(T row) {
		this.row = row;
	}
	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public Map<String, T> getMaps() {
		return maps;
	}

	public void setMaps(Map<String, T> maps) {
		this.maps = maps;
	}
	public List<String> getErrors() {
		return errors;
	}
	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

	
	
	public Map<String, List<T>> getMapList() {
		return mapList;
	}

	public void setMapList(Map<String, List<T>> mapList) {
		this.mapList = mapList;
	}
}
