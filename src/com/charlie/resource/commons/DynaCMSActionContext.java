package com.charlie.resource.commons;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.FileBean;
import net.sourceforge.stripes.controller.FlashScope;
import net.sourceforge.stripes.controller.StripesRequestWrapper;

import org.apache.log4j.Logger;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.charlie.security.model.UserModel;

public class DynaCMSActionContext extends ActionBeanContext {
    //Log log = Log.getInstance(DynaCMSActionContext.class);
	private Logger log = Logger.getLogger(this.getClass()); 
    // Message
    private String infoMsg;
    private String warnMsg;
    private String errorMsg;

    public String getInfoMsg() {
        return infoMsg;
    }

    public void setInfoMsg(String infoMsg) {
        this.infoMsg = infoMsg;
    }

    public String getWarnMsg() {
        return warnMsg;
    }

    public void setWarnMsg(String warnMsg) {
        this.warnMsg = warnMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public UserModel getLoginUser() {
        return (UserModel) getRequest().getSession().getAttribute(SessionConstants.LoginUser);
    }

    public void setLoginUser(UserModel user) {
        getRequest().getSession().setAttribute(SessionConstants.LoginUser, user);
    }

    public void logout() {
        getRequest().getSession().invalidate();
    }

    public String getRequestURI() {
        if ( log.isDebugEnabled() ) {
            log.debug("the request uri = " + this.getRequest().getRequestURI());
            log.debug("the request querystring = " + this.getRequest().getQueryString());
        }
        return getRequest().getRequestURI();
    }

    public FlashScope getCurrentFlashScope() {
        return FlashScope.getCurrent(getRequest(), true);
    }

    public String getContextPath() {
        return getRequest().getContextPath();
    }

    public void putDataSourceIntoRequest() {
        getRequest().setAttribute("dataSource", WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext()).getBean("dataSource"));
    }

    public void setQueryAttribute(String attrName, String value) {
        getRequest().getSession().setAttribute(attrName, value);
    }

    public String getQueryAttribute(String attrName) {
        Object result = getRequest().getSession().getAttribute(attrName);
        return String.valueOf( result == null ? "" : result );
    }

    public Map<String, String[]> getParameterMap() {
        return getRequest().getParameterMap();
    }

    public void putSessionAttribute(String key, Object value) {
        getRequest().getSession().setAttribute(key, value);
    }

    public Object getSessionAttribute(String key) {
        return getRequest().getSession().getAttribute(key);
    }
    
    public void removeSessionAttribute(String key) {
    	getRequest().getSession().removeAttribute(key);
    }

    public void putSessionAttributes(Map<String, Object> queryFieldValuesToPut) {
        for (Map.Entry<String, Object> entry : queryFieldValuesToPut.entrySet()) {
            getRequest().getSession().setAttribute(entry.getKey(), entry.getValue());
        }
    }

    public String[] getParameterValues(String paramName) {
        return getRequest().getParameterValues(paramName);
    }

    public String getParameter(String paramName) {
        return getRequest().getParameter(paramName);
    }

    public FileBean getFileParameterValue(String paramName) {
        StripesRequestWrapper req = (StripesRequestWrapper) getRequest();
        FileBean theFile = req.getFileParameterValue(paramName);
        return theFile;
    }

    public String getRealPath(String path) {
        return getRequest().getSession().getServletContext().getRealPath(path);
    }

    public OutputStream getResponseOutputStream() throws IOException {
        return getResponse().getOutputStream();
    }
    
    // add by JACKLEE (2011. May)
    public Object getRequestAttribute(String key) {
    	return getRequest().getAttribute(key);
    }
    
    public void putRequestAttribute(String key, Object value) {
    	getRequest().setAttribute(key, value);
    }
    
    public void removeRequestAttribute(String key) {
    	getRequest().removeAttribute(key);
    }
    
    public Object getFlashAttribute(String key) {
    	return getCurrentFlashScope().get(key);
    }
    
    public void putFlashAttribute(String key, Object value) {
    	getCurrentFlashScope().put(key, value);
    }

}
