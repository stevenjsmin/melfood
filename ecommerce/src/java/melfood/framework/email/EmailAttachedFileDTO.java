/** 
 * 2015 EmailAttachedFileDTO.java
 * Created by Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.email;

import melfood.framework.common.dto.BaseDto;

/**
 * @author Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *
 */
public class EmailAttachedFileDTO extends BaseDto {
	private String fileId;
	private String savedFilename;
	private String originalFilename;
	private String fileType;
	private String fileSize;
	private String fileDescription;
	private String creator;
	private String createDatetime;

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getSavedFilename() {
		return savedFilename;
	}

	public void setSavedFilename(String savedFilename) {
		this.savedFilename = savedFilename;
	}

	public String getOriginalFilename() {
		return originalFilename;
	}

	public void setOriginalFilename(String originalFilename) {
		this.originalFilename = originalFilename;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileDescription() {
		return fileDescription;
	}

	public void setFileDescription(String fileDescription) {
		this.fileDescription = fileDescription;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreateDatetime() {
		return createDatetime;
	}

	public void setCreateDatetime(String createDatetime) {
		this.createDatetime = createDatetime;
	}

}
