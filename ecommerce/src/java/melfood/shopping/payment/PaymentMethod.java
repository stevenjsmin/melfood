/** 
 * 2016 PaymentMethod.java
 * Created by steven.min
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.shopping.payment;

import melfood.framework.common.dto.BaseDto;
import melfood.framework.user.User;

/**
 * 상품판매자가 제공할수 있는 결재수단을 표현하는 클래스이다.
 * 
 * @author steven.min
 *
 */
public class PaymentMethod extends BaseDto {

	private String userId;
	private int methodSeq;
	private String paymentMethod;
	private String bankName;
	private String bankBsb;
	private String bankAccountNo;
	private String bankAccountOwnerName;

	private String bankNameCodeName;
	private String paymentMethodCodeName;

	private User seller;

	public PaymentMethod() {
	}
	
	public PaymentMethod(String userId) {
		this.userId = userId;
	}

	public PaymentMethod(String userId, int methodSeq) {
		this.userId = userId;
		this.methodSeq = methodSeq;
	}

	public PaymentMethod(String userId, String methodSeq) {
		this.userId = userId;
		this.methodSeq = Integer.parseInt(methodSeq);
	}

	public PaymentMethod(String userId, int methodSeq, String paymentMethod) {
		this.userId = userId;
		this.methodSeq = methodSeq;
		this.paymentMethod = paymentMethod;
	}

	public PaymentMethod(String userId, String methodSeq, String paymentMethod) {
		this.userId = userId;
		this.methodSeq = Integer.parseInt(methodSeq);
		this.paymentMethod = paymentMethod;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getMethodSeq() {
		return methodSeq;
	}

	public void setMethodSeq(int methodSeq) {
		this.methodSeq = methodSeq;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankBsb() {
		return bankBsb;
	}

	public void setBankBsb(String bankBsb) {
		this.bankBsb = bankBsb;
	}

	public String getBankAccountNo() {
		return bankAccountNo;
	}

	public void setBankAccountNo(String bankAccountNo) {
		this.bankAccountNo = bankAccountNo;
	}

	public String getBankAccountOwnerName() {
		return bankAccountOwnerName;
	}

	public void setBankAccountOwnerName(String bankAccountOwnerName) {
		this.bankAccountOwnerName = bankAccountOwnerName;
	}

	public User getSeller() {
		return seller;
	}

	public void setSeller(User seller) {
		this.seller = seller;
	}

	public String getBankNameCodeName() {
		return bankNameCodeName;
	}

	public void setBankNameCodeName(String bankNameCodeName) {
		this.bankNameCodeName = bankNameCodeName;
	}

	public String getPaymentMethodCodeName() {
		return paymentMethodCodeName;
	}

	public void setPaymentMethodCodeName(String paymentMethodCodeName) {
		this.paymentMethodCodeName = paymentMethodCodeName;
	}

	@Override
	public String toString() {
		return "PaymentMethod [userId=" + userId + ", methodSeq=" + methodSeq + ", paymentMethod=" + paymentMethod + ", bankName=" + bankName + ", bankBsb=" + bankBsb + ", bankAccountNo=" + bankAccountNo + ", bankAccountOwnerName="
				+ bankAccountOwnerName + ", seller=" + seller + "]";
	}
}
