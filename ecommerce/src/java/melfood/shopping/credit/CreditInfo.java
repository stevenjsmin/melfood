/** 
 * 2016 CreditInfo.java
 * Created by steven.min
 *  
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.shopping.credit;

import melfood.framework.common.dto.BaseDto;

/**
 * TODO: Class description
 *
 * @author steven.min
 *
 */
public class CreditInfo extends BaseDto {
	private String userId;
	private int seq;
	private int creditValue;
	private String useYn;
	private String creditReason;
	private String modifyDatetime;
	private String createDatetime;
	private String creator;

	public CreditInfo() {
		this.creditValue = 0;
		this.useYn = "Y";
	}

	public CreditInfo(String userId) {
		this.userId = userId;
		this.creditValue = 0;
		this.useYn = "Y";
	}

	public CreditInfo(String userId, int seq) {
		this.userId = userId;
		this.seq = seq;
		this.creditValue = 0;
		this.useYn = "Y";
	}

	public CreditInfo(String userId, int seq, int creditValue) {
		this.userId = userId;
		this.seq = seq;
		this.creditValue = creditValue;
		this.useYn = "Y";
	}

	public CreditInfo(String userId, int seq, int creditValue, String useYn) {
		this.userId = userId;
		this.seq = seq;
		this.creditValue = creditValue;
		this.useYn = useYn;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public int getCreditValue() {
		return creditValue;
	}

	public void setCreditValue(int creditValue) {
		this.creditValue = creditValue;
	}

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	public String getModifyDatetime() {
		return modifyDatetime;
	}

	public void setModifyDatetime(String modifyDatetime) {
		this.modifyDatetime = modifyDatetime;
	}

	public String getCreateDatetime() {
		return createDatetime;
	}

	public void setCreateDatetime(String createDatetime) {
		this.createDatetime = createDatetime;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreditReason() {
		return creditReason;
	}

	public void setCreditReason(String creditReason) {
		this.creditReason = creditReason;
	}

}
