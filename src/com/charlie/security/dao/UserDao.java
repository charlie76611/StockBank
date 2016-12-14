package com.charlie.security.dao;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.charlie.resource.commons.GenericIBatisDao;
import com.charlie.security.model.UserModel;
import com.charlie.security.model.enums.UserStatusType;

@Repository
public class UserDao extends GenericIBatisDao<UserModel, Long> {

	public UserDao() {
		super.setNamespace("dynacmsuser");
	}
	
	public UserModel findByAccount(String account) {
		return (UserModel) getSqlMapClientTemplate().queryForObject(
				super.getNamespace()+".findByAccount", account);
	}
	
	public List<UserModel> findByAccounts(List<String> accounts) {
		return getSqlMapClientTemplate().queryForList(super.getNamespace() + ".findByAccounts", accounts);
	}
	
	public List<UserModel> findByRolenames(List<String> rolenames) {
		return getSqlMapClientTemplate().queryForList(super.getNamespace() + ".findByRolenames", rolenames);
	}
	
	/** 修改密碼輸入錯誤之次數累加 */
	public int updateErrTimes(String account, int errorlogincount) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("account", account);
		map.put("errorlogincount", errorlogincount);
		return getSqlMapClientTemplate().update(namespace + ".updateErrTimes", map);
	}
	
	/** 修改帳號的狀態, 目前支援(active, inactive, lock) */
	public int updateStatus(String account, UserStatusType status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("account", account);
		map.put("status", status);
		return getSqlMapClientTemplate().update(namespace + ".updateStatus", map);
	}

	/** 登入成功後, 修改登入資訊 */
	public int updateLoginInfo(String account, String lastloginip, Timestamp lastlogintime) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("account", account);
		map.put("lastloginip", lastloginip);
		map.put("lastlogintime", lastlogintime);
		return getSqlMapClientTemplate().update(namespace + ".updateLoginInfo", map);		
	}
	
	/** 修改備註 */
	public int updateRemark(String account, String remark) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("account", account);
		map.put("remark", remark);
		return getSqlMapClientTemplate().update(namespace + ".updateRemark", map);			
	}

}
