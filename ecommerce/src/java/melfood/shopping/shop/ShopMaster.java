package melfood.shopping.shop;

import melfood.framework.common.dto.BaseDto;
import melfood.framework.user.User;

/**
 * Created by Steven on 16/7/17.
 */
public class ShopMaster extends BaseDto {

    private Integer shopId;
    private Integer shopCredit;
    private String shopName;
    private String shopOwner;
    private String shopOwnerName;
    private Integer shopGateImageFileId;
    private String notice;
    private Integer templateId;
    private String templateName;
    private String addressStreet;
    private String addressSuburb;
    private String addressState;
    private String addressPostcode;
    private String addressLatitude;
    private String addressLongitude;
    private String addressFormattedAddress;
    private String forDeliverCalcAddressStreet;
    private String forDeliverCalcAddressSuburb;
    private String forDeliverCalcAddressState;
    private String forDeliverCalcAddressPostcode;
    private String forDeliverCalcAddressLatitude;
    private String forDeliverCalcAddressLongitude;
    private String forDeliverCalcAddressFormattedAddress;
    private String deliveryService;
    private String deliveryFeePerKm;
    private Float deliveryBaseCharge;
    private Integer deliveryLimitKm;
    private Float minimumPurchaseAmount;
    private Float maximumPurchaseAmount;
    private String discountMethod;
    private Float discountFixedAmount;
    private Float discountRateValue;

    private ShopTemplate shopTemplate;
    private User owner; // Detail info of shopOwner


    public ShopMaster() {
    }

    public ShopMaster(Integer shopId) {
        this.shopId = shopId;
    }

    public ShopMaster(String shopId) {
        this.shopId = Integer.parseInt(shopId);
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Integer getShopCredit() {
        return shopCredit;
    }

    public void setShopCredit(Integer shopCredit) {
        this.shopCredit = shopCredit;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopOwner() {
        return shopOwner;
    }

    public void setShopOwner(String shopOwner) {
        this.shopOwner = shopOwner;
    }

    public Integer getShopGateImageFileId() {
        return shopGateImageFileId;
    }

    public void setShopGateImageFileId(Integer shopGateImageFileId) {
        this.shopGateImageFileId = shopGateImageFileId;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public String getAddressStreet() {
        return addressStreet;
    }

    public void setAddressStreet(String addressStreet) {
        this.addressStreet = addressStreet;
    }

    public String getAddressSuburb() {
        return addressSuburb;
    }

    public void setAddressSuburb(String addressSuburb) {
        this.addressSuburb = addressSuburb;
    }

    public String getAddressState() {
        return addressState;
    }

    public void setAddressState(String addressState) {
        this.addressState = addressState;
    }

    public String getAddressPostcode() {
        return addressPostcode;
    }

    public void setAddressPostcode(String addressPostcode) {
        this.addressPostcode = addressPostcode;
    }

    public String getAddressLatitude() {
        return addressLatitude;
    }

    public void setAddressLatitude(String addressLatitude) {
        this.addressLatitude = addressLatitude;
    }

    public String getAddressLongitude() {
        return addressLongitude;
    }

    public void setAddressLongitude(String addressLongitude) {
        this.addressLongitude = addressLongitude;
    }

    public String getAddressFormattedAddress() {
        return addressFormattedAddress;
    }

    public void setAddressFormattedAddress(String addressFormattedAddress) {
        this.addressFormattedAddress = addressFormattedAddress;
    }

    public String getForDeliverCalcAddressStreet() {
        return forDeliverCalcAddressStreet;
    }

    public void setForDeliverCalcAddressStreet(String forDeliverCalcAddressStreet) {
        this.forDeliverCalcAddressStreet = forDeliverCalcAddressStreet;
    }

    public String getForDeliverCalcAddressSuburb() {
        return forDeliverCalcAddressSuburb;
    }

    public void setForDeliverCalcAddressSuburb(String forDeliverCalcAddressSuburb) {
        this.forDeliverCalcAddressSuburb = forDeliverCalcAddressSuburb;
    }

    public String getForDeliverCalcAddressState() {
        return forDeliverCalcAddressState;
    }

    public void setForDeliverCalcAddressState(String forDeliverCalcAddressState) {
        this.forDeliverCalcAddressState = forDeliverCalcAddressState;
    }

    public String getForDeliverCalcAddressPostcode() {
        return forDeliverCalcAddressPostcode;
    }

    public void setForDeliverCalcAddressPostcode(String forDeliverCalcAddressPostcode) {
        this.forDeliverCalcAddressPostcode = forDeliverCalcAddressPostcode;
    }

    public String getForDeliverCalcAddressLatitude() {
        return forDeliverCalcAddressLatitude;
    }

    public void setForDeliverCalcAddressLatitude(String forDeliverCalcAddressLatitude) {
        this.forDeliverCalcAddressLatitude = forDeliverCalcAddressLatitude;
    }

    public String getForDeliverCalcAddressLongitude() {
        return forDeliverCalcAddressLongitude;
    }

    public void setForDeliverCalcAddressLongitude(String forDeliverCalcAddressLongitude) {
        this.forDeliverCalcAddressLongitude = forDeliverCalcAddressLongitude;
    }

    public String getForDeliverCalcAddressFormattedAddress() {
        return forDeliverCalcAddressFormattedAddress;
    }

    public void setForDeliverCalcAddressFormattedAddress(String forDeliverCalcAddressFormattedAddress) {
        this.forDeliverCalcAddressFormattedAddress = forDeliverCalcAddressFormattedAddress;
    }

    public String getDeliveryService() {
        return deliveryService;
    }

    public void setDeliveryService(String deliveryService) {
        this.deliveryService = deliveryService;
    }

    public String getDeliveryFeePerKm() {
        return deliveryFeePerKm;
    }

    public void setDeliveryFeePerKm(String deliveryFeePerKm) {
        this.deliveryFeePerKm = deliveryFeePerKm;
    }

    public Float getDeliveryBaseCharge() {
        return deliveryBaseCharge;
    }

    public void setDeliveryBaseCharge(Float deliveryBaseCharge) {
        this.deliveryBaseCharge = deliveryBaseCharge;
    }

    public Float getMinimumPurchaseAmount() {
        return minimumPurchaseAmount;
    }

    public void setMinimumPurchaseAmount(Float minimumPurchaseAmount) {
        this.minimumPurchaseAmount = minimumPurchaseAmount;
    }

    public Float getMaximumPurchaseAmount() {
        return maximumPurchaseAmount;
    }

    public void setMaximumPurchaseAmount(Float maximumPurchaseAmount) {
        this.maximumPurchaseAmount = maximumPurchaseAmount;
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

    public ShopTemplate getShopTemplate() {
        return shopTemplate;
    }

    public void setShopTemplate(ShopTemplate shopTemplate) {
        this.shopTemplate = shopTemplate;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getShopOwnerName() {
        return shopOwnerName;
    }

    public void setShopOwnerName(String shopOwnerName) {
        this.shopOwnerName = shopOwnerName;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public Integer getDeliveryLimitKm() {
        return deliveryLimitKm;
    }

    public void setDeliveryLimitKm(Integer deliveryLimitKm) {
        this.deliveryLimitKm = deliveryLimitKm;
    }
}
