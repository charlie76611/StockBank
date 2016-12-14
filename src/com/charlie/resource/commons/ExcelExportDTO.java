package com.charlie.resource.commons;

import java.util.List;


public class ExcelExportDTO<T> {

	private List<T> datasource;
	
	private String sheetName;
	
	private int startRowIndex;
	
	private int startColIndex;
	
	private String exportFileName;

	public List<T> getDatasource() {
		return datasource;
	}

	public void setDatasource(List<T> datasource) {
		this.datasource = datasource;
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public int getStartRowIndex() {
		return startRowIndex;
	}

	public void setStartRowIndex(int startRowIndex) {
		this.startRowIndex = startRowIndex;
	}

	public int getStartColIndex() {
		return startColIndex;
	}

	public void setStartColIndex(int startColIndex) {
		this.startColIndex = startColIndex;
	}

	public String getExportFileName() {
		return exportFileName;
	}

	public void setExportFileName(String exportFileName) {
		this.exportFileName = exportFileName;
	}
}
