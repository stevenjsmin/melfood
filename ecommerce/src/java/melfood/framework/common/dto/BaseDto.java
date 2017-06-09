/** 
 * 2015 BaseDto.java
 * Created by Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.common.dto;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import melfood.framework.MelfoodConstants;

/**
 * @author Steven
 *
 */
public class BaseDto implements Serializable {

	private String actionMode;
	private String creator;
	private String useYn;
	private String useYnLabel;
	private String createDatetime;
	private String modifyDatetime;

	private String searchDateFrom;
	private String searchDateTo;
	
	private int pagenationPage = 0;
	private int pagenationPageSize = 0;

	private static final long serialVersionUID = 1L;

	public int getPagenationPage() {
		return pagenationPage;
	}

	public void setPagenationPage(int pagenationPage) {
		this.pagenationPage = pagenationPage;
	}

	public int getPagenationPageSize() {
		return (this.pagenationPageSize == 0) ? 10 : pagenationPageSize;
	}

	public String getCreateDatetime() {
		return createDatetime;
	}

	public void setCreateDatetime(String createDatetime) {
		this.createDatetime = createDatetime;
	}

	public String getModifyDatetime() {
		return modifyDatetime;
	}

	public void setModifyDatetime(String modifyDatetime) {
		this.modifyDatetime = modifyDatetime;
	}

	public void setPagenationPageSize(int pagenationPageSize) {
		this.pagenationPageSize = pagenationPageSize;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	public String getActionMode() {
		if (StringUtils.isBlank(actionMode)) {
			return MelfoodConstants.ACTION_MODE_MODIFY;
		} else {
			return actionMode;
		}
	}

	public void setActionMode(String actionMode) {
		this.actionMode = actionMode;
	}

	/**
	 * @return the useYnLabel
	 */
	public String getUseYnLabel() {
		return useYnLabel;
	}

	/**
	 * @param useYnLabel the useYnLabel to set
	 */
	public void setUseYnLabel(String useYnLabel) {
		this.useYnLabel = useYnLabel;
	}

	public String getSearchDateFrom() {
		return searchDateFrom;
	}

	public void setSearchDateFrom(String searchDateFrom) {
		this.searchDateFrom = searchDateFrom;
	}

	public String getSearchDateTo() {
		return searchDateTo;
	}

	public void setSearchDateTo(String searchDateTo) {
		this.searchDateTo = searchDateTo;
	}
	
	

}
