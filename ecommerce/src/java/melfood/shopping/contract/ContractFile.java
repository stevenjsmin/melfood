package melfood.shopping.contract;

import melfood.framework.common.dto.BaseDto;

public class ContractFile extends BaseDto {
	private String userId;
	private int contractSeq;
	private int fileId;
	private String fileName;
	private String savedFileName;
	private String createDatetime;

	public ContractFile() {
	}

	public ContractFile(String userId) {
		this.userId = userId;
	}

	public ContractFile(String userId, int contractSeq) {
		this.userId = userId;
		this.contractSeq = contractSeq;
	}

	public ContractFile(String userId, int contractSeq, int fileId) {
		this.userId = userId;
		this.contractSeq = contractSeq;
		this.fileId = fileId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getContractSeq() {
		return contractSeq;
	}

	public void setContractSeq(int contractSeq) {
		this.contractSeq = contractSeq;
	}

	/**
	 * @return the fileId
	 */
	public int getFileId() {
		return fileId;
	}

	/**
	 * @param fileId the fileId to set
	 */
	public void setFileId(int fileId) {
		this.fileId = fileId;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the createDatetime
	 */
	public String getCreateDatetime() {
		return createDatetime;
	}

	/**
	 * @param createDatetime the createDatetime to set
	 */
	public void setCreateDatetime(String createDatetime) {
		this.createDatetime = createDatetime;
	}

	/**
	 * @return the savedFileName
	 */
	public String getSavedFileName() {
		return savedFileName;
	}

	/**
	 * @param savedFileName the savedFileName to set
	 */
	public void setSavedFileName(String savedFileName) {
		this.savedFileName = savedFileName;
	}

}
