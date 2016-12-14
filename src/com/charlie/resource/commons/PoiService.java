package com.charlie.resource.commons;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.springframework.stereotype.Service;

@Service
public class PoiService extends BaseService {
	
	/**
	 *  建立workbook
	 * @param sheetName
	 * @return
	 */
	public HSSFSheet exportXLS(String sheetName) {
		// 建立一個 workbook
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 建立一個worksheet
		HSSFSheet worksheet = workbook.createSheet(sheetName);
		return worksheet;
	}
	
	/**
	 * 設定response
	 * @param response
	 * @param fileName
	 * @return
	 */
	public HttpServletResponse setResponse(HttpServletResponse response,String fileName){
		// 設定reponse參數 (檔案名稱)
		response.setHeader("Content-Disposition", "inline; filename=" + fileName);
		// 設定檔案匯出類型
		response.setContentType("application/vnd.ms-excel");
		return response;
	}
	
	/**
	 * 匯出工項-HeadCellStyle 
	 * @param workbook
	 * @param font
	 * @return
	 */
	public HSSFCellStyle getCM003HeadCellStyle(HSSFWorkbook workbook, Font font) {
		// 標頭 CellStyle
        HSSFCellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(font);	//設定字體
        headerCellStyle.setFillForegroundColor(HSSFColor.LIGHT_ORANGE.index);  //設定欄位顏色
        headerCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);			//設定欄位填滿效果
        headerCellStyle.setAlignment(CellStyle.ALIGN_LEFT);  //靠左對齊
        headerCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);  //垂直置中
        headerCellStyle.setWrapText(false);  //設定換行
        headerCellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);  //設定下邊框為粗邊框 
        headerCellStyle.setBottomBorderColor(HSSFColor.BLACK.index); //設定下邊框顏色
        headerCellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);	  
        headerCellStyle.setLeftBorderColor(HSSFColor.BLACK.index);		
        headerCellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        headerCellStyle.setRightBorderColor(HSSFColor.BLACK.index);
        headerCellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        headerCellStyle.setTopBorderColor(HSSFColor.BLACK.index);
        
        return headerCellStyle;
	}
	
	/**
	 * 匯出工項-MainItemCellStyle 
	 * @param workbook
	 * @param font
	 * @return
	 */
	public HSSFCellStyle getCM003MainItemCellStyle(HSSFWorkbook workbook, Font font) {
		// MainItemCellStyle
        HSSFCellStyle mainItemCellStyle = workbook.createCellStyle();
        mainItemCellStyle.setFont(font);	//設定字體
        mainItemCellStyle.setFillForegroundColor(HSSFColor.BRIGHT_GREEN.index);  //設定欄位顏色
        mainItemCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);			//設定欄位填滿效果
        mainItemCellStyle.setAlignment(CellStyle.ALIGN_LEFT);  //靠左對齊
        mainItemCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);  //垂直置中
        mainItemCellStyle.setWrapText(false);  //設定換行
        mainItemCellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);  //設定下邊框為粗邊框 
        mainItemCellStyle.setBottomBorderColor(HSSFColor.BLACK.index); //設定下邊框顏色
        mainItemCellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);	  
        mainItemCellStyle.setLeftBorderColor(HSSFColor.BLACK.index);		
        mainItemCellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        mainItemCellStyle.setRightBorderColor(HSSFColor.BLACK.index);
        mainItemCellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        mainItemCellStyle.setTopBorderColor(HSSFColor.BLACK.index);
        
        return mainItemCellStyle;
	}
	
	/**
	 * 匯出工項-SubItemCellStyle 
	 * @param workbook
	 * @param font
	 * @return
	 */
	public HSSFCellStyle getCM003SubItemCellStyle(HSSFWorkbook workbook, Font font) {
		// SubItemCellStyle
        HSSFCellStyle subItemCellStyle = workbook.createCellStyle();
        subItemCellStyle.setFont(font);	//設定字體
        subItemCellStyle.setFillForegroundColor(HSSFColor.GOLD.index);  //設定欄位顏色
        subItemCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);			//設定欄位填滿效果
        subItemCellStyle.setAlignment(CellStyle.ALIGN_LEFT);  //靠左對齊
        subItemCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);  //垂直置中
        subItemCellStyle.setWrapText(false);  //設定換行
        subItemCellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);  //設定下邊框為粗邊框 
        subItemCellStyle.setBottomBorderColor(HSSFColor.BLACK.index); //設定下邊框顏色
        subItemCellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);	  
        subItemCellStyle.setLeftBorderColor(HSSFColor.BLACK.index);		
        subItemCellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        subItemCellStyle.setRightBorderColor(HSSFColor.BLACK.index);
        subItemCellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        subItemCellStyle.setTopBorderColor(HSSFColor.BLACK.index);
        
        return subItemCellStyle;
	}
	
	/**
	 * 匯出工項-ItemCellStyle 
	 * @param workbook
	 * @param font
	 * @return
	 */
	public HSSFCellStyle getCM003ItemCellStyle(HSSFWorkbook workbook, Font font) {
		// itemCellStyle
        HSSFCellStyle itemCellStyle = workbook.createCellStyle();
        itemCellStyle.setFont(font);	//設定字體
        itemCellStyle.setAlignment(CellStyle.ALIGN_LEFT);  //靠左對齊
        itemCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);  //垂直置中
        itemCellStyle.setWrapText(false);  //設定換行
        itemCellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);  //設定下邊框為粗邊框 
        itemCellStyle.setBottomBorderColor(HSSFColor.BLACK.index); //設定下邊框顏色
        itemCellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);	  
        itemCellStyle.setLeftBorderColor(HSSFColor.BLACK.index);		
        itemCellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        itemCellStyle.setRightBorderColor(HSSFColor.BLACK.index);
        itemCellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        itemCellStyle.setTopBorderColor(HSSFColor.BLACK.index);
        itemCellStyle.setWrapText(true);
        
        return itemCellStyle;
	}
	
}
