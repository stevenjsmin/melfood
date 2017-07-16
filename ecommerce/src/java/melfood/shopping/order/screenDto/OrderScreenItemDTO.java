package melfood.shopping.order.screenDto;

import java.util.List;
import java.util.Map;

/**
 * Created by Steven on 11/7/17.
 */
public class OrderScreenItemDTO {
    List<Map<String, String>> productOrder;
    List<Map<String, String>> options;

    public List<Map<String, String>> getProductOrder() {
        return productOrder;
    }

    public void setProductOrder(List<Map<String, String>> productOrder) {
        this.productOrder = productOrder;
    }

    public List<Map<String, String>> getOptions() {
        return options;
    }

    public void setOptions(List<Map<String, String>> options) {
        this.options = options;
    }
}
