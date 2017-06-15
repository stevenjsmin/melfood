package melfood.shopping.grouppurchase;

import melfood.framework.uitl.html.Option;
import melfood.shopping.grouppurchase.dto.GroupPurchase;

import java.util.List;

/**
 * Created by Steven on 14/6/17.
 */
public interface GroupPurchaseService {

    public GroupPurchase getGroupPurchase(int groupPurchaseId) throws Exception;
    public List<GroupPurchase> getGroupPurchases(GroupPurchase groupPurchase) throws Exception;

    public Integer getTotalCntForGroupPurchases(GroupPurchase groupPurchase);

    public Integer deleteGroupPurchase(GroupPurchase groupPurchase) throws Exception;

    public Integer insertGroupPurchase(GroupPurchase groupPurchase) throws Exception;

    public Integer modifyGroupPurchase(GroupPurchase groupPurchase) throws Exception;

    public Integer modifyGroupPurchaseForNotNull(GroupPurchase groupPurchase) throws Exception;

    public List<Option> getOrganizers()throws Exception;
    public List<Option> getMarketSuburbs()throws Exception;
    public List<Option> getDiscountMethods()throws Exception;
    public List<Option> getStopSellingOptions()throws Exception;

}
