package melfood.shopping.order;

import melfood.framework.common.dto.BaseDto;

/**
 * Created by Steven on 4/7/17.
 */
public class OrderMasterProductOption extends BaseDto {
    private int orderMasterProductOptionId;
    private int orderMasterProductId;
    private String optionName;
    private String optionValue;
    private Float addtionalPrice;

    public OrderMasterProductOption() {
    }

    public OrderMasterProductOption(int orderMasterProductOptionId) {
        this.orderMasterProductOptionId = orderMasterProductOptionId;
        this.orderMasterProductId = orderMasterProductId;
        this.optionName = optionName;
        this.optionValue = optionValue;
    }

    public OrderMasterProductOption(String orderMasterProductOptionId) {
        this.orderMasterProductOptionId = Integer.parseInt(orderMasterProductOptionId);
        this.optionName = optionName;
        this.optionValue = optionValue;
    }


    public OrderMasterProductOption(int orderMasterProductOptionId, int orderMasterProductId) {
        this.orderMasterProductOptionId = orderMasterProductOptionId;
        this.orderMasterProductId = orderMasterProductId;
        this.optionName = optionName;
        this.optionValue = optionValue;
    }

    public OrderMasterProductOption(String orderMasterProductOptionId, String orderMasterProductId) {
        this.orderMasterProductOptionId = Integer.parseInt(orderMasterProductOptionId);
        this.orderMasterProductId = Integer.parseInt(orderMasterProductId);
        this.optionName = optionName;
        this.optionValue = optionValue;
    }

    public OrderMasterProductOption(int orderMasterProductOptionId, int orderMasterProductId, String optionName, String optionValue) {
        this.orderMasterProductOptionId = orderMasterProductOptionId;
        this.orderMasterProductId = orderMasterProductId;
        this.optionName = optionName;
        this.optionValue = optionValue;
    }

    public OrderMasterProductOption(String orderMasterProductOptionId, String orderMasterProductId, String optionName, String optionValue) {
        this.orderMasterProductOptionId = Integer.parseInt(orderMasterProductOptionId);
        this.orderMasterProductId = Integer.parseInt(orderMasterProductId);
        this.optionName = optionName;
        this.optionValue = optionValue;
    }


    public OrderMasterProductOption(int orderMasterProductOptionId, int orderMasterProductId, String optionName, String optionValue, Float addtionalPrice) {
        this.orderMasterProductOptionId = orderMasterProductOptionId;
        this.orderMasterProductId = orderMasterProductId;
        this.optionName = optionName;
        this.optionValue = optionValue;
        this.addtionalPrice = addtionalPrice;
    }

    public OrderMasterProductOption(String orderMasterProductOptionId, String orderMasterProductId, String optionName, String optionValue, Float addtionalPrice) {
        this.orderMasterProductOptionId = Integer.parseInt(orderMasterProductOptionId);
        this.orderMasterProductId = Integer.parseInt(orderMasterProductId);
        this.optionName = optionName;
        this.optionValue = optionValue;
        this.addtionalPrice = addtionalPrice;
    }

    public int getOrderMasterProductOptionId() {
        return orderMasterProductOptionId;
    }

    public void setOrderMasterProductOptionId(int orderMasterProductOptionId) {
        this.orderMasterProductOptionId = orderMasterProductOptionId;
    }

    public int getOrderMasterProductId() {
        return orderMasterProductId;
    }

    public void setOrderMasterProductId(int orderMasterProductId) {
        this.orderMasterProductId = orderMasterProductId;
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

    public Float getAddtionalPrice() {
        return addtionalPrice;
    }

    public void setAddtionalPrice(Float addtionalPrice) {
        this.addtionalPrice = addtionalPrice;
    }

    @Override
    public String toString() {
        return "OrderMasterProductOption{" +
                "orderMasterProductOptionId=" + orderMasterProductOptionId +
                ", orderMasterProductId=" + orderMasterProductId +
                ", optionName='" + optionName + '\'' +
                ", optionValue='" + optionValue + '\'' +
                ", addtionalPrice=" + addtionalPrice +
                '}';
    }
}


