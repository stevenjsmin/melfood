/** 
 * 2016 CheckBeforeBuy.java
 * Created by steven.min
 *  
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.shopping.checkbeforebuy;

import melfood.framework.common.dto.BaseDto;

/**
 * DTO class for Notice
 *
 * @author steven.min
 *
 */
public class CheckBeforeBuy extends BaseDto {

	private int id;
	private String sellerId;
	private String subject;
	private String contents;
	private String isDefault;
	private String confirmStatus;
	
	private String sellerName;

	public CheckBeforeBuy() {
	}

	public CheckBeforeBuy(int id) {
		this.id = id;
	}

	public CheckBeforeBuy(String id) {
		this.id = Integer.parseInt(id);
	}

	public CheckBeforeBuy(int id, String sellerId) {
		this.id = id;
		this.sellerId = sellerId;
	}

	public CheckBeforeBuy(String id, String sellerId) {
		this.id = Integer.parseInt(id);
		this.sellerId = sellerId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}

	public String getConfirmStatus() {
		return confirmStatus;
	}

	public void setConfirmStatus(String confirmStatus) {
		this.confirmStatus = confirmStatus;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
	
	

}
