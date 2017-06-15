package melfood.shopping.grouppurchase.dao;

import melfood.shopping.grouppurchase.dto.GroupPurchaseProduct;

import java.util.List;

/**
 * Created by Steven on 14/6/17.
 */
public interface GroupPurchaseProductDAO {

    public List<GroupPurchaseProduct> getGroupPurchaseProducts(GroupPurchaseProduct purchaseProduct) throws Exception;

    public Integer getTotalCntForGroupPurchaseProducts(GroupPurchaseProduct purchaseProduct);

    public Integer deleteGroupPurchaseProduct(GroupPurchaseProduct purchaseProduct) throws Exception;
    public Integer deleteGroupPurchaseProducts(List<GroupPurchaseProduct> purchaseProducts) throws Exception;

    public Integer insertGroupPurchaseProduct(GroupPurchaseProduct purchaseProduct) throws Exception;

    public Integer modifyGroupPurchaseProduct(GroupPurchaseProduct purchaseProduct) throws Exception;

    public Integer modifyGroupPurchaseProductForNotNull(GroupPurchaseProduct purchaseProduct) throws Exception;
}
