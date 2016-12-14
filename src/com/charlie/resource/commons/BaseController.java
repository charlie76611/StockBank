package com.charlie.resource.commons;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sourceforge.stripes.action.ActionBeanContext;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.charlie.security.model.UserModel;



public abstract class BaseController {

	protected Logger log = Logger.getLogger(this.getClass());

	@Autowired
	private MessageSource messageSource;
	private DynaCMSActionContext context;
	
	public static final String AJAX_SUCCESS = "success";
	public static final String AJAX_FAILED = "failed";
	public static final String AJAX_EMPTY = "empty";

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		// 日期
		CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("yyyy/MM/dd"), true);
		binder.registerCustomEditor(Date.class, editor);
		// 字串
		StringTrimmerEditor strEditor = new StringTrimmerEditor(true);
		binder.registerCustomEditor(String.class, strEditor);
	}
	
	public DynaCMSActionContext getContext() {
		return context;
	}

	public void setContext(ActionBeanContext context) {
		this.context = (DynaCMSActionContext) context;
	}

	protected HttpServletRequest getRequest() {
		ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		return attrs.getRequest();
	}
	
	public UserModel getLoginUser() {
		UserModel user = getContext().getLoginUser();
		if (user == null) {
			user = new UserModel();
		}
		return user;
	}
	
	public String getLoginAccount() {
		return getLoginUser().getAccount();
	}

	protected HttpSession getSession() {
		return getRequest().getSession();
	}

	protected String getUserIp() {
		return getRequest().getRemoteAddr();
	}

	protected String getMessageDetail(String key, String... replacrStrings) {
		return messageSource.getMessage(key, replacrStrings, getRequest().getLocale());
	}

	protected String getMessageDetail(String key) {
		return messageSource.getMessage(key, null, getRequest().getLocale());
	}

	protected Integer getCurrentMenuId() {
		return Integer.parseInt((String) getSession().getAttribute("currentMenuId"));
	}
	
	protected String getCurrentThreadMethodName() {
		return Thread.currentThread().getStackTrace()[2].getMethodName();
	}

	@SuppressWarnings({ "rawtypes" })
	protected Map getParamMap(HttpServletRequest request) {
		Map<String, String[]> requestMap = request.getParameterMap();
		Map<String, Object> paramMap = new HashMap<>(requestMap.size());
		for (Map.Entry<String, String[]> entry : requestMap.entrySet()) {
			String[] values = entry.getValue();
			if (values.length == 0) {
				paramMap.put(entry.getKey(), null);
			} else {
				paramMap.put(entry.getKey(), StringUtils.trimToNull(values[0]));
			}
		}
		return paramMap;
	}
}
