package melfood.shopping.grouppurchase.dto;

import melfood.framework.common.dto.BaseDto;
import melfood.shopping.product.ProductImage;

import java.util.List;

/**
 * Created by Steven on 13/6/17.
 */
public class GroupPurchase extends BaseDto {

    private int groupPurchaseId;
    private String groupPurchaseTitle;
    private String groupPurchaseSubtitle;
    private String purchaseOrganizer;
    private String purchaseOrganizerName;
    private String orderStartDt;
    private String orderEndDt;
    private String stopSelling;
    private String stopSellingReason;
    private String marketAddressStreet;
    private String marketAddressSuburb;
    private String marketAddressState;
    private String marketAddressPostcode;
    private String marketAddressComment;
    private String marketOpenStartDt;
    private String marketOpenEndDt;
    private String minimumPurchaseAmount;
    private String discountMethod;
    private Float discountFixedAmount;
    private Float discountRateValue;
    private String deliverable;
    private Float deliveryFeePerKm;
    private String groupPurchaseNotice;
    private String mallMasterChecked;

    private String marketOpenStartDate;
    private String marketOpenStartTime;
    private String marketOpenEndDate;
    private String marketOpenEndTime;

    private List<GroupPurchaseProduct> purchaseProducts;
    private List<ProductImage> groupPurchaseImages;

    public GroupPurchase() {
    }

    public GroupPurchase(int groupPurchaseId) {
        this.groupPurchaseId = groupPurchaseId;
    }

    public GroupPurchase(String groupPurchaseId) {
        this.groupPurchaseId = Integer.parseInt(groupPurchaseId);
    }

    public int getGroupPurchaseId() {
        return groupPurchaseId;
    }

    public void setGroupPurchaseId(int groupPurchaseId) {
        this.groupPurchaseId = groupPurchaseId;
    }

    public String getGroupPurchaseTitle() {
        return groupPurchaseTitle;
    }

    public void setGroupPurchaseTitle(String groupPurchaseTitle) {
        this.groupPurchaseTitle = groupPurchaseTitle;
    }

    public String getGroupPurchaseSubtitle() {
        return groupPurchaseSubtitle;
    }

    public void setGroupPurchaseSubtitle(String groupPurchaseSubtitle) {
        this.groupPurchaseSubtitle = groupPurchaseSubtitle;
    }

    public String getPurchaseOrganizer() {
        return purchaseOrganizer;
    }

    public void setPurchaseOrganizer(String purchaseOrganizer) {
        this.purchaseOrganizer = purchaseOrganizer;
    }

    public String getOrderStartDt() {
        return orderStartDt;
    }

    public void setOrderStartDt(String orderStartDt) {
        this.orderStartDt = orderStartDt;
    }

    public String getOrderEndDt() {
        return orderEndDt;
    }

    public void setOrderEndDt(String orderEndDt) {
        this.orderEndDt = orderEndDt;
    }

    public String getStopSelling() {
        return stopSelling;
    }

    public void setStopSelling(String stopSelling) {
        this.stopSelling = stopSelling;
    }

    public String getStopSellingReason() {
        return stopSellingReason;
    }

    public void setStopSellingReason(String stopSellingReason) {
        this.stopSellingReason = stopSellingReason;
    }

    public String getMarketAddressStreet() {
        return marketAddressStreet;
    }

    public void setMarketAddressStreet(String marketAddressStreet) {
        this.marketAddressStreet = marketAddressStreet;
    }

    public String getMarketAddressSuburb() {
        return marketAddressSuburb;
    }

    public void setMarketAddressSuburb(String marketAddressSuburb) {
        this.marketAddressSuburb = marketAddressSuburb;
    }

    public String getMarketAddressState() {
        return marketAddressState;
    }

    public void setMarketAddressState(String marketAddressState) {
        this.marketAddressState = marketAddressState;
    }

    public String getMarketAddressPostcode() {
        return marketAddressPostcode;
    }

    public void setMarketAddressPostcode(String marketAddressPostcode) {
        this.marketAddressPostcode = marketAddressPostcode;
    }

    public String getMarketAddressComment() {
        return marketAddressComment;
    }

    public void setMarketAddressComment(String marketAddressComment) {
        this.marketAddressComment = marketAddressComment;
    }

    public String getMinimumPurchaseAmount() {
        return minimumPurchaseAmount;
    }

    public void setMinimumPurchaseAmount(String minimumPurchaseAmount) {
        this.minimumPurchaseAmount = minimumPurchaseAmount;
    }

    public String getDiscountMethod() {
        return discountMethod;
    }

    public void setDiscountMethod(String discountMethod) {
        this.discountMethod = discountMethod;
    }

    public Float getDiscountFixedAmount() {
        return discountFixedAmount;
    }

    public void setDiscountFixedAmount(Float discountFixedAmount) {
        this.discountFixedAmount = discountFixedAmount;
    }

    public Float getDiscountRateValue() {
        return discountRateValue;
    }

    public void setDiscountRateValue(Float discountRateValue) {
        this.discountRateValue = discountRateValue;
    }

    public String getGroupPurchaseNotice() {
        return groupPurchaseNotice;
    }

    public void setGroupPurchaseNotice(String groupPurchaseNotice) {
        this.groupPurchaseNotice = groupPurchaseNotice;
    }

    public String getMallMasterChecked() {
        return mallMasterChecked;
    }

    public void setMallMasterChecked(String mallMasterChecked) {
        this.mallMasterChecked = mallMasterChecked;
    }

    public List<GroupPurchaseProduct> getPurchaseProducts() {
        return purchaseProducts;
    }

    public void setPurchaseProducts(List<GroupPurchaseProduct> purchaseProducts) {
        this.purchaseProducts = purchaseProducts;
    }

    public String getPurchaseOrganizerName() {
        return purchaseOrganizerName;
    }

    public void setPurchaseOrganizerName(String purchaseOrganizerName) {
        this.purchaseOrganizerName = purchaseOrganizerName;
    }

    public String getMarketOpenStartDt() {
        return marketOpenStartDt;
    }

    public void setMarketOpenStartDt(String marketOpenStartDt) {
        this.marketOpenStartDt = marketOpenStartDt;
    }

    public String getMarketOpenEndDt() {
        return marketOpenEndDt;
    }

    public void setMarketOpenEndDt(String marketOpenEndDt) {
        this.marketOpenEndDt = marketOpenEndDt;
    }

    public List<ProductImage> getGroupPurchaseImages() {
        return groupPurchaseImages;
    }

    public void setGroupPurchaseImages(List<ProductImage> groupPurchaseImages) {
        this.groupPurchaseImages = groupPurchaseImages;
    }

    public String getDeliverable() {
        return deliverable;
    }

    public void setDeliverable(String deliverable) {
        this.deliverable = deliverable;
    }

    public Float getDeliveryFeePerKm() {
        return deliveryFeePerKm;
    }

    public void setDeliveryFeePerKm(Float deliveryFeePerKm) {
        this.deliveryFeePerKm = deliveryFeePerKm;
    }

    public String getMarketOpenStartDate() {
        return marketOpenStartDate;
    }

    public void setMarketOpenStartDate(String marketOpenStartDate) {
        this.marketOpenStartDate = marketOpenStartDate;
    }

    public String getMarketOpenStartTime() {
        return marketOpenStartTime;
    }

    public void setMarketOpenStartTime(String marketOpenStartTime) {
        this.marketOpenStartTime = marketOpenStartTime;
    }

    public String getMarketOpenEndDate() {
        return marketOpenEndDate;
    }

    public void setMarketOpenEndDate(String marketOpenEndDate) {
        this.marketOpenEndDate = marketOpenEndDate;
    }

    public String getMarketOpenEndTime() {
        return marketOpenEndTime;
    }

    public void setMarketOpenEndTime(String marketOpenEndTime) {
        this.marketOpenEndTime = marketOpenEndTime;
    }
}
