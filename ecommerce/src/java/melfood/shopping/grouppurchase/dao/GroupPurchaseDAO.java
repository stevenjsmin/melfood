package melfood.shopping.grouppurchase.dao;

import melfood.shopping.grouppurchase.dto.GroupPurchase;

import java.util.List;

/**
 * Created by Steven on 14/6/17.
 */
public interface GroupPurchaseDAO {

    public List<GroupPurchase> getGroupPurchases(GroupPurchase groupPurchase) throws Exception;

    public Integer getTotalCntForGroupPurchases(GroupPurchase groupPurchase);

    public Integer deleteGroupPurchase(GroupPurchase groupPurchase) throws Exception;

    public Integer insertGroupPurchase(GroupPurchase groupPurchase) throws Exception;

    public Integer modifyGroupPurchase(GroupPurchase groupPurchase) throws Exception;

    public Integer modifyGroupPurchaseForNotNull(GroupPurchase groupPurchase) throws Exception;

}
