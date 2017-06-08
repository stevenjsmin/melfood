/** 
 * 2016 AttachmentFile.java
 * Created by steven.min
 *  
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.attachement;

import melfood.framework.common.dto.BaseDto;

/**
 * TODO: Class description
 *
 * @author steven.min
 *
 */
public class AttachmentFile extends BaseDto {
	private int fileId;
	private String fileName;
	private String fileType;
	private int fileSize;
	private String fileDescription;
	private String subDirectory;
	private String savedFileName;

	private String createDatetimeFrom;
	private String createDatetimeTo;

	public AttachmentFile() {
	}

	public AttachmentFile(int fileId) {
		this.fileId = fileId;
	}

	public AttachmentFile(String fileId) {
		this.fileId = Integer.parseInt(fileId);
	}

	public AttachmentFile(int fileId, String fileName) {
		this.fileId = fileId;
		this.fileName = fileName;
		this.savedFileName = fileName;
	}

	public AttachmentFile(String fileId, String fileName) {
		this.fileId = Integer.parseInt(fileId);
		this.fileName = fileName;
		this.savedFileName = fileName;
	}

	public AttachmentFile(int fileId, String fileName, String savedFileName) {
		this.fileId = fileId;
		this.fileName = fileName;
		this.savedFileName = savedFileName;
	}

	public int getFileId() {
		return fileId;
	}

	public void setFileId(int fileId) {
		this.fileId = fileId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public int getFileSize() {
		return fileSize;
	}

	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileDescription() {
		return fileDescription;
	}

	public void setFileDescription(String fileDescription) {
		this.fileDescription = fileDescription;
	}

	public String getSubDirectory() {
		return subDirectory;
	}

	public void setSubDirectory(String subDirectory) {
		this.subDirectory = subDirectory;
	}

	public String getSavedFileName() {
		return savedFileName;
	}

	public void setSavedFileName(String savedFileName) {
		this.savedFileName = savedFileName;
	}

	public String getCreateDatetimeFrom() {
		return createDatetimeFrom;
	}

	public void setCreateDatetimeFrom(String createDatetimeFrom) {
		this.createDatetimeFrom = createDatetimeFrom;
	}

	public String getCreateDatetimeTo() {
		return createDatetimeTo;
	}

	public void setCreateDatetimeTo(String createDatetimeTo) {
		this.createDatetimeTo = createDatetimeTo;
	}

}
