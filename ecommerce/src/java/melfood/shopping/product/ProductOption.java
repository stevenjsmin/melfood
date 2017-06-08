/** 
 * 2016 ProductOption.java
 * Created by steven.min
 *  
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.shopping.product;

import java.util.List;

import melfood.framework.common.dto.BaseDto;

/**
 * TODO: Class description
 *
 * @author steven.min
 *
 */
public class ProductOption extends BaseDto {
	private int prodId;
	private int optionSeq;
	private String optionItem;
	private String isMandatory;
	

	private List<ProductOptionValue> optionValues;

	public ProductOption() {
	}

	public ProductOption(int prodId) {
		this.prodId = prodId;
	}

	public ProductOption(String prodId) {
		this.prodId = Integer.parseInt(prodId);
	}

	public ProductOption(int prodId, int optionSeq) {
		this.prodId = prodId;
		this.optionSeq = optionSeq;
	}

	public ProductOption(String prodId, int optionSeq) {
		this.prodId = Integer.parseInt(prodId);
		this.optionSeq = optionSeq;
	}

	public ProductOption(int prodId, int optionSeq, String optionItem) {
		this.prodId = prodId;
		this.optionSeq = optionSeq;
		this.optionItem = optionItem;
	}

	public ProductOption(String prodId, String optionSeq, String optionName) {
		this.prodId = Integer.parseInt(prodId);
		this.optionSeq = Integer.parseInt(optionSeq);
		this.optionItem = optionItem;
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
	 * @return the optionSeq
	 */
	public int getOptionSeq() {
		return optionSeq;
	}

	/**
	 * @param optionSeq the optionSeq to set
	 */
	public void setOptionSeq(int optionSeq) {
		this.optionSeq = optionSeq;
	}

	/**
	 * @return the optionValues
	 */
	public List<ProductOptionValue> getOptionValues() {
		return optionValues;
	}

	/**
	 * @param optionValues the optionValues to set
	 */
	public void setOptionValues(List<ProductOptionValue> optionValues) {
		this.optionValues = optionValues;
	}

	/**
	 * @return the optionItem
	 */
	public String getOptionItem() {
		return optionItem;
	}

	/**
	 * @param optionItem the optionItem to set
	 */
	public void setOptionItem(String optionItem) {
		this.optionItem = optionItem;
	}

	public String getIsMandatory() {
		return isMandatory;
	}

	public void setIsMandatory(String isMandatory) {
		this.isMandatory = isMandatory;
	}
	
	

}
