package melfood.shopping.order.screenDto;

import melfood.framework.user.User;

import java.util.List;

/**
 * Created by Steven on 11/7/17.
 */
public class OrderScreenDTO {
    private String groupPurchaseId;
    private List<OrderScreenItemDTO> items;
    private String deliveryService;
    private String deliveryDistance;
    private String customerOrderNote;
    private String paymentMethod;

    private User customer;

    public String getGroupPurchaseId() {
        return groupPurchaseId;
    }

    public void setGroupPurchaseId(String groupPurchaseId) {
        this.groupPurchaseId = groupPurchaseId;
    }

    public String getDeliveryService() {
        return deliveryService;
    }

    public void setDeliveryService(String deliveryService) {
        this.deliveryService = deliveryService;
    }

    public String getDeliveryDistance() {
        return deliveryDistance;
    }

    public void setDeliveryDistance(String deliveryDistance) {
        this.deliveryDistance = deliveryDistance;
    }

    public List<OrderScreenItemDTO> getItems() {
        return items;
    }

    public void setItems(List<OrderScreenItemDTO> items) {
        this.items = items;
    }

    public String getCustomerOrderNote() {
        return customerOrderNote;
    }

    public void setCustomerOrderNote(String customerOrderNote) {
        this.customerOrderNote = customerOrderNote;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
