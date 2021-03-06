package melfood.shopping.grouppurchase.dto;

import melfood.framework.common.dto.BaseDto;
import melfood.shopping.product.Product;

/**
 * Created by Steven on 13/6/17.
 */
public class GroupPurchaseProduct extends BaseDto {
    private int groupPurchaseId;
    private int productId;
    private String productName;
    private String productOwner;
    private String stopSelling;
    private String stopSellingReason;

    // For additional info of product
    private String unitPrice;
    private String checkBeforeBuy;
    private String productDescription;

    private Product product;


    public GroupPurchaseProduct() {
    }

    public GroupPurchaseProduct(int groupPurchaseId) {
        this.groupPurchaseId = groupPurchaseId;
    }

    public GroupPurchaseProduct(String groupPurchaseId) {
        this.groupPurchaseId = Integer.parseInt(groupPurchaseId);
    }

    public GroupPurchaseProduct(int groupPurchaseId, int productId) {
        this.groupPurchaseId = groupPurchaseId;
        this.productId = productId;
    }

    public GroupPurchaseProduct(String groupPurchaseId, String productId) {
        this.groupPurchaseId = Integer.parseInt(groupPurchaseId);
        this.productId = Integer.parseInt(productId);
    }

    public int getGroupPurchaseId() {
        return groupPurchaseId;
    }

    public void setGroupPurchaseId(int groupPurchaseId) {
        this.groupPurchaseId = groupPurchaseId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductOwner() {
        return productOwner;
    }

    public void setProductOwner(String productOwner) {
        this.productOwner = productOwner;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getCheckBeforeBuy() {
        return checkBeforeBuy;
    }

    public void setCheckBeforeBuy(String checkBeforeBuy) {
        this.checkBeforeBuy = checkBeforeBuy;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
