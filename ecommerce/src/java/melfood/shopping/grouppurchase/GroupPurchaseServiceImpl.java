package melfood.shopping.grouppurchase;

import melfood.framework.uitl.html.Option;
import melfood.shopping.grouppurchase.dao.GroupPurchaseDAO;
import melfood.shopping.grouppurchase.dto.GroupPurchase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Steven on 14/6/17.
 */
@Service
public class GroupPurchaseServiceImpl implements GroupPurchaseService {

    @Autowired
    GroupPurchaseDAO groupPurchaseDAO;

    @Override
    public GroupPurchase getGroupPurchase(int groupPurchaseId) throws Exception {

        List<GroupPurchase> groupPurchase = this.getGroupPurchases(new GroupPurchase(groupPurchaseId));
        if (groupPurchase != null & groupPurchase.size() > 0) {
            return groupPurchase.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<GroupPurchase> getGroupPurchases(GroupPurchase groupPurchase) throws Exception {
        return groupPurchaseDAO.getGroupPurchases(groupPurchase);
    }

    @Override
    public Integer getTotalCntForGroupPurchases(GroupPurchase groupPurchase) {
        return groupPurchaseDAO.getTotalCntForGroupPurchases(groupPurchase);
    }

    @Override
    public Integer deleteGroupPurchase(GroupPurchase groupPurchase) throws Exception {
        return groupPurchaseDAO.deleteGroupPurchase(groupPurchase);
    }

    @Override
    public Integer insertGroupPurchase(GroupPurchase groupPurchase) throws Exception {
        return groupPurchaseDAO.insertGroupPurchase(groupPurchase);
    }

    @Override
    public Integer modifyGroupPurchase(GroupPurchase groupPurchase) throws Exception {
        return groupPurchaseDAO.modifyGroupPurchase(groupPurchase);
    }

    @Override
    public Integer modifyGroupPurchaseForNotNull(GroupPurchase groupPurchase) throws Exception {
        return groupPurchaseDAO.modifyGroupPurchaseForNotNull(groupPurchase);
    }

    @Override
    public List<Option> getOrganizers() throws Exception {
        return null;
    }

    @Override
    public List<Option> getOrganizers(String selectedUserId) throws Exception {
        return null;
    }

    @Override
    public List<Option> getMarketSuburbs() throws Exception {
        return null;
    }

    @Override
    public List<Option> getDiscountMethods() throws Exception {
        return null;
    }

    @Override
    public List<Option> getStopSellingOptions() throws Exception {
        return null;
    }
}
