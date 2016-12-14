package com.charlie.security.model;

import java.sql.Timestamp;
import java.util.Collection;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.charlie.resource.commons.BaseModel;
import com.charlie.security.model.enums.UserStatusType;

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
 * <td>2013/10/11</td>
 * <td>使用者Model</td>
 * <td>t00030</td>
 * </tr>
 * </table> 
 *
 * @author t00030
 *
 */
public class UserModel extends BaseModel {	
	
	private String account = "";
	private String password = "";
	private String username = "";
	private String nickname = "";
	private String email = "";
    private String unitname = "";
	private Timestamp lastlogintime;
	private String lastpassword = "";
	private Timestamp lastchgpwdtime;
	private int errorlogincount;
	
	// add by JACK.LEE 2011/06/01
	private String base64;
	private String lastloginip;
	private String remark;
	private boolean chgPwdFlag; // 是否必須更改密碼
	private UserStatusType status = UserStatusType.ACTIVE; // active, inactive, lock
	
	private Collection<RoleModel> roles;	
	
	@Override
	public String toString() {
        return ToStringBuilder.reflectionToString(this);
	}

    public String getUserlabel() {
        return getAccount() + "(" + getUsername() + ")";
    }

	public String getAccount() {
        if ( account == null ) {
            account = "";
        }
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
        if ( password == null ) {
            password = "";
        }
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
        if ( username == null ) {
            username = "";
        }
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getNickname() {
        if ( nickname == null ) {
            nickname = "";
        }
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getEmail() {
        if ( email == null ) {
            email = "";
        }
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Timestamp getLastlogintime() {
		return lastlogintime;		
	}
	public void setLastlogintime(Timestamp lastlogintime) {
		this.lastlogintime = lastlogintime;
	}
	public String getLastpassword() {
        if ( lastpassword == null ) {
            lastpassword = "";
        }
		return lastpassword;
	}
	public void setLastpassword(String lastpassword) {
		this.lastpassword = lastpassword;
	}
	public Timestamp getLastchgpwdtime() {
		return lastchgpwdtime;		
	}
	public void setLastchgpwdtime(Timestamp lastchgpwdtime) {
		this.lastchgpwdtime = lastchgpwdtime;
	}
	public int getErrorlogincount() {
		return errorlogincount;
	}
	public void setErrorlogincount(int errorlogincount) {
		this.errorlogincount = errorlogincount;
	}
//	public Collection<RoleModel> getRoles() {
//        if ( roles == null ) {
//            roles = new ArrayList<RoleModel>();
//        }
//		return roles;
//	}
//	public void setRoles(Collection<RoleModel> roles) {
//		this.roles = roles;
//	}

    public String getUnitname() {
        if ( unitname == null ) {
            unitname = "";
        }
        return unitname;
    }

    public void setUnitname(String unitname) {
        this.unitname = unitname;
    }

	public String getBase64() {
		return base64;
	}

	public void setBase64(String base64) {
		this.base64 = base64;
	}

	public String getLastloginip() {
		return lastloginip;
	}

	public void setLastloginip(String lastloginip) {
		this.lastloginip = lastloginip;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public boolean isChgPwdFlag() {
		return chgPwdFlag;
	}

	public void setChgPwdFlag(boolean chgPwdFlag) {
		this.chgPwdFlag = chgPwdFlag;
	}

	public Collection<RoleModel> getRoles() {
		return roles;
	}

	public void setRoles(Collection<RoleModel> roles) {
		this.roles = roles;
	}

	public UserStatusType getStatus() {
		return status;
	}

	public void setStatus(UserStatusType status) {
		this.status = status;
	}

}
