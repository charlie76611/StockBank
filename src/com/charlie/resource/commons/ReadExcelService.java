package com.charlie.resource.commons;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.charlie.resource.util.DateUtils;

/**
 * Import EXCEL
 * Use guide：
 * 1.Prepare an entity and new a Service to extends this 
 * 2.Overwrite abstract method setEntity(String[] oneRowCellsArray);
 * 3.Call readExcel methods so that return a List<T>
 * 
 * <table>
 * <tr>
 * <th>版本</th>
 * <th>日期</th>
 * <th>詳細說明</th>
 * <th>modifier</th>
 * </tr>
 * <tr>
 * <td>1.0</td>
 * <td>2014/9/22</td>
 * <td>新建檔案</td>
 * <td>Charlie Woo</td>
 * </tr>
 * </table> 
 *
 * @author Charlie Woo
 *
 * @param <T>
 */
public abstract class ReadExcelService<T> extends BaseService {

	/**
	 * The public method use by File
	 * @param file
	 * @return
	 * @throws Exception 
	 * @throws BiffException
	 */
	public List<ExcelImportDTO<T>> readExcel(File file, int sheetIndex, int startRowIndex) throws Exception {
		return readAndParseExcel(WorkbookFactory.create(file), sheetIndex, startRowIndex);
	}
	
	/**
	 * The public method use by InputStream
	 * @param is
	 * @return
	 * @throws Exception 
	 * @throws BiffException
	 */
	public List<ExcelImportDTO<T>> readExcel(InputStream is, int sheetIndex, int startRowIndex) throws Exception {
		return readAndParseExcel(WorkbookFactory.create(is), sheetIndex, startRowIndex);
	}
	
	/**
	 * The public method use by workBook
	 * @param is
	 * @return
	 * @throws Exception 
	 * @throws BiffException
	 */
	public List<ExcelImportDTO<T>> readExcelByWorkBook(Workbook workbook, int sheetIndex, int startRowIndex) throws Exception {
		return readAndParseExcel(workbook, sheetIndex, startRowIndex);
	}
	
	/**
	 * Parse to entity in its' way
	 * @param oneRowCellsArray
	 * @return
	 */
	protected abstract T setEntity(String[] oneRowCellsArray) throws Exception;
	
	/**
	 * Validate cells data by their logic way
	 * @param record
	 * @return
	 */
	protected abstract String validateModel(Integer rowLine, T record) throws Exception;
	
	private ExcelValidateInterface<T> excelValidator;
	
	
	/**
	 * Get the String[] list and loop each
	 * @param HSSFWorkbook
	 * @return
	 * @throws Exception 
	 * @throws BiffException
	 */
	/**
	 * @param HSSFWorkbook
	 * @param sheetIndex
	 * @param startRowIndex
	 * @return
	 * @throws Exception
	 */
	protected List<ExcelImportDTO<T>> readAndParseExcel(Workbook HSSFWorkbook, int sheetIndex, int startRowIndex) throws Exception {
		
		List<String[]> strArrays = readExcel(HSSFWorkbook, sheetIndex, startRowIndex);
		
		List<ExcelImportDTO<T>> models = new ArrayList<ExcelImportDTO<T>>();
		int count = startRowIndex;
		for (String[] oneRowCellsArray : strArrays) {  // for each model
			T obj = setEntity(oneRowCellsArray);
			if (obj != null) {				
				ExcelImportDTO<T> dto = new ExcelImportDTO<T>();
				dto.setRowLine(count+1);
				dto.setRecord(obj);
				if(getExcelValidator()!=null){
					dto.setErrorMsgs(StringUtils.trimToNull(getExcelValidator().validateModel(dto.getRowLine(), dto.getRecord())));
				}else{
					dto.setErrorMsgs(StringUtils.trimToNull(validateModel(dto.getRowLine(), dto.getRecord())));
				}
				models.add(dto);
			}
			count++;
		}
		return models;
	}
	
	/**
	 * Read excel and return String[] list
	 * @param workbook
	 * @return
	 */
	protected List<String[]> readExcel(Workbook workbook, int sheetIndex, int startRowIndex) {
		List<String[]> models = new LinkedList<String[]>();
		try {
			// 取得sheet
			Sheet sheet = workbook.getSheetAt(sheetIndex);	// 要匯入的第一個表
			// 顯示sheet資訊
			log.debug("Sheet=(Rows : " + sheet.getLastRowNum() + "), Name=" + sheet.getSheetName());
			
			// 將每行Cell內容轉成String Array的集合
			String[] contents = null;
			for (int r = startRowIndex; r <= sheet.getLastRowNum(); r++) {
				Row row = sheet.getRow(r);
				if(row!=null){
					contents = new String[row.getLastCellNum()];
					for (int c = 0; c < row.getLastCellNum(); c++) {					
						Cell cell = row.getCell(c);
						if (cell != null) {
							contents[c] = convertCellStr(cell);
						}
					}
					models.add(contents);
				}
				contents = null;
			}			
		} catch (Exception ex) {
			log.error(ExceptionUtils.getFullStackTrace(ex));
		}
		return models;
	}

	/**
	 * 把儲存格內的類型轉換成String
	 * 
	 * @param cell
	 * @return
	 */
	private String convertCellStr(Cell cell) {

		if (cell == null) return null;
		
		String cellStr = null;
		switch (cell.getCellType()) {

			case Cell.CELL_TYPE_STRING :
				// 讀取String
				cellStr = cell.getStringCellValue().toString();
				break;
	
			case Cell.CELL_TYPE_BOOLEAN :
				// 得到Boolean對象的方法
				cellStr = String.valueOf(cell.getBooleanCellValue());
				break;
	
			case Cell.CELL_TYPE_NUMERIC :
				// 是否為日期格式
				if (DateUtil.isCellDateFormatted(cell)) {
					cellStr = DateUtils.formatDateUS(cell.getDateCellValue(), "yyyy/MM/dd HH:mm:ss");
					// cellStr = cell.getDateCellValue().toString();
				} else { // 讀取數字
					double number = cell.getNumericCellValue();
					// 是否為int型態
					if (number - (int) number < Double.MIN_VALUE) {
						cellStr = Integer.toString((int) number);
					} else { // 是否為double型
						cellStr = Double.toString(cell.getNumericCellValue());
					}
				}
				break;
	
			case Cell.CELL_TYPE_BLANK : // 空值
				cellStr = null;
				break;
		}
		return cellStr;
	}

	public ExcelValidateInterface<T> getExcelValidator() {
		return excelValidator;
	}

	public void setExcelValidator(ExcelValidateInterface<T> excelValidator) {
		this.excelValidator = excelValidator;
	}
	
}
