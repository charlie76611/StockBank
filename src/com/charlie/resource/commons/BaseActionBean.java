package com.charlie.resource.commons;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.After;
import net.sourceforge.stripes.action.Before;
import net.sourceforge.stripes.controller.FlashScope;
import net.sourceforge.stripes.controller.LifecycleStage;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.charlie.resource.enums.ActionType;
import com.charlie.security.model.UserModel;

public abstract class BaseActionBean<T> implements ActionBean {
	protected Logger log = Logger.getLogger(this.getClass());

	public static final List<String> INDEX_URLS = Arrays.asList("", "",	"/main.jsp");
	private DynaCMSActionContext context;
	protected T model; // data from DAO
	protected T form; // data from JSP
	protected List<T> models; // collection from DAO without pagination
	protected QueryConditionModel<T> qcModel; // collection from DAO with pagination
	protected ActionType actionType; // insert, update, delete, query
	private String errorMsg = "";
	private String actionBeanRequestURI = "";

	@Before(stages = LifecycleStage.BindingAndValidation)
	private void commonSetting() {
		//StripesRequestWrapper req = (StripesRequestWrapper) getContext().getRequest();

		// 因為回到畫面的 requestURI 會變成 layout.jsp, 所以要在這裡存好 action bean 的 requestURI
		// 為何
//		setActionBeanRequestURI(getContext().getRequestURI());

		if (log.isDebugEnabled()) {
			log.debug("event=" + getContext().getEventName() + "; account=" + getLoginAccount());
		}
	}

	@After(stages = LifecycleStage.BindingAndValidation)
	private void commonSettingMsg() {
		if (StringUtils.isNotBlank(errorMsg)) {
			getContext().setErrorMsg(errorMsg);
		}
	}
	
	/** 複製物件 */
	protected void copyProperties(Object source, Object target) {
		BeanUtils.copyProperties(source, target);
	}

	public DynaCMSActionContext getContext() {
		return context;
	}

	public void setContext(ActionBeanContext context) {
		this.context = (DynaCMSActionContext) context;
	}
	
	public Map<String, Object> getSessionAttributeMap() {
		Map<String, Object> result = new HashMap<String, Object>();
		Enumeration e = getSession().getAttributeNames();
		while (e.hasMoreElements()) {
			String attName = String.valueOf(e.nextElement());
			result.put(attName, getSession().getAttribute(attName));
		}
		return result;
	}

	public HttpSession getSession() {
		return context.getRequest().getSession();
	}

	public HttpServletRequest getRequest() {
		return context.getRequest();
	}

	protected final void setNoCache(HttpServletResponse response) {
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Cache-Control", "no-store");
		response.setDateHeader("Expires", 0);
		response.setHeader("Pragma", "no-cache");
	}

	public List<T> getModels() {
		return models;
	}

	public void setModels(List<T> models) {
		this.models = models;
	}

	public T getModel() {
		return model;
	}

	public void setModel(T model) {
		this.model = model;
	}

	public T getForm() {
		return form;
	}

	public void setForm(T form) {
		this.form = form;
	}

	// for editable/delete page
	protected long sysid;

	public long getSysid() {
		return sysid;
	}

	public void setSysid(long sysid) {
		this.sysid = sysid;
	}

	public UserModel getLoginUser() {
		UserModel user = getContext().getLoginUser();
		if (user == null) {
			user = new UserModel();
		}
		return user;
	}
	
	/** 取得登入者的角色清單 */
//	public Collection<RoleModel> getLoginUserRoles() {
//		UserModel user = this.getLoginUser();
//		return user.getRoles();
//	}

	public String getLoginAccount() {
		return getLoginUser().getAccount();
	}

	public String getContextPath() {

		return getContext().getRequest().getContextPath();
	}

	protected void debugRequest(HttpServletRequest request) {
		if (log.isDebugEnabled()) {
			HttpServletRequest requst = getContext().getRequest();
			Map map = requst.getParameterMap();
			Set set = map.keySet();
			Iterator itr = set.iterator();
			while (itr.hasNext()) {
				String key = (String) itr.next();
				log.debug(key + "=" + request.getParameter(key));
			}
		}
	}

	protected void debugModels() {
		if (log.isDebugEnabled()) {
			for (T m : models) {
				log.debug(m);
			}
		}
	}
	
	protected void debugModel() {
		if (log.isDebugEnabled()) {
			log.debug(model);
		}
	}

	public String getActionBeanRequestURI() {
		return actionBeanRequestURI;
	}

	public void setActionBeanRequestURI(String actionBeanRequestURI) {
		this.actionBeanRequestURI = actionBeanRequestURI;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	protected void saveMessages() {
		if (log.isDebugEnabled()) {
			log.debug("finish ... begin");
		}

		FlashScope flashScope = this.getContext().getCurrentFlashScope();

		if (log.isDebugEnabled()) {
			log.debug("errorMsg=" + this.getContext().getErrorMsg());
			log.debug("infoMsg=" + this.getContext().getInfoMsg());
			log.debug("warnMsg=" + this.getContext().getWarnMsg());
		}

		flashScope.put("errorMsg", this.getContext().getErrorMsg());
		flashScope.put("infoMsg", this.getContext().getInfoMsg());
		flashScope.put("warnMsg", this.getContext().getWarnMsg());

		if (log.isDebugEnabled()) {
			log.debug("finish ... OK");
		}
	}

	/**
	 * 取得domainURL
	 * 
	 * @return
	 */
	protected String getWebPath() {
		String requestURL = getRequest().getRequestURL().toString();
		String requestURI = getRequest().getRequestURI();
		String url = (requestURL.substring(0, requestURL.length() - requestURI.length()) + getRequest().getContextPath());
		return url;
	}


	public QueryConditionModel<T> getQcModel() {
		return qcModel;
	}


	public void setQcModel(QueryConditionModel<T> qcModel) {
		this.qcModel = qcModel;
	}


	public ActionType getActionType() {
		return actionType;
	}


	public void setActionType(ActionType actionType) {
		this.actionType = actionType;
	}
	
}
