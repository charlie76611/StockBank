package com.charlie.transaction.web;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.charlie.resource.commons.BaseController;
import com.charlie.resource.commons.BaseJasonObject;
import com.charlie.transaction.model.OfficeTimeModel;
import com.charlie.transaction.service.OfficeTimeService;
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
 * <td>2016/11/17</td>
 * <td>工時計算器</td>
 * <td>Charlie Woo</td>
 * </tr>
 * </table> 
 *
 * @author Charlie Woo
 *
 */
@Controller
public class OfficeTimeController extends BaseController {

	@Autowired
	private OfficeTimeService service;
	
	/**
	 * 匯入Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/officeTime/exportXls", method = RequestMethod.POST) 
	@ResponseBody
	public BaseJasonObject<String> expotScrapItem(HttpServletRequest request, HttpServletResponse response, 
			MultipartHttpServletRequest mutipartRequest, OfficeTimeModel dto) {

		// 取得匯入資料清單
		List<OfficeTimeModel> officeTimeList = null;
		// 讀取報表SET OfficeTimeModel
		try {			
			for (Iterator<String> itr = mutipartRequest.getFileNames(); itr.hasNext();) {
				MultipartFile file = mutipartRequest.getFile(itr.next());
				officeTimeList = service.readReport(file.getInputStream());
				break;	// only one file import
			}
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			return new BaseJasonObject<String>(false, e.getMessage());
		}
		
		// 計算邏輯
		service.calculateOfficeTime(officeTimeList, dto);
		
		// 放置session
		request.getSession().setAttribute("officeTimeTemp", officeTimeList);
		return new BaseJasonObject<String>(true, "");
	}
	
	/**
	 * 匯出EXCEL
	 * @param request
	 * @param response
	 * @param poId
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/officeTime/exportExcel", method = RequestMethod.GET) 
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) { 
		
		String year = request.getParameter("year");
		int month = Integer.parseInt(request.getParameter("month"));
		String monthOfYear = month < 10 ? "0" + month : "" + month;
		List<OfficeTimeModel> officeTimeList = (List<OfficeTimeModel>) request.getSession().getAttribute("officeTimeTemp");
		
		// 匯出結果		
		String fileName = "OfficeTime_" + year + monthOfYear;
		try {
			fileName = new String(fileName.getBytes(), "ISO-8859-1");
			service.exportExcel(response, "工時計算器", fileName, officeTimeList);
		} catch (UnsupportedEncodingException e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
    } 
}
