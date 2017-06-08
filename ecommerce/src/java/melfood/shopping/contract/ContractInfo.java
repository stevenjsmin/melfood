/** 
 * 2016 ContractInfo.java
 * Created by steven.min
 *  
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.shopping.contract;

import java.util.List;

import melfood.framework.common.dto.BaseDto;

/**
 * TODO: Class description
 *
 * @author steven.min
 *
 */
public class ContractInfo extends BaseDto {
	private String userId;
	private int contractSeq;
	private String contractStatus;
	private String contractStatusLabel;
	private String contractStartDate;
	private String contractEndDate;
	private String contractDescription;
	private String attachementFile;
	private String createDatetime;
	private String modifyDatetime;
	private String creator;

	private String userName;
	private String applyStatus;
	private List<ContractFile> contractFiles;

	public ContractInfo() {
	}

	public ContractInfo(String userId) {
		this.userId = userId;
	}

	public ContractInfo(String userId, int contractSeq) {
		this.userId = userId;
		this.contractSeq = contractSeq;
	}

	public ContractInfo(String userId, String contractSeq) {
		this.userId = userId;
		this.contractSeq = Integer.parseInt(contractSeq);
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the contractSeq
	 */
	public int getContractSeq() {
		return contractSeq;
	}

	/**
	 * @param contractSeq the contractSeq to set
	 */
	public void setContractSeq(int contractSeq) {
		this.contractSeq = contractSeq;
	}

	/**
	 * @return the contractStartDate
	 */
	public String getContractStartDate() {
		return contractStartDate;
	}

	/**
	 * @param contractStartDate the contractStartDate to set
	 */
	public void setContractStartDate(String contractStartDate) {
		this.contractStartDate = contractStartDate;
	}

	/**
	 * @return the contractEndDate
	 */
	public String getContractEndDate() {
		return contractEndDate;
	}

	/**
	 * @param contractEndDate the contractEndDate to set
	 */
	public void setContractEndDate(String contractEndDate) {
		this.contractEndDate = contractEndDate;
	}

	/**
	 * @return the contractDescription
	 */
	public String getContractDescription() {
		return contractDescription;
	}

	/**
	 * @param contractDescription the contractDescription to set
	 */
	public void setContractDescription(String contractDescription) {
		this.contractDescription = contractDescription;
	}

	/**
	 * @return the attachementFile
	 */
	public String getAttachementFile() {
		return attachementFile;
	}

	/**
	 * @param attachementFile the attachementFile to set
	 */
	public void setAttachementFile(String attachementFile) {
		this.attachementFile = attachementFile;
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
	 * @return the modifyDatetime
	 */
	public String getModifyDatetime() {
		return modifyDatetime;
	}

	/**
	 * @param modifyDatetime the modifyDatetime to set
	 */
	public void setModifyDatetime(String modifyDatetime) {
		this.modifyDatetime = modifyDatetime;
	}

	/**
	 * @return the creator
	 */
	public String getCreator() {
		return creator;
	}

	/**
	 * @param creator the creator to set
	 */
	public void setCreator(String creator) {
		this.creator = creator;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the contractStatus
	 */
	public String getContractStatus() {
		return contractStatus;
	}

	/**
	 * @param contractStatus the contractStatus to set
	 */
	public void setContractStatus(String contractStatus) {
		this.contractStatus = contractStatus;
	}

	/**
	 * @return the applyStatus
	 */
	public String getApplyStatus() {
		return applyStatus;
	}

	/**
	 * @param applyStatus the applyStatus to set
	 */
	public void setApplyStatus(String applyStatus) {
		this.applyStatus = applyStatus;
	}

	/**
	 * @return the contractFiles
	 */
	public List<ContractFile> getContractFiles() {
		return contractFiles;
	}

	/**
	 * @param contractFiles the contractFiles to set
	 */
	public void setContractFiles(List<ContractFile> contractFiles) {
		this.contractFiles = contractFiles;
	}

	/**
	 * @return the contractStatusLabel
	 */
	public String getContractStatusLabel() {
		return contractStatusLabel;
	}

	/**
	 * @param contractStatusLabel the contractStatusLabel to set
	 */
	public void setContractStatusLabel(String contractStatusLabel) {
		this.contractStatusLabel = contractStatusLabel;
	}

}
