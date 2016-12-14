<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/tags/c.tld" prefix="c" %>  
<%-- <%@ taglib uri="/tags/fmt.tld" prefix="fmt" %>   --%>
<%-- <%@ taglib uri="/tags/fn.tld" prefix="fn" %>   --%>
<%-- <%@ taglib uri="/tags/display.tld" prefix="d" %>  --%>
<%-- <%@ taglib uri="/tags/stripes.tld" prefix="stripes" %> --%>
<%-- <%@ taglib uri="/tags/tiles.tld" prefix="tiles" %> --%>
		
		<c:if test="${not empty requestScope.infoMsg }"> 
            <img alt="infoMsg" src="${pageContext.request.contextPath}/images/msg/icon_success_lrg.gif" border="0"><span style="color:#090;font-size:11px">
				${requestScope.infoMsg }
			</span> <br/>
		</c:if>
		<c:if test="${not empty requestScope.warnMsg }">
            <img alt="warnMsg" src="${pageContext.request.contextPath}/images/msg/icon_warning_lrg.gif" border="0"><span style="color:#c60;font-size:11px">
                ${requestScope.warnMsg }
            </span> <br/>
		</c:if>
		<c:if test="${not empty requestScope.errorMsg }">
            <img alt="errMsg" src="${pageContext.request.contextPath}/images/msg/icon_error_lrg.gif" border="0"><span style="color:red;font-size:11px">
                ${requestScope.errorMsg }
			</span> <br/>
		</c:if>