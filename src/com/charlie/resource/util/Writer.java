package com.charlie.resource.util;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
  
public class Writer {  
	
    public static void write(HttpServletResponse response, HSSFSheet worksheet) {  
  
//    	System.out.println("Writing report to the stream");  
        try {  
            // Retrieve the output stream  
            ServletOutputStream outputStream = response.getOutputStream();  
            // Write to the output stream  
            worksheet.getWorkbook().write(outputStream);  
            // 清除緩衝區資料  
            outputStream.flush();  
  
        } catch (Exception e) {  
        	System.err.println("匯入失敗!" + ExceptionUtils.getFullStackTrace(e));  
        }  
    }  
    
    
    public static void write(HttpServletResponse response, HSSFWorkbook workbook) {  
    	  
//    	System.out.println("Writing report to the stream");  
        try {  
            // Retrieve the output stream  
            ServletOutputStream outputStream = response.getOutputStream();  
            // Write to the output stream  
            workbook.write(outputStream);  
            // 清除緩衝區資料  
            outputStream.flush();  
  
        } catch (Exception e) {  
        	System.err.println("匯入失敗!" + ExceptionUtils.getFullStackTrace(e));  
        }  
    }  
    
    
  
}  
