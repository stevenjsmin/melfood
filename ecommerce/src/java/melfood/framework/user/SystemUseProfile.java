/** 
 * 2015 SystemUseProfile.java
 * Created by Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */
package melfood.framework.user;

import melfood.framework.common.dto.BaseDto;

/**
 * Persistence model for User
 * 
 * @author Steven J.S Min
 * 
 */
public class SystemUseProfile extends BaseDto {

	private String seq;
	private String userId;
	private String loginIPAddress;
	private String loginDatetime;
	private String logoutDatetime;
	private String minutesOfUse;
	private String createDt;

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLoginDatetime() {
		return loginDatetime;
	}

	public void setLoginDatetime(String loginDatetime) {
		this.loginDatetime = loginDatetime;
	}

	public String getLogoutDatetime() {
		return logoutDatetime;
	}

	public void setLogoutDatetime(String logoutDatetime) {
		this.logoutDatetime = logoutDatetime;
	}

	public String getMinutesOfUse() {
		return minutesOfUse;
	}

	public void setMinutesOfUse(String minutesOfUse) {
		this.minutesOfUse = minutesOfUse;
	}

	public String getCreateDt() {
		return createDt;
	}

	public void setCreateDt(String createDt) {
		this.createDt = createDt;
	}

	public String getLoginIPAddress() {
		return loginIPAddress;
	}

	public void setLoginIPAddress(String loginIPAddress) {
		this.loginIPAddress = loginIPAddress;
	}

}
