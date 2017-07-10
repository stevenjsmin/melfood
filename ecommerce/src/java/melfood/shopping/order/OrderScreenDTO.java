package melfood.shopping.order;

import java.util.List;

/**
 * Created by Steven on 11/7/17.
 */
public class OrderScreenDTO {
    private String groupPurchaseId;
    private List<OrderScreenItemDTO> items;
    private String deliveryService;
    private String deliveryDistance;

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
}
