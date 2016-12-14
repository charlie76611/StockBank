package com.charlie.security.web;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.integration.spring.SpringBean;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;

import com.charlie.resource.commons.BaseActionBean;
import com.charlie.resource.commons.SessionConstants;
import com.charlie.security.model.UserModel;
import com.charlie.security.service.UserService;
import com.charlie.transaction.service.StockTransService;

@UrlBinding("/auth.action")
public class AuthActionBean extends BaseActionBean<UserModel> {

    public static final String LOGIN_PAGE = "/login.tiles";
    private String stockNumber;
	private String stockPrice;
	
	@SpringBean
	private StockTransService service;
	
	public Resolution getStockInfo() {
		
		try {
			stockPrice = service.getOneStockInfomation(stockNumber);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ForwardResolution("/defaultMain.tiles");
	}

	public String getStockNumber() {
		return stockNumber;
	}

	public void setStockNumber(String stockNumber) {
		this.stockNumber = stockNumber;
	}

	public String getStockPrice() {
		return stockPrice;
	}

	public void setStockPrice(String stockPrice) {
		this.stockPrice = stockPrice;
	}
    /*
     * CMS 登入登出說明:
     * 1. 登入 login
     *  1.1. SSO 登入 : 如果目前沒在 SSO 登入狀態就導向 SSO 登入畫面, 是登入狀態就取 user 資料進 session
     *  1.2. CMS 登入 : 直接取 username/password 進行登入, 登入失敗就導向 CMS 的登入頁面
     *
     * 2. 登出 logout : 一律 session invalidate 之後導向 login method. 由登入的 method 來判斷要導向哪一頁
     */

    private String account;
	private String password;
	
	@SpringBean
	private UserService userService;
//	@SpringBean
//	private UserSessionService userSessionService; // add by JACK.LEE 2012/2/11
	
	@DefaultHandler
	public Resolution login() {
		String clientIP = getRequest().getRemoteAddr();
		log.debug("Welcome to login ip : " + clientIP);
		try {
			if ( StringUtils.isNotBlank(account) && StringUtils.isNotBlank(password) ) {
            	// 帳號密碼不分區大小寫 by JACK
				log.debug("account : " + account + " password : " + password);
            	account = account.toLowerCase();
            	password = password.toLowerCase();
                model = userService.login(account, password, clientIP);
            } else {
            	//getSession().invalidate();
                return new RedirectResolution(LOGIN_PAGE);
            }
		} catch ( Exception ex) {	
			log.error("登入失敗. account=" + account + ", password=" + password); // 登入失敗, 記錄 user 輸入的資訊
            log.error(ExceptionUtils.getFullStackTrace(ex));
			return new RedirectResolution(LOGIN_PAGE);
		}
    	
		if( model != null ) {
			// 如果通過登入的帳號密碼檢核, 在將登入資訊放入session時,
			// 先確認session中是否已有登入資訊, 若有, 已存在的帳號要跟, 即將塞入session的帳號, 屬於同一個
//			if (getContext().getLoginUser() != null) {
//				UserModel origModel = getContext().getLoginUser();
//				String origAccount = origModel.getAccount();
//				if (!account.equals(origAccount)) {
//					log.error("登入失敗. account=" + account + ", 該次登入的SessionID發生衝突, 系統判斷為開多分頁且用不同帳號登!!!");
//					this.getContext().setErrorMsg("登入失敗[系統判斷登入發生衝突, 請勿開啟多個瀏覽器分頁進行登入!!]");
//					saveMessages();
//					return new RedirectResolution(LOGIN_PAGE);							
//				}
//			}
			getContext().setLoginUser(model);
		}
		
		// 若必須強制修改密碼, 則導引至修改密碼頁面 (add by JACK.LEE 2011.June) 
//		if (model != null && model.isChgPwdFlag()) {
//			return new ForwardResolution("/profile/userdata/password/edit.tiles");
//		}

//        return new ForwardResolution(BaseActionBean.INDEX_URLS.get(2)); 
		return new ForwardResolution("/defaultMain.tiles");
	}
	
	public Resolution goHome() {
		return new ForwardResolution("/defaultMain.tiles");
	}
	
//	/** 強制修改密碼頁面 */
//	public Resolution pwdEdit() {
//        try {        	
//        	sysid = this.getLoginUser().getSysid();
//            model = userService.getById(sysid);            
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            log.error("unexpected exception", ex);
//        }
//        return new ForwardResolution("/profile/userdata/password/edit.tiles");		
//	}
	
//	/** 執行密碼修改(only密碼) */
//    public Resolution pwdUpdate() {
//        try {        	
//        	String passwordConfirm = StringUtil.trimToEmpty(getRequest().getParameter("passwordConfirm"));
//        	
//        	if( !model.getPassword().equals(passwordConfirm) ) {
//        		throw new ApplicationException("ERROR:密碼驗證與密碼不符");
//        	}
//        	
//        	sysid = this.getLoginUser().getSysid();
//            UserModel dbmodel = userService.getById(sysid); 
//            if( StringUtils.isNotBlank(model.getPassword())) {
//            	// 帳號密碼不分區大小寫 by JACK.LEE
//                dbmodel.setPassword(StringUtil.trimToEmpty(model.getPassword()).toLowerCase());
//            }
//            userService.updateWithoutRoles(dbmodel, getLoginAccount());
//            dbmodel = userService.getById(sysid); // 再重新查詢
//            getContext().setLoginUser(dbmodel); // 覆寫session的UserModel
//            this.getContext().setInfoMsg("修改成功");
//            
//        } catch (ApplicationException ex) {
//        	ex.printStackTrace();
//            log.error(StringUtil.getStackTraceAsString(ex));
//            this.getContext().setErrorMsg("修改失敗 ["+ex.getMessageCode()+"]");
//            
//        } catch (SystemException ex) {
//        	ex.printStackTrace();
//            log.error(StringUtil.getStackTraceAsString(ex));
//            this.getContext().setErrorMsg("修改失敗 ["+ex.getMessageCode()+"]");
//        	
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            log.error(StringUtil.getStackTraceAsString(ex));
//            this.getContext().setErrorMsg("修改失敗 [error.unknown]");
//        }
//        
//        saveMessages();
//        
//        return new RedirectResolution(getClass(),"pwdEdit").flash(this);
//
//    }

	public Resolution logout() {
		model = (UserModel)getSession().getAttribute(SessionConstants.LoginUser);
		if (model != null) {
			// 登出時, 根據帳號刪除Session表格控管的資料 add by JACK.LEE 2012/2/11
			//================================
		}
		super.getSession().removeAttribute(SessionConstants.LoginUser); // 觸發attributeRemoved
		super.getSession().invalidate(); // 觸發sessionDestroyed 
		return new RedirectResolution(LOGIN_PAGE);
	}
	
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
