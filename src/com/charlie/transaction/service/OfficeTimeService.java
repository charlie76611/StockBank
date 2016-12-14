package com.charlie.transaction.service;

import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.charlie.resource.AppConstants;
import com.charlie.resource.commons.ExcelImportDTO;
import com.charlie.resource.commons.PoiService;
import com.charlie.resource.commons.ReadExcelService;
import com.charlie.resource.util.DateUtils;
import com.charlie.resource.util.Writer;
import com.charlie.transaction.model.OfficeTimeModel;

@Service
public class OfficeTimeService extends ReadExcelService<OfficeTimeModel> {
	
	public static final String STANDARD_SHORT_DATETIME_PATTERN = "yyyy/MM/ddHHmm";
	
	@Autowired
	private PoiService poiService;
	
	// Export
	public void exportExcel(HttpServletResponse response, String sheetName, String fileName, List<OfficeTimeModel> result) {
		HSSFSheet worksheet = poiService.exportXLS(sheetName);
		// 建立報表的 標題、日期、欄位名稱
		String[] title = { "日期", "班別", "早班(下)", "午班(下)", "晚班(下)", "總上班時數", "加班時數", "欠班時數"};
		buildExcel(worksheet, result, title);
		
		// 輸出流
		Writer.write(poiService.setResponse(response, fileName + ".xls"), worksheet);
	} 
	
	/**
	 * 建立欄位名稱與樣式
	 * @param worksheet
	 * @param result
	 * @param titlea
	 * @param expType
	 */
	private void buildExcel(HSSFSheet worksheet, List<OfficeTimeModel> result, String[] titleName) {
		// 欄位字體
		HSSFWorkbook workbook = worksheet.getWorkbook();
		Font font = workbook.createFont();
		font.setBoldweight(Font.BOLDWEIGHT_NORMAL); // 字體普通粗細
		// 設定欄寬
		worksheet.setColumnWidth(0, 15 * 256);
		worksheet.setColumnWidth(1, 10 * 256);
		worksheet.setColumnWidth(2, 10 * 256);
		worksheet.setColumnWidth(3, 10 * 256);
		worksheet.setColumnWidth(4, 10 * 256);
		worksheet.setColumnWidth(5, 20 * 256);
		worksheet.setColumnWidth(6, 20 * 256);
		worksheet.setColumnWidth(7, 20 * 256);

		HashMap<String, HSSFCellStyle> cellStyleMap = new HashMap<String, HSSFCellStyle>();
		cellStyleMap.put("header", poiService.getCM003HeadCellStyle(workbook, font));
		
		// 標題
		String[] title = titleName;
		// 建立欄位名稱
		HSSFRow header = worksheet.createRow(0);
		for (int i = 0; i < title.length; i++) {
			HSSFCell headerCell = header.createCell(i);
			headerCell.setCellValue(title[i]);
			headerCell.setCellStyle(cellStyleMap.get("header"));
		}
	    
	    //日期格式
//		HSSFCellStyle dateStyle=workbook.createCellStyle();    
//		dateStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy"));
		
		// 寫入資料
		int rowIdx = 1;
		for (int i = 0; i < result.size(); i++) {
			HSSFRow excelRow = worksheet.createRow(rowIdx);
			OfficeTimeModel row = (OfficeTimeModel) result.get(i);
			excelRow.createCell(0).setCellValue(row.getDate());
			excelRow.createCell(1).setCellValue(row.getOfficialPart());
			if (StringUtils.isNotEmpty(row.getOfficialPart())) {
				excelRow.createCell(2).setCellValue(DateUtils.formatDate(row.getOffWorkTimeA(), AppConstants.ALTERNATIVE_MINUTE_PATTERN));
				excelRow.createCell(3).setCellValue(DateUtils.formatDate(row.getOffWorkTimeP(), AppConstants.ALTERNATIVE_MINUTE_PATTERN));
				excelRow.createCell(4).setCellValue(DateUtils.formatDate(row.getOffWorkTimeN(), AppConstants.ALTERNATIVE_MINUTE_PATTERN));
				
				HSSFCell hssfcell_5 = excelRow.createCell(5);	
				HSSFCell hssfcell_6 = excelRow.createCell(6);			
				HSSFCell hssfcell_7 = excelRow.createCell(7);
				
				hssfcell_5.setCellValue(DateUtils.diffMinutesForString(row.getTotalWorkTime()));
				if (row.getOverWorkTimePerDay() > 0.0)
				hssfcell_6.setCellValue(DateUtils.diffMinutesForString(row.getOverWorkTimePerDay()));
				if (row.getLackWorkTimePerDay() > 0.0)
				hssfcell_7.setCellValue(DateUtils.diffMinutesForString(row.getLackWorkTimePerDay()));			
			}			
			rowIdx++;	// 下一筆
		}
	}
	
	/**
	 * 將Excel資料轉為List
	 * @param is
	 * @return
	 * @throws Exception
	 */
	public List<OfficeTimeModel> readReport(InputStream is) throws Exception {
		List<OfficeTimeModel> results = new ArrayList<OfficeTimeModel>();
		List<ExcelImportDTO<OfficeTimeModel>> models = readAndParseExcel(WorkbookFactory.create(is), 0, 1);
		
		for (ExcelImportDTO<OfficeTimeModel> model : models) {
			if (model.getRecord() == null) {
				continue;
			}
			if (!StringUtils.isBlank(model.getErrorMsgs())) {
				throw new Exception(model.getErrorMsgs());
			}
			
//			log.debug(model.getRecord().toString());
			results.add(model.getRecord());
		}
		
		return results;
	}
	
	@Override
	protected OfficeTimeModel setEntity(String[] oneRowCellsArray) throws Exception {
		if (oneRowCellsArray.length == 0 || StringUtils.isEmpty(oneRowCellsArray[0])) {
			return null;
		}
		
		OfficeTimeModel model = new OfficeTimeModel();
		model.setDate(oneRowCellsArray[0]);
		model.setOfficialPart(oneRowCellsArray[1]);
		
		if (StringUtils.isNotEmpty(oneRowCellsArray[2]))
		model.setOffWorkTimeA(DateUtils.parse(oneRowCellsArray[0] + oneRowCellsArray[2], STANDARD_SHORT_DATETIME_PATTERN));
		if (StringUtils.isNotEmpty(oneRowCellsArray[3]))
		model.setOffWorkTimeP(DateUtils.parse(oneRowCellsArray[0] + oneRowCellsArray[3], STANDARD_SHORT_DATETIME_PATTERN));
		if (StringUtils.isNotEmpty(oneRowCellsArray[4]))
		model.setOffWorkTimeN(DateUtils.parse(oneRowCellsArray[0] + oneRowCellsArray[4], STANDARD_SHORT_DATETIME_PATTERN));
		return model;
	}

	@Override
	protected String validateModel(Integer rowLine, OfficeTimeModel record) throws Exception {
		//  Auto-generated method stub
		return null;
	}

	/**
	 * 計算各時段加班時數與總上班時數
	 * @param officeTimeList
	 * @param dto
	 * @return
	 * @author Charlie Woo 2016/11/25
	 */
	public void calculateOfficeTime(List<OfficeTimeModel> officeTimeList, OfficeTimeModel dto) {
		Map<String, Timestamp> typeTimeMap = new HashMap<>();
		for (OfficeTimeModel model : officeTimeList) {			
			if (StringUtils.isEmpty(model.getOfficialPart())) continue;		// 放假日沒有班別
			typeTimeMap = getTypeTimeMap(typeTimeMap, model.getDate(), dto);
			
			Double totalWorkTime = 0.0, overTime = 0.0, lackTime = 0.0;			
			// 計算各班別加班時間
			switch (model.getOfficialPart()) {
				case "A" :	// (早+午)
					// 加班(早)：若下班時間超過加班起算，則實算，反之則不算加班
					if (model.getOffWorkTimeA().getTime() > typeTimeMap.get("typeAA_duty").getTime()) {	// 超過加班起算
						overTime += DateUtils.dateTimeDiff(typeTimeMap.get("typeAA_off"), model.getOffWorkTimeA(), DateUtils.MINUTES);
						totalWorkTime += DateUtils.dateTimeDiff(typeTimeMap.get("typeAA_on"), model.getOffWorkTimeA(), DateUtils.MINUTES);
						
					} else if (model.getOffWorkTimeA().getTime() > typeTimeMap.get("typeAA_off").getTime()) {	// 有上足，但未超過加班起算，以正規時間算
						totalWorkTime += DateUtils.dateTimeDiff(typeTimeMap.get("typeAA_on"), typeTimeMap.get("typeAA_off"), DateUtils.MINUTES);
					} else {	// 有欠班時間
						totalWorkTime += DateUtils.dateTimeDiff(typeTimeMap.get("typeAA_on"), model.getOffWorkTimeA(), DateUtils.MINUTES);
						lackTime += DateUtils.dateTimeDiff(model.getOffWorkTimeA(), typeTimeMap.get("typeAA_off"), DateUtils.MINUTES);
					}
					
					// 加班(午)：若下班時間超過加班起算，則實算，反之則不算加班
					if (model.getOffWorkTimeP().getTime() > typeTimeMap.get("typeAP_duty").getTime()) {	// 超過加班起算
						overTime += DateUtils.dateTimeDiff(typeTimeMap.get("typeAP_off"), model.getOffWorkTimeP(), DateUtils.MINUTES);
						totalWorkTime += DateUtils.dateTimeDiff(typeTimeMap.get("typeAP_on"), model.getOffWorkTimeP(), DateUtils.MINUTES);
						
					} else if (model.getOffWorkTimeP().getTime() > typeTimeMap.get("typeAP_off").getTime()) {	// 有上足，但未超過加班起算，以正規時間算
						totalWorkTime += DateUtils.dateTimeDiff(typeTimeMap.get("typeAP_on"), typeTimeMap.get("typeAP_off"), DateUtils.MINUTES);
					} else {	// 有欠班時間
						totalWorkTime += DateUtils.dateTimeDiff(typeTimeMap.get("typeAP_on"), model.getOffWorkTimeP(), DateUtils.MINUTES);
						lackTime += DateUtils.dateTimeDiff(model.getOffWorkTimeP(), typeTimeMap.get("typeAP_off"), DateUtils.MINUTES);
					}
					break;
					
				case "B" :	// (早+晚)
					// 加班(早)：若下班時間超過加班起算，則實算，反之則不算加班
					if (model.getOffWorkTimeA().getTime() > typeTimeMap.get("typeBA_duty").getTime()) {
						overTime += DateUtils.dateTimeDiff(typeTimeMap.get("typeBA_off"), model.getOffWorkTimeA(), DateUtils.MINUTES);
						totalWorkTime += DateUtils.dateTimeDiff(typeTimeMap.get("typeBA_on"), model.getOffWorkTimeA(), DateUtils.MINUTES);
						
					} else if (model.getOffWorkTimeA().getTime() > typeTimeMap.get("typeBA_off").getTime()) {
						totalWorkTime += DateUtils.dateTimeDiff(typeTimeMap.get("typeBA_on"), typeTimeMap.get("typeBA_off"), DateUtils.MINUTES);
					} else {
						totalWorkTime += DateUtils.dateTimeDiff(typeTimeMap.get("typeBA_on"), model.getOffWorkTimeA(), DateUtils.MINUTES);
						lackTime += DateUtils.dateTimeDiff(model.getOffWorkTimeA(), typeTimeMap.get("typeBA_off"), DateUtils.MINUTES);
					}
					
					// 加班(晚)：若下班時間超過加班起算，則實算，反之則不算加班
					if (model.getOffWorkTimeN().getTime() > typeTimeMap.get("typeBN_duty").getTime()) {
						overTime += DateUtils.dateTimeDiff(typeTimeMap.get("typeBN_off"), model.getOffWorkTimeN(), DateUtils.MINUTES);
						totalWorkTime += DateUtils.dateTimeDiff(typeTimeMap.get("typeBN_on"), model.getOffWorkTimeN(), DateUtils.MINUTES);
					} else if (model.getOffWorkTimeN().getTime() > typeTimeMap.get("typeBN_off").getTime()) {
						totalWorkTime += DateUtils.dateTimeDiff(typeTimeMap.get("typeBN_on"), typeTimeMap.get("typeBN_off"), DateUtils.MINUTES);
					} else {
						totalWorkTime += DateUtils.dateTimeDiff(typeTimeMap.get("typeBN_on"), model.getOffWorkTimeN(), DateUtils.MINUTES);
						lackTime += DateUtils.dateTimeDiff(model.getOffWorkTimeN(), typeTimeMap.get("typeBN_off"), DateUtils.MINUTES);
					}
					break;
					
				case "C" :	// (晚)
					// 加班(晚)：若下班時間超過加班起算，則實算，反之則不算加班
					if (model.getOffWorkTimeN().getTime() > typeTimeMap.get("typeCN_duty").getTime()) {
						overTime += DateUtils.dateTimeDiff(typeTimeMap.get("typeCN_off"), model.getOffWorkTimeN(), DateUtils.MINUTES);
						totalWorkTime += DateUtils.dateTimeDiff(typeTimeMap.get("typeCN_on"), model.getOffWorkTimeN(), DateUtils.MINUTES);
					} else if (model.getOffWorkTimeN().getTime() > typeTimeMap.get("typeCN_off").getTime()) {
						totalWorkTime += DateUtils.dateTimeDiff(typeTimeMap.get("typeCN_on"), typeTimeMap.get("typeCN_off"), DateUtils.MINUTES);
					} else {
						totalWorkTime += DateUtils.dateTimeDiff(typeTimeMap.get("typeCN_on"), model.getOffWorkTimeN(), DateUtils.MINUTES);
						lackTime += DateUtils.dateTimeDiff(model.getOffWorkTimeN(), typeTimeMap.get("typeCN_off"), DateUtils.MINUTES);
					}
					break;
					
				case "D" :	// (早)
					// 加班(早)：若下班時間超過加班起算，則實算，反之則不算加班
					if (model.getOffWorkTimeA().getTime() > typeTimeMap.get("typeDA_duty").getTime()) {
						overTime += DateUtils.dateTimeDiff(typeTimeMap.get("typeDA_off"), model.getOffWorkTimeA(), DateUtils.MINUTES);
						totalWorkTime += DateUtils.dateTimeDiff(typeTimeMap.get("typeDA_on"), model.getOffWorkTimeA(), DateUtils.MINUTES);
					} else if (model.getOffWorkTimeA().getTime() > typeTimeMap.get("typeDA_off").getTime()) {
						totalWorkTime += DateUtils.dateTimeDiff(typeTimeMap.get("typeDA_on"), typeTimeMap.get("typeDA_off"), DateUtils.MINUTES);
					} else {
						totalWorkTime += DateUtils.dateTimeDiff(typeTimeMap.get("typeDA_on"), model.getOffWorkTimeA(), DateUtils.MINUTES);
						lackTime += DateUtils.dateTimeDiff(model.getOffWorkTimeA(),typeTimeMap.get("typeDA_off"), DateUtils.MINUTES);
					}
					break;
			}
			
			// 總加/欠班(加班扣抵欠班)
			if (overTime > lackTime) {
				model.setOverTime(true);
				model.setOverWorkTimePerDay(overTime - lackTime);	// 加班時間總和
			} else if (overTime < lackTime) {
				model.setLackTime(true);
				model.setLackWorkTimePerDay(lackTime - overTime);	// 欠班時間總和
			}
			
			// 總上班
			model.setTotalWorkTime(totalWorkTime);
			log.debug(model.toString());
		}
		
	}
	
	private Map<String, Timestamp> getTypeTimeMap(Map<String, Timestamp> map, String date, OfficeTimeModel dto) {
		map.clear();
		map.put("typeAA_on", DateUtils.parse(date + dto.getTypeAA_on() + "00", "yyyy/MM/ddHHmmss"));
		map.put("typeAA_off", DateUtils.parse(date + dto.getTypeAA_off() + "00", "yyyy/MM/ddHHmmss"));
		map.put("typeAA_duty", DateUtils.parse(date + dto.getTypeAA_duty() + "00", "yyyy/MM/ddHHmmss"));
		map.put("typeAP_on", DateUtils.parse(date + dto.getTypeAP_on() + "00", "yyyy/MM/ddHHmmss"));
		map.put("typeAP_off", DateUtils.parse(date + dto.getTypeAP_off() + "00", "yyyy/MM/ddHHmmss"));
		map.put("typeAP_duty", DateUtils.parse(date + dto.getTypeAP_duty() + "00", "yyyy/MM/ddHHmmss"));
		
		map.put("typeBA_on", DateUtils.parse(date + dto.getTypeBA_on() + "00", "yyyy/MM/ddHHmmss"));
		map.put("typeBA_off", DateUtils.parse(date + dto.getTypeBA_off() + "00", "yyyy/MM/ddHHmmss"));
		map.put("typeBA_duty", DateUtils.parse(date + dto.getTypeBA_duty() + "00", "yyyy/MM/ddHHmmss"));
		map.put("typeBN_on", DateUtils.parse(date + dto.getTypeBN_on() + "00", "yyyy/MM/ddHHmmss"));
		map.put("typeBN_off", DateUtils.parse(date + dto.getTypeBN_off() + "00", "yyyy/MM/ddHHmmss"));
		map.put("typeBN_duty", DateUtils.parse(date + dto.getTypeBN_duty() + "00", "yyyy/MM/ddHHmmss"));
		
		map.put("typeCN_on", DateUtils.parse(date + dto.getTypeCN_on() + "00", "yyyy/MM/ddHHmmss"));
		map.put("typeCN_off", DateUtils.parse(date + dto.getTypeCN_off() + "00", "yyyy/MM/ddHHmmss"));
		map.put("typeCN_duty", DateUtils.parse(date + dto.getTypeCN_duty() + "00", "yyyy/MM/ddHHmmss"));
		
		map.put("typeDA_on", DateUtils.parse(date + dto.getTypeDA_on() + "00", "yyyy/MM/ddHHmmss"));
		map.put("typeDA_off", DateUtils.parse(date + dto.getTypeDA_off() + "00", "yyyy/MM/ddHHmmss"));
		map.put("typeDA_duty", DateUtils.parse(date + dto.getTypeDA_duty() + "00", "yyyy/MM/ddHHmmss"));
		return map;
	}
}
