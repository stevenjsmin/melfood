/** 
 * 2016 DeliveryMethod.java
 * Created by steven.min
 *  
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.shopping.product;

import melfood.framework.common.dto.BaseDto;

/**
 * TODO: Class description
 *
 * @author steven.min
 *
 */
public class DeliveryMethod extends BaseDto {
	private int prodId;
	private String methodId;
	private String methodName;
	private String description;

	private String isSelectedMethod = "N";

	public DeliveryMethod() {
	}

	public DeliveryMethod(String methodId) {
		this.methodId = methodId;
	}

	public DeliveryMethod(int prodId, String methodId) {
		this.prodId = prodId;
		this.methodId = methodId;
	}
	
	public DeliveryMethod(String prodId, String methodId) {
		this.prodId = Integer.parseInt(prodId);
		this.methodId = methodId;
	}

	public DeliveryMethod(int prodId, String methodId, String methodName) {
		this.prodId = prodId;
		this.methodId = methodId;
		this.methodName = methodName;
	}

	/**
	 * @return the prodId
	 */
	public int getProdId() {
		return prodId;
	}

	/**
	 * @param prodId the prodId to set
	 */
	public void setProdId(int prodId) {
		this.prodId = prodId;
	}

	/**
	 * @return the methodId
	 */
	public String getMethodId() {
		return methodId;
	}

	/**
	 * @param methodId the methodId to set
	 */
	public void setMethodId(String methodId) {
		this.methodId = methodId;
	}

	/**
	 * @return the methodName
	 */
	public String getMethodName() {
		return methodName;
	}

	/**
	 * @param methodName the methodName to set
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	public String getIsSelectedMethod() {
		return isSelectedMethod;
	}

	public void setIsSelectedMethod(String isSelectedMethod) {
		this.isSelectedMethod = isSelectedMethod;
	}

}
