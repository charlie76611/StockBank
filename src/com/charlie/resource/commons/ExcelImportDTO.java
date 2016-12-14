package com.charlie.resource.commons;


public class ExcelImportDTO<T> {

	private T record;
	
	private Integer rowLine;
	
	private String errorMsgs;

	public T getRecord() {
		return record;
	}

	public void setRecord(T record) {
		this.record = record;
	}

	public Integer getRowLine() {
		return rowLine;
	}

	public void setRowLine(Integer rowLine) {
		this.rowLine = rowLine;
	}

	public String getErrorMsgs() {
		return errorMsgs;
	}

	public void setErrorMsgs(String errorMsgs) {
		this.errorMsgs = errorMsgs;
	}

}
