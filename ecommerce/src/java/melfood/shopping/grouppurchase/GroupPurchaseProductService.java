package melfood.shopping.grouppurchase;

import melfood.shopping.grouppurchase.dto.GroupPurchaseProduct;

import java.util.List;

/**
 * Created by Steven on 14/6/17.
 */
public interface GroupPurchaseProductService {

    public GroupPurchaseProduct getGroupPurchaseProduct(String groupPurchaseId, String productId) throws Exception;
    public GroupPurchaseProduct getGroupPurchaseProduct(int groupPurchaseId, int productId) throws Exception;
    public List<GroupPurchaseProduct> getGroupPurchaseProducts(GroupPurchaseProduct purchaseProduct) throws Exception;
    public List<GroupPurchaseProduct> getGroupPurchaseProducts(int groupPurchaseId) throws Exception;
    public List<GroupPurchaseProduct> getGroupPurchaseProducts(String groupPurchaseId) throws Exception;

    public Integer getTotalCntForGroupPurchaseProducts(GroupPurchaseProduct purchaseProduct);

    public Integer deleteGroupPurchaseProducts(int groupPurchaseId) throws Exception;
    public Integer deleteGroupPurchaseProduct(int groupPurchaseId, int productId) throws Exception;
    public Integer deleteGroupPurchaseProduct(GroupPurchaseProduct purchaseProduct) throws Exception;
    public Integer deleteGroupPurchaseProducts(List<GroupPurchaseProduct> purchaseProducts) throws Exception;

    public Integer insertGroupPurchaseProduct(GroupPurchaseProduct purchaseProduct) throws Exception;

    public Integer modifyGroupPurchaseProduct(GroupPurchaseProduct purchaseProduct) throws Exception;

    public Integer modifyGroupPurchaseProductForNotNull(GroupPurchaseProduct purchaseProduct) throws Exception;

    public Integer modifyGroupPurchaseStopSelling(GroupPurchaseProduct purchaseProduct) throws Exception;

}
