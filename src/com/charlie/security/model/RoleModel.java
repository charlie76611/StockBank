package com.charlie.security.model;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.charlie.resource.commons.BaseModel;

/**
 * 
 * 角色物件, 
 * 與群組物件不同的地方主要是群組通常用於前台判斷該文章是否可以閱讀/開啟/下載 等等,
 * 而角色主要是負責處理後台管理介面的功能權限.
 * 
 * @author jini
 *
 */
public class RoleModel extends BaseModel {
	

	private String rolename = "";	// 角色名稱 建議採用 adminRole, userRole 命名方式
	private String description = ""; // 角色說明
	
	//private String roledesc = "";
	
	@Override
	public String toString() {
		return new ToStringBuilder(this).appendSuper(super.toString()).append(
				"rolename", rolename).append("description", description)
				.toString();
	}
	
	
	public String getRoledesc() {
		return description + "("+rolename+")";
	}
	
	
	/**
	 * the name of role, ie, adminRole, userRole and etc..
	 * @return
	 */
	public String getRolename() {
        if ( rolename == null ) {
            rolename = "";
        }
		return rolename;
	}
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	/**
	 * some description for rolename, shortly to say it
	 * @return
	 */
	public String getDescription() {
        if ( description == null ) {
            description = "";
        }
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
