package com.charlie.resource.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.charlie.resource.commons.SessionConstants;
import com.charlie.security.model.UserModel;

public class SecurityFilter implements Filter {
	
	private static final Logger log = Logger.getLogger(SecurityFilter.class.getName());

	private String loginURL = "/auth.action";
	
	private String doLoginURL = "/auth.action?login=1";
	
	public static String userSession = "softleaderUser";
	
	private String[] resourcePaths = new String[] {"/css/","/js/","/images/","/static/","/login.jsp"};
	
	@Override
	public void init(FilterConfig config) throws ServletException {
//		String configLoginURL = config.getInitParameter("loginURL");
//		if(SlBeanUtil.isNotEmpty(configLoginURL)) {
//			loginURL = configLoginURL;
//		}
//		
//		String configUserSession = config.getInitParameter("userSession");
//		if(SlBeanUtil.isNotEmpty(configUserSession)) {
//			userSession = configUserSession;
//		}
		
	}
	

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain fc) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		HttpSession session = req.getSession(true);
		
		UserModel loginUser = (UserModel) session.getAttribute(SessionConstants.LoginUser);
		
		res.setHeader("Pragma", "no-cache");
		res.setHeader("Cache-Control", "no-cache");
		res.setDateHeader("Expires", 0);
				
		log.debug("RequestURI='"+req.getRequestURI()+"'; RequestMethod='"+req.getMethod()+"'");
		
		for(String resourcePath:resourcePaths) {
			if( req.getRequestURI().contains(resourcePath) ) {
				log.debug("Resources folders");
				fc.doFilter(request, response);
				return;
			}
		}
		
		// ignore paths
		if( req.getRequestURI().contains(loginURL) || req.getRequestURI().contains(doLoginURL) ) {
			log.debug("filter auto pass login");
			fc.doFilter(request, response);
			return;
		}
		
		
		if( loginUser == null || loginUser.getSysid() <= 0) {
			session.removeAttribute(userSession);
//			req.getRequestDispatcher(loginURL).forward(request, response);
			res.sendRedirect(req.getContextPath()+"/login.jsp");
			log.debug("forward to login page");
			//fc.doFilter(request, response);
			return;
		} else {
			//log.debug("loginUser="+loginUser.getAccount());
			fc.doFilter(request, response);
			return;
		}
		
	}

	@Override
	public void destroy() {
		
	}


}
