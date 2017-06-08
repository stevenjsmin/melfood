/** 
 * 2016 Product.java
 * Created by steven.min
 *  
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.shopping.product;

import java.math.BigDecimal;
import java.util.List;

import melfood.framework.common.dto.BaseDto;
import melfood.shopping.order.OrderProductOption;

/**
 * TODO: Class description
 *
 * @author steven.min
 *
 */
public class Product extends BaseDto {
	private int prodId;
	private int categoryId;
	private String categoryName;
	private String name;
	private String description;
	private String validateUntil;
	private String seller;
	private int parentProdId;
	private int inStockCnt;
	private BigDecimal unitPrice;
	private BigDecimal extraCharge;
	private String registerStatus;
	private String sellingCommission;
	private String sellingCommissionType;
	private Float sellingCommissionRate;
	private BigDecimal sellingCommissionFee;
	private String sellingStatus;
	private String tags;

	private ProductImage productImage;

	private String sellerName;
	private String registerStatusName;
	private String sellingStatusName;
	private String sellingCommissionTypeName;

	private List<ProductOption> productionOptions;
	private List<ProductImage> productionImages;

	private int orderItemId; // 이 값은 장바구니 단계에서 고유의 각 주문항목 ID 이다.
	private List<OrderProductOption> productOrderedOptions; // 이 객체는 장바구니 및 주문단계에서 사용될 용도이다.
	private int orderAmount; // 이 값은 장바구니 및 주문단계에서 사용될 용도이다.

	public Product() {
	}

	public Product(int prodId) {
		this.prodId = prodId;
	}

	public Product(String prodId) {
		this.prodId = Integer.parseInt(prodId);
	}

	public Product(int prodId, String name) {
		this.prodId = prodId;
		this.name = name;
	}

	public Product(String prodId, String name) {
		this.prodId = Integer.parseInt(prodId);
		this.name = name;
	}

	public Product(int prodId, String name, int categoryId) {
		this.prodId = prodId;
		this.name = name;
		this.categoryId = categoryId;
	}

	public Product(String prodId, String name, int categoryId) {
		this.prodId = Integer.parseInt(prodId);
		this.name = name;
		this.categoryId = categoryId;
	}

	public int getProdId() {
		return prodId;
	}

	public void setProdId(int prodId) {
		this.prodId = prodId;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getValidateUntil() {
		return validateUntil;
	}

	public void setValidateUntil(String validateUntil) {
		this.validateUntil = validateUntil;
	}

	public int getParentProdId() {
		return parentProdId;
	}

	public void setParentProdId(int parentProdId) {
		this.parentProdId = parentProdId;
	}

	public int getInStockCnt() {
		return inStockCnt;
	}

	public void setInStockCnt(int inStockCnt) {
		this.inStockCnt = inStockCnt;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getSellingCommissionType() {
		return sellingCommissionType;
	}

	public void setSellingCommissionType(String sellingCommissionType) {
		this.sellingCommissionType = sellingCommissionType;
	}

	public Float getSellingCommissionRate() {
		return sellingCommissionRate;
	}

	public void setSellingCommissionRate(Float sellingCommissionRate) {
		this.sellingCommissionRate = sellingCommissionRate;
	}

	public BigDecimal getSellingCommissionFee() {
		return sellingCommissionFee;
	}

	public void setSellingCommissionFee(BigDecimal sellingCommissionFee) {
		this.sellingCommissionFee = sellingCommissionFee;
	}

	public List<ProductOption> getProductionOptions() {
		return productionOptions;
	}

	public void setProductionOptions(List<ProductOption> productionOptions) {
		this.productionOptions = productionOptions;
	}

	public List<ProductImage> getProductionImages() {
		return productionImages;
	}

	public void setProductionImages(List<ProductImage> productionImages) {
		this.productionImages = productionImages;
	}

	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	public String getRegisterStatus() {
		return registerStatus;
	}

	public void setRegisterStatus(String registerStatus) {
		this.registerStatus = registerStatus;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public String getRegisterStatusName() {
		return registerStatusName;
	}

	public void setRegisterStatusName(String registerStatusName) {
		this.registerStatusName = registerStatusName;
	}

	public String getSellingCommissionTypeName() {
		return sellingCommissionTypeName;
	}

	public void setSellingCommissionTypeName(String sellingCommissionTypeName) {
		this.sellingCommissionTypeName = sellingCommissionTypeName;
	}

	public String getSellingCommission() {
		return sellingCommission;
	}

	public void setSellingCommission(String sellingCommission) {
		this.sellingCommission = sellingCommission;
	}

	public BigDecimal getExtraCharge() {
		return extraCharge;
	}

	public void setExtraCharge(BigDecimal extraCharge) {
		this.extraCharge = extraCharge;
	}

	public String getSellingStatus() {
		return sellingStatus;
	}

	public void setSellingStatus(String sellingStatus) {
		this.sellingStatus = sellingStatus;
	}

	public String getSellingStatusName() {
		return sellingStatusName;
	}

	public void setSellingStatusName(String sellingStatusName) {
		this.sellingStatusName = sellingStatusName;
	}

	public ProductImage getProductImage() {
		return productImage;
	}

	public void setProductImage(ProductImage productImage) {
		this.productImage = productImage;
	}

	public List<OrderProductOption> getProductOrderedOptions() {
		return productOrderedOptions;
	}

	public void setProductOrderedOptions(List<OrderProductOption> productOrderedOptions) {
		this.productOrderedOptions = productOrderedOptions;
	}

	public int getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(int orderAmount) {
		this.orderAmount = orderAmount;
	}

	public int getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(int orderItemId) {
		this.orderItemId = orderItemId;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

}
