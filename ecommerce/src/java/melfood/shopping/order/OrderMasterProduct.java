package melfood.shopping.order;

import melfood.framework.common.dto.BaseDto;

import java.util.List;

/**
 * Created by Steven on 4/7/17.
 */
public class OrderMasterProduct extends BaseDto {

    private int orderMasterProductId;
    private int orderMasterId; // order_master 테이블의 참조
    private int productId;
    private String productName;
    private String productDescription;
    private String productCategory;
    private String hasProductOption;
    private Float amountProductOption;
    private String sellerId;
    private String sellerName;
    private String sellerMobile;
    private String sellerPhone;
    private String sellerEmail;
    private String sellerAddressStreet;
    private String sellerAddressSuburb;
    private String sellerAddressPostcode;
    private String sellerAddressState;
    private Float unitPrice;
    private Float comissionFee;
    private Integer numberOfOrder;

    private List<OrderMasterProductOption> orderMasterProductOptionList;

    public OrderMasterProduct() {
    }

    public OrderMasterProduct(int orderMasterProductId) {
        this.orderMasterProductId = orderMasterProductId;
    }

    public OrderMasterProduct(String orderMasterProductId) {
        this.orderMasterProductId = Integer.parseInt(orderMasterProductId);
    }

    public OrderMasterProduct(int orderMasterProductId, int orderMasterId) {
        this.orderMasterProductId = orderMasterProductId;
        this.orderMasterId = orderMasterId;
    }

    public OrderMasterProduct(String orderMasterProductId, String orderMasterId) {
        this.orderMasterProductId = Integer.parseInt(orderMasterProductId);
        this.orderMasterId = Integer.parseInt(orderMasterId);
    }

    public int getOrderMasterProductId() {
        return orderMasterProductId;
    }

    public void setOrderMasterProductId(int orderMasterProductId) {
        this.orderMasterProductId = orderMasterProductId;
    }

    public int getOrderMasterId() {
        return orderMasterId;
    }

    public void setOrderMasterId(int orderMasterId) {
        this.orderMasterId = orderMasterId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getHasProductOption() {
        return hasProductOption;
    }

    public void setHasProductOption(String hasProductOption) {
        this.hasProductOption = hasProductOption;
    }

    public Float getAmountProductOption() {
        return amountProductOption;
    }

    public void setAmountProductOption(Float amountProductOption) {
        this.amountProductOption = amountProductOption;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getSellerMobile() {
        return sellerMobile;
    }

    public void setSellerMobile(String sellerMobile) {
        this.sellerMobile = sellerMobile;
    }

    public String getSellerPhone() {
        return sellerPhone;
    }

    public void setSellerPhone(String sellerPhone) {
        this.sellerPhone = sellerPhone;
    }

    public String getSellerEmail() {
        return sellerEmail;
    }

    public void setSellerEmail(String sellerEmail) {
        this.sellerEmail = sellerEmail;
    }

    public String getSellerAddressStreet() {
        return sellerAddressStreet;
    }

    public void setSellerAddressStreet(String sellerAddressStreet) {
        this.sellerAddressStreet = sellerAddressStreet;
    }

    public String getSellerAddressSuburb() {
        return sellerAddressSuburb;
    }

    public void setSellerAddressSuburb(String sellerAddressSuburb) {
        this.sellerAddressSuburb = sellerAddressSuburb;
    }

    public String getSellerAddressPostcode() {
        return sellerAddressPostcode;
    }

    public void setSellerAddressPostcode(String sellerAddressPostcode) {
        this.sellerAddressPostcode = sellerAddressPostcode;
    }

    public String getSellerAddressState() {
        return sellerAddressState;
    }

    public void setSellerAddressState(String sellerAddressState) {
        this.sellerAddressState = sellerAddressState;
    }

    public Float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Float getComissionFee() {
        return comissionFee;
    }

    public void setComissionFee(Float comissionFee) {
        this.comissionFee = comissionFee;
    }

    public List<OrderMasterProductOption> getOrderMasterProductOptionList() {
        return orderMasterProductOptionList;
    }

    public void setOrderMasterProductOptionList(List<OrderMasterProductOption> orderMasterProductOptionList) {
        this.orderMasterProductOptionList = orderMasterProductOptionList;
    }

    public Integer getNumberOfOrder() {
        return numberOfOrder;
    }

    public void setNumberOfOrder(Integer numberOfOrder) {
        this.numberOfOrder = numberOfOrder;
    }
}


