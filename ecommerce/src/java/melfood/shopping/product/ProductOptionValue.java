/** 
 * 2016 ProductOptionValue.java
 * Created by steven.min
 *  
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.shopping.product;

import java.math.BigDecimal;

import melfood.framework.common.dto.BaseDto;

/**
 * TODO: Class description
 *
 * @author steven.min
 *
 */
public class ProductOptionValue extends BaseDto {
	private int prodId;
	private int optionSeq;
	private int valueSeq;
	private String valueLabel;
	private String useYn;
	private int inStockCnt;
	private BigDecimal unitPrice;
	private BigDecimal extraCharge;

	private String isMandatory;
	private String optionItem;

	public ProductOptionValue() {
	}

	public ProductOptionValue(int prodId) {
		this.prodId = prodId;
	}

	public ProductOptionValue(String prodId) {
		this.prodId = Integer.parseInt(prodId);
	}

	public ProductOptionValue(int prodId, int optionSeq) {
		this.prodId = prodId;
		this.optionSeq = optionSeq;
	}

	public ProductOptionValue(String prodId, int optionSeq) {
		this.prodId = Integer.parseInt(prodId);
		this.optionSeq = optionSeq;
	}

	public ProductOptionValue(String prodId, String optionSeq) {
		this.prodId = Integer.parseInt(prodId);
		this.optionSeq = Integer.parseInt(optionSeq);
	}

	public ProductOptionValue(int prodId, int optionSeq, int valueSeq) {
		this.prodId = prodId;
		this.optionSeq = optionSeq;
		this.valueSeq = valueSeq;
	}

	public ProductOptionValue(int prodId, int optionSeq, String valueSeq) {
		this.prodId = prodId;
		this.optionSeq = optionSeq;
		this.valueSeq = Integer.parseInt(valueSeq);
	}

	public ProductOptionValue(String prodId, String optionSeq, String valueSeq) {
		this.prodId = Integer.parseInt(prodId);
		this.optionSeq = Integer.parseInt(optionSeq);
		this.valueSeq = Integer.parseInt(valueSeq);
	}

	public ProductOptionValue(int prodId, int optionSeq, int valueSeq, int inStockCnt) {
		this.prodId = prodId;
		this.optionSeq = optionSeq;
		this.valueSeq = valueSeq;
		this.inStockCnt = inStockCnt;
	}

	public ProductOptionValue(int prodId, int optionSeq, String valueSeq, int inStockCnt) {
		this.prodId = prodId;
		this.optionSeq = optionSeq;
		this.valueSeq = Integer.parseInt(valueSeq);
		this.inStockCnt = inStockCnt;
	}

	public ProductOptionValue(String prodId, String optionSeq, String valueSeq, int inStockCnt) {
		this.prodId = Integer.parseInt(prodId);
		this.optionSeq = Integer.parseInt(optionSeq);
		this.valueSeq = Integer.parseInt(valueSeq);
		this.inStockCnt = inStockCnt;
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
	 * @return the unitPrice
	 */
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	/**
	 * @param unitPrice the unitPrice to set
	 */
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	/**
	 * @return the inStockCnt
	 */
	public int getInStockCnt() {
		return inStockCnt;
	}

	/**
	 * @param inStockCnt the inStockCnt to set
	 */
	public void setInStockCnt(int inStockCnt) {
		this.inStockCnt = inStockCnt;
	}

	/**
	 * @return the useYn
	 */
	public String getUseYn() {
		return useYn;
	}

	/**
	 * @param useYn the useYn to set
	 */
	public void setUseYn(String useYn) {
		this.useYn = useYn;
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

	/**
	 * @return the extraCharge
	 */
	public BigDecimal getExtraCharge() {
		return extraCharge;
	}

	/**
	 * @param extraCharge the extraCharge to set
	 */
	public void setExtraCharge(BigDecimal extraCharge) {
		this.extraCharge = extraCharge;
	}

	public int getValueSeq() {
		return valueSeq;
	}

	public void setValueSeq(int valueSeq) {
		this.valueSeq = valueSeq;
	}

	public String getValueLabel() {
		return valueLabel;
	}

	public void setValueLabel(String valueLabel) {
		this.valueLabel = valueLabel;
	}

	public String getIsMandatory() {
		return isMandatory;
	}

	public void setIsMandatory(String isMandatory) {
		this.isMandatory = isMandatory;
	}

}
