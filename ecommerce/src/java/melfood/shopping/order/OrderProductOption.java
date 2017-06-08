/** 
 * 2016 OrderProductOption.java
 * Created by steven.min
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.shopping.order;

import melfood.framework.common.dto.BaseDto;

/**
 * 각 장바구니나 주문의 상품은 각 옵션을 가질수있는데, 옵션은 상품마다 하나이상 가질수 있다.<br>
 * cartSeqId가 프라이머리키가 되면서, 이 키는 장바구니와 Order테입블에서 이용된다.<br>
 * 각 상품별 하나이상의 옵션을 위하여 seq가 이용된다.
 * 
 * @author steven.min
 *
 */
public class OrderProductOption extends BaseDto {
	private int orderItemId;
	private int seq;
	private String cartOrOrder;
	private int prodId;
	private String optionName;
	private String optionValue;
	private String customerId;
	private String sellerId;
	private String addtionalPrice;

	public OrderProductOption() {
	}

	public OrderProductOption(int orderItemId) {
		this.orderItemId = orderItemId;
	}

	public OrderProductOption(int orderItemId, int seq) {
		this.orderItemId = orderItemId;
		this.seq = seq;
	}
	
	public OrderProductOption(int orderItemId, String optionName, String optionValue) {
		this.orderItemId = orderItemId;
		this.optionName = optionName;
		this.optionValue = optionValue;
	}

	public int getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(int orderItemId) {
		this.orderItemId = orderItemId;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getCartOrOrder() {
		return cartOrOrder;
	}

	public void setCartOrOrder(String cartOrOrder) {
		this.cartOrOrder = cartOrOrder;
	}

	public int getProdId() {
		return prodId;
	}

	public void setProdId(int prodId) {
		this.prodId = prodId;
	}

	public String getOptionName() {
		return optionName;
	}

	public void setOptionName(String optionName) {
		this.optionName = optionName;
	}

	public String getOptionValue() {
		return optionValue;
	}

	public void setOptionValue(String optionValue) {
		this.optionValue = optionValue;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public String getAddtionalPrice() {
		return addtionalPrice;
	}

	public void setAddtionalPrice(String addtionalPrice) {
		this.addtionalPrice = addtionalPrice;
	}

}
