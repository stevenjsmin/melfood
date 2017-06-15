package melfood.shopping.grouppurchase;

import melfood.shopping.grouppurchase.dao.GroupPurchaseProductDAO;
import melfood.shopping.grouppurchase.dto.GroupPurchaseProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Steven on 15/6/17.
 */
@Service
public class GroupPurchaseProductServiceImpl implements GroupPurchaseProductService {

    @Autowired
    GroupPurchaseProductDAO groupPurchaseProductDao;

    @Override
    public GroupPurchaseProduct getGroupPurchaseProduct(int groupPurchaseId, int productId) throws Exception {
        List<GroupPurchaseProduct> groupPurchaseProducts = this.getGroupPurchaseProducts(new GroupPurchaseProduct(groupPurchaseId, productId));
        if (groupPurchaseProducts != null & groupPurchaseProducts.size() > 0) {
            return groupPurchaseProducts.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<GroupPurchaseProduct> getGroupPurchaseProducts(GroupPurchaseProduct purchaseProduct) throws Exception {
        return groupPurchaseProductDao.getGroupPurchaseProducts(purchaseProduct);
    }

    @Override
    public Integer getTotalCntForGroupPurchaseProducts(GroupPurchaseProduct purchaseProduct) {
        return groupPurchaseProductDao.getTotalCntForGroupPurchaseProducts(purchaseProduct);
    }

    @Override
    public Integer deleteGroupPurchaseProduct(GroupPurchaseProduct purchaseProduct) throws Exception {
        return groupPurchaseProductDao.deleteGroupPurchaseProduct(purchaseProduct);
    }

    @Override
    public Integer deleteGroupPurchaseProducts(List<GroupPurchaseProduct> purchaseProducts) throws Exception {
        return groupPurchaseProductDao.deleteGroupPurchaseProducts(purchaseProducts);
    }

    @Override
    public Integer insertGroupPurchaseProduct(GroupPurchaseProduct purchaseProduct) throws Exception {
        return groupPurchaseProductDao.insertGroupPurchaseProduct(purchaseProduct);
    }

    @Override
    public Integer modifyGroupPurchaseProduct(GroupPurchaseProduct purchaseProduct) throws Exception {
        return groupPurchaseProductDao.modifyGroupPurchaseProduct(purchaseProduct);
    }

    @Override
    public Integer modifyGroupPurchaseProductForNotNull(GroupPurchaseProduct purchaseProduct) throws Exception {
        return groupPurchaseProductDao.modifyGroupPurchaseProductForNotNull(purchaseProduct);
    }
}
