package com.charlie.resource.commons;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


/**
 * 
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
 * <td>2014/10/1</td>
 * <td>Excel匯出共用Service</td>
 * <td>Charlie Woo</td>
 * </tr>
 * </table> 
 *
 * @author Charlie Woo
 *
 * @param <T>
 */
public abstract class WriteExcelService<T> extends BaseService {

	public void exportExcel(ExcelExportDTO<T> dto, HttpServletResponse response) throws IOException {
		// 建立一個 workbook
		HSSFWorkbook workbook = new HSSFWorkbook();
		
		// 建立一個worksheet
		HSSFSheet worksheet = workbook.createSheet(dto.getSheetName());
		
		// 建立報表的 標題、日期、欄位名稱、欄位屬性
		setDefaults(worksheet, dto.getStartRowIndex(), dto.getStartColIndex());

		// 設定儲存格的值
		setContents(worksheet, dto.getStartRowIndex(), dto.getStartColIndex(), dto.getDatasource());

		// 設定reponse參數 (檔案名稱)
		String fileName = dto.getExportFileName();
		response.setHeader("Content-Disposition", "inline; filename="+ fileName);
		// 設定檔案匯出類型
		response.setContentType("application/vnd.ms-excel");

		// 輸出流
		write(response, worksheet);
	}
	
	private void write(HttpServletResponse response, HSSFSheet worksheet) throws IOException {  		  
        // Retrieve the output stream  
        ServletOutputStream outputStream = response.getOutputStream();  
        // Write to the output stream  
        worksheet.getWorkbook().write(outputStream);  
        // 清除緩衝區資料  
        outputStream.flush();   
        
        outputStream.close();
    }  
	
	private void setDefaults(HSSFSheet worksheet, int startRowIndex, int startColIndex) {
        // 設定儲存格的屬性 
		setColAttributes(worksheet);        
        //建立報表標題與日期
        setReportTitle(worksheet, startRowIndex, startColIndex);  
        //建立欄位名稱
        setHeaders(worksheet, startRowIndex, startColIndex);  
    } 
	
	protected abstract void setContents(HSSFSheet worksheet, int startRowIndex, int startColIndex, List<T> datasource);
	
	protected abstract void setColAttributes(HSSFSheet worksheet);
	
	protected abstract void setReportTitle(HSSFSheet worksheet, int startRowIndex, int startColIndex);
	
	protected abstract void setHeaders(HSSFSheet worksheet, int startRowIndex, int startColIndex);
}
