/** 
 * 2015 ApplicationInfo.java
 * Created by Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.system;

import melfood.framework.common.dto.BaseDto;

/**
 * @author Steven
 *
 */
public class ApplicationInfo extends BaseDto {
	private String sysId;
	private String sysName;
	private String sysDescript;
	private String copyright;
	private String adminUserId;
	private String fileId;
	private String acessChkExceptUrl;
	private String dataUploadRootpath;

	public String getSysId() {
		return sysId;
	}

	public void setSysId(String sysId) {
		this.sysId = sysId;
	}

	public String getSysName() {
		return sysName;
	}

	public void setSysName(String sysName) {
		this.sysName = sysName;
	}

	public String getSysDescript() {
		return sysDescript;
	}

	public void setSysDescript(String sysDescript) {
		this.sysDescript = sysDescript;
	}

	public String getCopyright() {
		return copyright;
	}

	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}

	public String getAdminUserId() {
		return adminUserId;
	}

	public void setAdminUserId(String adminUserId) {
		this.adminUserId = adminUserId;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getAcessChkExceptUrl() {
		return acessChkExceptUrl;
	}

	public void setAcessChkExceptUrl(String acessChkExceptUrl) {
		this.acessChkExceptUrl = acessChkExceptUrl;
	}

	public String getDataUploadRootpath() {
		return dataUploadRootpath;
	}

	public void setDataUploadRootpath(String dataUploadRootpath) {
		this.dataUploadRootpath = dataUploadRootpath;
	}

}
