/** 
 * 2015 ServiceDto.java
 * Created by Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.service;

import melfood.framework.common.dto.BaseDto;

/**
 * This class represent service
 * 
 * @author Steven Min
 *
 */
public class ServiceDto extends BaseDto {
	private String svcId;
	private String svcName;
	private String svcDesc;
	private String svcUrl;
	private String useYn;
	private String createDt;
	private String creator;
	private String authRequired;
	private String svcPrefix;
	private String isDummy;
	private String isMenu;
	private String upperSvc;

	public String getSvcId() {
		return svcId;
	}

	public void setSvcId(String svcId) {
		this.svcId = svcId;
	}

	public String getSvcName() {
		return svcName;
	}

	public void setSvcName(String svcName) {
		this.svcName = svcName;
	}

	public String getSvcDesc() {
		return svcDesc;
	}

	public void setSvcDesc(String svcDesc) {
		this.svcDesc = svcDesc;
	}

	public String getSvcUrl() {
		return svcUrl;
	}

	public void setSvcUrl(String svcUrl) {
		this.svcUrl = svcUrl;
	}

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	public String getCreateDt() {
		return createDt;
	}

	public void setCreateDt(String createDt) {
		this.createDt = createDt;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getAuthRequired() {
		return authRequired;
	}

	public void setAuthRequired(String authRequired) {
		this.authRequired = authRequired;
	}

	public String getSvcPrefix() {
		return svcPrefix;
	}

	public void setSvcPrefix(String svcPrefix) {
		this.svcPrefix = svcPrefix;
	}

	public String getIsDummy() {
		return isDummy;
	}

	public void setIsDummy(String isDummy) {
		this.isDummy = isDummy;
	}

	public String getIsMenu() {
		return isMenu;
	}

	public void setIsMenu(String isMenu) {
		this.isMenu = isMenu;
	}

	public String getUpperSvc() {
		return upperSvc;
	}

	public void setUpperSvc(String upperSvc) {
		this.upperSvc = upperSvc;
	}

}
