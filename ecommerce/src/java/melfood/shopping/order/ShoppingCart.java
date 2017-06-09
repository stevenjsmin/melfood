
/** 
 * 2016 ShoppingCart.java
 * Created by steven.min
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.shopping.order;

import java.util.List;

import melfood.framework.common.dto.BaseDto;
import melfood.framework.user.User;
import melfood.shopping.product.Product;

public class ShoppingCart extends BaseDto {

	private int id;
	private String customerId;
	private String sellerId;
	private int prodId;
	private int amount;

	private User seller;
	private User customer;

	// 장바구니에 담긴 상품 상세정보 목록이다.
	private List<Product> products;
	private float totalPrice;
	private int cartNumber;

	public ShoppingCart() {
	}

	public ShoppingCart(String id) {
		this.id = Integer.parseInt(id);
	}

	public ShoppingCart(int id) {
		this.id = id;
	}

	public ShoppingCart(String customerId, String sellerId) {
		this.customerId = customerId;
		this.sellerId = sellerId;
	}

	public ShoppingCart(int id, String customerId) {
		this.id = id;
		this.customerId = customerId;
	}

	public ShoppingCart(int id, String customerId, String sellerId) {
		this.id = id;
		this.customerId = customerId;
		this.sellerId = sellerId;
	}

	public ShoppingCart(int id, String customerId, String sellerId, int prodId, int amount) {
		this.id = id;
		this.customerId = customerId;
		this.sellerId = sellerId;
		this.prodId = prodId;
		this.amount = amount;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public int getProdId() {
		return prodId;
	}

	public void setProdId(int prodId) {
		this.prodId = prodId;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public User getSeller() {
		return seller;
	}

	public void setSeller(User seller) {
		this.seller = seller;
	}

	public User getCustomer() {
		return customer;
	}

	public void setCustomer(User customer) {
		this.customer = customer;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}

	public int getCartNumber() {
		return cartNumber;
	}

	public void setCartNumber(int cartNumber) {
		this.cartNumber = cartNumber;
	}

}
