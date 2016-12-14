package com.charlie.security.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.charlie.exceptions.ApplicationException;
import com.charlie.resource.commons.QueryConditionModel;
import com.charlie.resource.util.DateUtil;
import com.charlie.security.dao.UserDao;
import com.charlie.security.model.UserModel;
import com.charlie.security.model.enums.UserStatusType;
import com.softleader.util.Base64Util;
import com.softleader.util.DateTimeUtil;
import com.softleader.util.HashUtil;


/** 2011/06 重新定義密碼規則(JACK.LEE)
 * 1. 密碼不以明碼形式存於資料庫中
 * 2. 採用Base64與Hash兩種編碼method, 將密碼予以混碼, 其中Base64可逆, Hash不可逆
 * 3. 不同帳號若使用相同密碼, 即使經過混碼後, 儲存的密碼(亂碼)也相同, 為避免有心人士利用此漏洞, 
 *     故真正儲存的 密碼混碼= 編碼Method(帳號+@@@+密碼明碼) */
@Service
public class UserService {
    private Logger log = Logger.getLogger(this.getClass());
    private static String DELIM_FLAG = "@@@";
    private static char[] EVIL_PWD_CHAR = {' ', '\"', '\''};

//    @Autowired
//    private RoleDao roleDao;
	
	@Autowired
	private UserDao userDao;

//	@Autowired
//	private UserRoleDao userRoleDao;

	/**
	 * 取得所有的使用者資料
	 * 
	 * @return
	 * @throws Exception 
	 */
	public List<UserModel> getAll() throws Exception {
		return userDao.findAll();
	}

	/**
	 * 根據傳入的分頁, 排序, 關鍵字等訊息回傳使用者資料
	 * @see QueryConditionModel
	 * @param paginationContext
	 * @return
	 * @throws Exception 
	 */
	public List<UserModel> getByQueryCondition(QueryConditionModel qcModel) throws Exception {
		return userDao.findByQueryCondition(qcModel);
	}
	
	public int getTotalCount(QueryConditionModel qcModel) {
		return userDao.findTotalCount(qcModel);
	}

//    public UserModel findUserWithRoleByUserid(long userid) {
//        UserModel user = userDao.findById(userid);
//        List<UserRoleModel> userRoleList = userRoleDao.findByUserId(userid);
//        List<Long> roleIdList = new ArrayList<Long>();
//        for (UserRoleModel userRole : userRoleList) {
//            roleIdList.add( userRole.getRoleid() );
//        }
//        user.setRoles( roleDao.findByIds(roleIdList.toArray(new Long[roleIdList.size()])) );
//        return user;
//    }
	
	/** 根據sysid查詢 */
	public UserModel getById(long sysid) {
		UserModel user = userDao.findById(sysid);
		// 使用前先處理密碼(密碼是以亂碼儲存於DB): 主要是將base64密碼還原
		restorePwd(user);
		user.setChgPwdFlag(isChangePwd(user)); // 判斷是否需要強制更改密碼
		return user;
	}
	
	/** 根據帳號查詢 */
	public UserModel getByAccount(String account) {
		UserModel user = userDao.findByAccount(account);
		if (user == null) {
			return null;
		}
		// 使用前先處理密碼(密碼是以亂碼儲存於DB): 主要是將base64密碼還原
		restorePwd(user);
		user.setChgPwdFlag(isChangePwd(user)); // 判斷是否需要強制更改密碼
		return user;
	}
	
	/**
	 * 以帳號取得所有符合的userModel
	 * 
	 * @param rolenames
	 * @return
	 */
	public List<UserModel> getByAccounts(List<String> accounts) {
		if (accounts == null || accounts.size() == 0) {
			return new ArrayList<UserModel>();
		}
		return userDao.findByAccounts(accounts);
	}
	
	/**
	 * 以角色名稱取得所有符合的userModel
	 * 
	 * @param rolenames
	 * @return
	 */
	public List<UserModel> getByRolenames(List<String> rolenames) {
		if (rolenames == null || rolenames.size() == 0) {
			return new ArrayList<UserModel>();
		}
		return userDao.findByRolenames(rolenames);
	}

	/**
	 * 新增使用者, 先判斷帳號是否有重複 另外針對 user-role-mapping 進行 delete-insert
	 * @Todo add log
	 * @param model
	 * @throws Exception 
	 */
	public synchronized UserModel insert(UserModel model, String account) throws Exception {
		UserModel user = getByAccount(model.getAccount());
		if (user != null) {
			throw new ApplicationException("ERROR:帳號已存在!");
		}

		if (StringUtils.isEmpty(model.getAccount())) {
			throw new ApplicationException("ERROR:帳號不允許空白!");
		}

		if (StringUtils.isEmpty(model.getPassword())) {
			throw new ApplicationException("ERROR:密碼不允許空白!");
		}
		String msg = null;
		
		// 進行帳號檢核
		msg = StringUtils.trimToEmpty(verifyAct(model.getAccount()));
		if (msg.length() != 0) { // 若msg有值
			throw new ApplicationException(msg);
		}
		// 在處理密碼前, 先將密碼取出
		String origPwd = model.getPassword();		
		// 儲存前, 進行密碼前置處理(因為不允許密碼以明碼方式儲存於DB)
		model = handlePwd(model);		
		// 進行檢核密碼規則
		msg = StringUtils.trimToEmpty(verifyPwd(origPwd, model)); // 回傳檢核規則之msg
		if (msg.length() != 0) { // 若msg有值
			throw new ApplicationException(msg);
		}
		
		//model.setStatus(UserStatusType.ACTIVE);
		model.setLastpassword(initLastPwd(model.getBase64())); // 初始"密碼歷程字串"
		//model.setLastchgpwdtime(CMSUtils.day99991231()); // 預設值, 當作首登入時強制變更密碼的依據
		userDao.insert(model, account);
//		userRoleDao.deleteByUserid(model.getSysid()); // 理論上 delete count = 0
//		for (RoleModel role : model.getRoles()) {
//			userRoleDao.insert(model, role);
//		}
        return model;
	}
	
	/** 修改 
	 * @throws Exception */
	private void update(UserModel user, String account) throws Exception {
		// 在處理密碼前, 先將密碼取出
		String origPwd = user.getPassword();
		// 儲存前, 進行密碼前置處理
		user = handlePwd(user);		
		// 檢查密碼是否變更, 若有, 則進行密碼檢核程序	
		String base64Pwd = user.getBase64(); // 經base64編碼之密碼
		String lastPwd = StringUtils.trimToEmpty(user.getLastpassword()); // 密碼歷程字串	
		String preLastPwd = getCurrentLastPwd(lastPwd); // 從密碼歷程字串中, 取出最近一次的base64密碼
		log.info("origPwd:" + origPwd);
		log.info("base64Pwd:" + base64Pwd);
		log.info("lastPwd:" + lastPwd);	
		log.info("preLastPwd:" + preLastPwd);
		
		if (!preLastPwd.equals(base64Pwd)) { // 若密碼歷程中的最近1次base64, 跟從頁面接收的base64不同, 判斷為有更改密碼
			// 進行檢核密碼規則
			String msg = StringUtils.trimToEmpty(verifyPwd(origPwd, user)); // 回傳檢核規則之msg
			if (msg.length() != 0) { // 若msg有值
				throw new ApplicationException(msg);
			}
			user.setLastpassword(rebuildLastPwd(base64Pwd, lastPwd)); // 若通過檢核, 重組密碼歷程字串(供下次變更密碼之檢核)
			user.setLastchgpwdtime(DateUtil.nowTimestamp());
		}		
		userDao.update(user, account);
	}	


    public void updateWithoutRoles(UserModel user, String account) throws Exception {
        //userDao.update(user, account);
    	this.update(user, account);
    }

	/**
	 * 修改使用者資料, 並且對於 user-role-mapping 進行修改
	 * @param model
	 * @throws Exception 
	 */
	public void updateWithRoles(UserModel model, String account) throws Exception {
		//userDao.update(model, account);
		this.update(model, account);

//		userRoleDao.deleteByUserid(model.getSysid());
//		for (RoleModel role : model.getRoles()) {
//			userRoleDao.insert(model, role);
//		}
	}

	public void deleteUser(long sysid, String account) throws Exception {
		userDao.delete(sysid, account);
		//userRoleDao.deleteByUserid(sysid);
	}
	
	/** 解鎖
	 * 1. 更改status狀態欄位
	 * 2. 將錯誤次數歸零 */
	public void unlockUser(String account) {
		updateStatus(account, UserStatusType.ACTIVE);
		updateErrTimes(account, 0);
	}

	/**
	 * 登入, 為了避免 SQL injection, 應該是拿出資料判斷 password
	 *  若是密碼錯誤, 要累加錯誤次數, 並且檢查錯誤次數是否達到鎖住上限
	 *  若是登入成功, 更新登入時間, IP, 並將錯誤次數歸0, 並且檢查是否要強制變更密碼(首登 or 有效期限到期)
	 * @param account
	 * @param password
	 * @return
	 * @throws ApplicationException
	 */
	public UserModel login(String account, String password, String clientIP) throws ApplicationException {
		if (StringUtils.isEmpty(account)) {
			throw new ApplicationException("ERROR:帳號不允許空白!");
		}

		if (StringUtils.isEmpty(password)) {
			throw new ApplicationException("ERROR:密碼不允許空白!");
		}
		// 根據帳號查詢
		UserModel user = getByAccount(account);
		
		if (user == null || user.getSysid() <= 0) {
			throw new ApplicationException("ERROR:帳號不存在!");
		}
		
		if (!(UserStatusType.ACTIVE.toString()).equalsIgnoreCase(user.getStatus().toString())) {
			throw new ApplicationException("ERROR:帳號已被停用或鎖定, 請聯絡系統管理員!");
		}
		
		if (user.getPassword().equals(password)) { // 比對密碼OK, 登入成功!!
//            List<UserRoleModel> userRoles = userRoleDao.findByUserId(user.getSysid());
//            List<Long> roleids = new ArrayList<Long>();
//            for (UserRoleModel userRole : userRoles) {
//                roleids.add( userRole.getRoleid() );
//            }
            user.setLastloginip(clientIP);
            user.setLastlogintime(DateUtil.nowTimestamp());
            this.updateLoginInfo(account, user.getLastloginip(), user.getLastlogintime()); // 登入成功, 更新一些登入資訊
            this.updateErrTimes(user.getAccount(), 0); // 將錯誤次數歸零
//            user.setRoles( roleDao.findByIds(roleids.toArray(new Long[roleids.size()])) );
			return user;
		} else {
			int errTimes = handleWrongPwd(user); // 若使用者輸入錯誤密碼, 進行後續紀錄及處理
			int configErrTimes = 5; //AppConstants.appPassword.getErrTimes();
			String msg = null;
			if (errTimes >= configErrTimes) {
				msg = "錯誤次數已達" + configErrTimes + "次, 帳號已鎖住";
				String remark = StringUtils.trimToEmpty(user.getRemark()) + "\n" + msg + "(from:" + clientIP + ", datetime:" + DateUtil.nowTimestamp() +")";
				this.updateRemark(account, remark);
			} else {
				msg = "錯誤次數已達" + errTimes + "次";
			}
			throw new ApplicationException("ERROR:密碼輸入錯誤 (" + msg + ")!");
		}
	}
	
	/** 修改密碼輸入錯誤之次數累加 */
	public int updateErrTimes(String account, int errorlogincount) {
		return userDao.updateErrTimes(account, errorlogincount);
	}
	
	/** 修改帳號的狀態, 目前支援(active, inactive, lock) */
	public int updateStatus(String account, UserStatusType status) {
		return userDao.updateStatus(account, status);
	}
	
	/** 登入成功後, 修改登入資訊 */
	public int updateLoginInfo(String account, String clientIP, Timestamp time) {
		return userDao.updateLoginInfo(account, clientIP, time);
	}
	
	/** 修改備註 */
	public int updateRemark(String account, String remark) {
		return userDao.updateRemark(account, remark);
	}

    public List<UserModel> getByRoleid(long roleid) {
        List<UserModel> result = new ArrayList<UserModel>();
//        List<UserRoleModel> userRoleList = userRoleDao.findByRoleId(roleid);
//        for (UserRoleModel userRole : userRoleList) {
//            UserModel user = userDao.findById(userRole.getUserid());
//            if ( user == null ) {
//                continue;
//            }
//            result.add( user );
//        }
        return result;
    }    
    
    //--------------------------------- private method ------------------------------------------------
    /** 判斷帳號的規則
     * (1) 帳號不可以空白
     * (2) 帳號長度只能 3 至 16 個字元
     * (3) 帳號只能是數字,英文字母及「_」「.」等符號,其他的符號都不能使用
     * (4)「_」符號不可相連
     * (5)「_」符號不可在最後
     * */	
	private String verifyAct(String account) {
		String msg = "";
        if (account.length() == 0) {
            msg = "ERROR:帳號不允許空白!";
        } else {
            if (account.length() < 3 || account.length() > 16) {
            	msg = "ERROR:帳號長度要介於3~16!";
            }
            for (int i = 0; i < account.length(); i++) {
                if (account.charAt(i) == '.') {
                    continue;
                }
                if (!((account.charAt(i) >= 'a' && account.charAt(i) <= 'z') || (account.charAt(i) >= '0' && account.charAt(i) <= '9') || (account.charAt(i) == '_'))) {
                	msg = "ERROR:帳號只能是數字和英文字母!";
                    break;
                }
                if (account.charAt(i) == '_' && account.charAt(i - 1) == '_') {
                	msg = "ERROR:「_」符號不可相連!";
                    break;
                }
            }
            if (account.charAt(account.length() - 1) == '_') {
            	msg = "ERROR:「_」符號不可在最後!";
            }
        }		
		return msg;
	}
	
	/** 驗證密碼規則 */
	private String verifyPwd(String origPwd, UserModel user) {
		String account = user.getAccount();
		String base64Pwd = user.getBase64();
		String lastPwd = StringUtils.trimToEmpty(user.getLastpassword());	
		boolean passFlag = true;
		String msg = "";
		// 檢核1. 密碼長度
		int minLength = 8;// AppConstants.appPassword.getLength();
		int maxLength = 20;// AppConstants.appPassword.getMaxLength();
		if (minLength != -1) {
			if (origPwd.length() < minLength) {
				passFlag = false;
				msg = "ERROR:密碼長度不足!";
			} else if (origPwd.length() > maxLength) {
				passFlag = false;
				msg = "ERROR:密碼長度超過!";
			}
		}
		
		// 檢核2. 不得與前幾代重複
		int configKeepTimes = 3;// AppConstants.appPassword.getKeepTimes();
		if (passFlag && configKeepTimes != -1) {
			boolean isRepeat = compareLastPwd(base64Pwd, lastPwd, configKeepTimes);
			if (isRepeat) {
				passFlag = false;
				msg = "ERROR:密碼不得與近幾代重複!";
			}
		}
		
		// 檢核3. 要英數夾雜
		boolean isEngNum = false;// AppConstants.appPassword.isEngNum();
		if (passFlag && isEngNum) {
			if (!isEngNum(origPwd)) {
				passFlag = false;
				msg = "ERROR:密碼必須英數夾雜!";
			}
		}
		
		// 檢核4. 不能與帳號相同
		if (passFlag) {
			if (account.equals(origPwd)) {
				passFlag = false;
				msg = "ERROR:密碼不得與帳號相同!";
			}
		}
		
		// 檢核5. 不得為易猜密碼
//		if (passFlag) {
//			String excludePwd = StringUtil.trimToEmpty(AppConstants.appPassword.getExcludePwd());
//			if (excludePwd.indexOf(origPwd) != -1) {
//				passFlag = false;
//				msg = "ERROR:密碼不得為易猜!";
//			}
//		}
		
		// 檢核6. 不得有特殊符號
		if (passFlag) {
			if (isSpecialChar(origPwd)) {
				passFlag = false;
				msg = "ERROR:密碼不得有特殊符號!";
			}
		}
		
		return msg;
	}
	
	/** 判斷是不是英數雜夾 */
	private boolean isEngNum(String str) {
		boolean flag = false;
		String engRE = "^[A-Za-z]+$";
		String numRE = "^[0-9]+$";
		String engNumRE = "^[A-Za-z0-9]+$";
		//log.info(str.matches(engNumRE));
		//log.info(str.matches(engRE));
		//log.info(str.matches(numRE));
		if (str.matches(engNumRE)) { // 一定要符合英文+數字
			if (!str.matches(engRE) && !str.matches(numRE)) { // 但不能全部是英文, 且不能全部是數字
				flag = true;
			}
		}
		return flag;
	}
	
	/** 是否有包括特殊符號 */
	private boolean isSpecialChar(String str) {
		boolean flag = false;
        for (int i = 0; i < str.length(); i++) {
        	for (int j=0; j<EVIL_PWD_CHAR.length; j++) {
        		if (str.charAt(i) == EVIL_PWD_CHAR[j]) {
        			flag = true;
        			break;
        		}
        	}
        }		
		return flag;
	}
	
	/** 新增帳號時, 賦予預設的之前密碼代數(有5代), 皆為目前密碼
	 * 格式為: base64密碼@@@base64密碼@@@......... 共5組,
	 * 由左而右為現代, 前1代, 前2代.....(依此類推) */
	private String initLastPwd(String base64Pwd) {
		StringBuffer sbf = new StringBuffer();
		for (int i=0; i<5; i++) {
			sbf.append(base64Pwd + DELIM_FLAG);
		}
		return sbf.toString();
	}
	
	/** 修改密碼時, 除了更改密碼欄位外, 也要重建之前密碼代數的欄位,
	 * 規則: 將新的base64密碼加入最左邊, 將最右邊(前5代)移除 */
	private String rebuildLastPwd(String base64Pwd, String lastPwd) {
		//log.info("base64Pwd:" + base64Pwd);
		//log.info("lastPwd:" + lastPwd);
		Map<String, String> lastPwdMap = convertString2Map(lastPwd, DELIM_FLAG);
		StringBuffer sbf = new StringBuffer();
		sbf.append(base64Pwd);
		for (int i=1; i<5; i++) {
			String value = lastPwdMap.get((i-1) + "");
			sbf.append(DELIM_FLAG + value);
		}
		//log.info("rebuildLastPwd:" + sbf.toString());
		return sbf.toString();
	}
	
	/** 從密碼歷程中, 取出最近一次的base64密碼 */ 
	private String getCurrentLastPwd(String lastPwd) {
		String result = "";
		lastPwd = StringUtils.trimToEmpty(lastPwd);
		int index = lastPwd.indexOf(DELIM_FLAG);
		if (lastPwd.length() != 0 && index != -1) {
			result = lastPwd.substring(0, index);
		}
		return result;
	}
	
	/** 進行密碼代數比對, 檢核是否跟前幾代重複 */
	private boolean compareLastPwd(String base64Pwd, String lastPwd, int configValue) {
		boolean flag = false;
		Map<String, String> lastPwdMap = convertString2Map(lastPwd, DELIM_FLAG);
		if (configValue == -1) {
			return flag; // 表示不啟用檢核之前密碼
		}
		for (int i=0; i<configValue; i++) {
			String itemPwd = StringUtils.trimToEmpty(lastPwdMap.get(i+""));
			//log.info("[key]:" +i + ", [lastPwd]:" + itemPwd + "#####" + "[base64Pwd]:" + base64Pwd);
			if (itemPwd.equals(base64Pwd)) {
				flag = true; // 表示新密碼跟以前的密碼有重複
				break;
			}
		}		
		return flag;
	}
	
	/** 將字串轉成Map, Map的key是數字序號(String) */
	private Map<String, String> convertString2Map(String values, String delim) {
		Map<String, String> map = new TreeMap<String, String>();
		StringTokenizer s = new StringTokenizer(values, delim);
		int i = 0;
		while (s.hasMoreTokens()) {
			String item = s.nextToken();
			map.put(i+"", item);
			//log.info("[key]:" +i + ", [value]:" + item + "!!!!!");
			i ++;
		}
		return map;		
	}
	
	/** 儲存密碼前置處理: add by JACK.LEE 建議密碼不要以明碼儲存於DB */
	private UserModel handlePwd(UserModel model) {
		String account = StringUtils.trimToEmpty(model.getAccount());
		String password = StringUtils.trimToEmpty(model.getPassword());
		String value = account + DELIM_FLAG + password;
		String base64Pwd = Base64Util.encodeBase64(value);
		String hashPwd = HashUtil.hash(value);
		model.setBase64(base64Pwd); // 可逆
		model.setPassword(hashPwd); // 不可逆		
		return model;
	}
	
	/** 查詢出來後, 要對密碼進行內部處理
	 * 因為DB的密碼欄位之規則= 混碼Method(帳號+"@@@"+密碼) */
    private UserModel restorePwd(UserModel model) {
    	if (model == null) {
    		return null;
    	}
        String base64 = StringUtils.trimToEmpty(model.getBase64());
        String value = Base64Util.decodeBase64(base64);
        int index = value.indexOf(DELIM_FLAG);
        if (index != -1) {
        	String account = value.substring(0, index);
        	String password = value.substring(index+3);
        	model.setPassword(password);
        	log.info("[account]:" + account + "; [password]:" + password + "=====");
        }
        return model;
    }	
    
	/** 輸入錯誤密碼的後續處理
	 * 1. 累加錯誤次數
	 * 2. 檢查累加次數是否達上限(鎖住帳號) */
	private int handleWrongPwd(UserModel user) {
		int count = user.getErrorlogincount();
		count = count + 1;
		// 進行累加
		updateErrTimes(user.getAccount(), count);
		
		int configErrTimes = 5;// AppConstants.appPassword.getErrTimes();
		if (configErrTimes != -1) { // 表示有啟用此設定
			if (count >= configErrTimes) { // 若錯誤次數達上限, 鎖住帳號
				updateStatus(user.getAccount(), UserStatusType.LOCK);
			}
		}		
		return count;
	}
	
	/** 判斷是否需要強制更改密碼
	 * 目前需要更改的情況有: 首登, 密碼期限到期 */
	private boolean isChangePwd(UserModel user) {
		boolean flag = false;
		String account = user.getAccount();
		Timestamp lastchgpwdtime = user.getLastchgpwdtime(); // 上次更改密碼時間(新增帳號時, 預設值為9999/12/31)
		Timestamp now = DateUtil.nowTimestamp(); // 系統現在時間

		if (lastchgpwdtime.after(now)) { // 若晚於now, 表示建立帳號後尚未更改過密碼(即首登)
			log.info("===== 首登 =====" + account);
			flag = true;
		} else {
			int configPeriod = -1;// AppConstants.appPassword.getPeriod(); // 密碼的有效天數
			if (configPeriod != -1) { // 表示啟用這個設定
				try {
					Date base = DateTimeUtil.convertTimestamp2Date(lastchgpwdtime);
					Date compare  = DateTimeUtil.convertTimestamp2Date(now);
					long differ = DateTimeUtil.differ(base, compare);
					log.info("===== 距離上次變更密碼, 已經過了 " + Math.abs(differ) + " 天 =====" + account);
					if (Math.abs(differ) > configPeriod) {
						flag = true;
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
		return flag;
	}   	
	 
}
