package melfood.shopping.grouppurchase.dao;

import melfood.shopping.grouppurchase.dto.GroupPurchaseProduct;

import java.util.List;

/**
 * Created by Steven on 14/6/17.
 */
public interface GroupPurchaseProductDAO {

    public List<GroupPurchaseProduct> getGroupPurchaseProducts(GroupPurchaseProduct purchaseProduct) throws Exception;

    public Integer getTotalCntForGroupPurchaseProducts(GroupPurchaseProduct purchaseProduct);

    /**
     * 동공구매ID(group_purchase_id)와 상품ID(ProdID)에 해당하는 등록된 상품하나를 삭제한다
     *
     * @param purchaseProduct
     * @return
     * @throws Exception
     */
    public Integer deleteGroupPurchaseProduct(GroupPurchaseProduct purchaseProduct) throws Exception;

    /**
     * 해당 공동구매에 속한 모든 등록된 상품을 삭제한다.
     *
     * @param groupPurchaseId
     * @return
     * @throws Exception
     */
    public Integer deleteGroupPurchaseProducts(int groupPurchaseId) throws Exception;

    public Integer insertGroupPurchaseProduct(List<GroupPurchaseProduct> purchaseProducts) throws Exception;

    public Integer modifyGroupPurchaseProduct(GroupPurchaseProduct purchaseProduct) throws Exception;

    public Integer modifyGroupPurchaseProductForNotNull(GroupPurchaseProduct purchaseProduct) throws Exception;

}
