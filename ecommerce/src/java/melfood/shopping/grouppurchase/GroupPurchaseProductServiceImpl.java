package melfood.shopping.grouppurchase;

import melfood.shopping.grouppurchase.dao.GroupPurchaseProductDAO;
import melfood.shopping.grouppurchase.dto.GroupPurchaseProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Steven on 15/6/17.
 */
@Service
public class GroupPurchaseProductServiceImpl implements GroupPurchaseProductService {

    @Autowired
    GroupPurchaseProductDAO groupPurchaseProductDao;

    @Override
    public GroupPurchaseProduct getGroupPurchaseProduct(String groupPurchaseId, String productId) throws Exception {
        return this.getGroupPurchaseProduct(Integer.parseInt(groupPurchaseId), Integer.parseInt(productId));
    }

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
    public List<GroupPurchaseProduct> getGroupPurchaseProducts(String groupPurchaseId) throws Exception {
        return this.getGroupPurchaseProducts(Integer.parseInt(groupPurchaseId));
    }

    @Override
    public List<GroupPurchaseProduct> getGroupPurchaseProducts(int groupPurchaseId) throws Exception {
        GroupPurchaseProduct purchaseProduct = new GroupPurchaseProduct();
        purchaseProduct.setGroupPurchaseId(groupPurchaseId);

        return this.getGroupPurchaseProducts(purchaseProduct);
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
    public Integer deleteGroupPurchaseProducts(int groupPurchaseId) throws Exception {
        return groupPurchaseProductDao.deleteGroupPurchaseProducts(groupPurchaseId);
    }

    @Override
    public Integer deleteGroupPurchaseProduct(int groupPurchaseId, int productId) throws Exception {
        return this.deleteGroupPurchaseProduct(new GroupPurchaseProduct(groupPurchaseId, productId));
    }

    @Override
    public Integer deleteGroupPurchaseProduct(GroupPurchaseProduct purchaseProduct) throws Exception {
        if (purchaseProduct == null || purchaseProduct.getGroupPurchaseId() == 0 || purchaseProduct.getProductId() == 0) {
            throw new Exception("등록된 사품의 groupPurchaseId 또는 productId를 확인해주세요");
        }
        List<GroupPurchaseProduct> purchaseProducts = new ArrayList<GroupPurchaseProduct>();
        purchaseProducts.add(purchaseProduct);

        return this.deleteGroupPurchaseProducts(purchaseProducts);
    }

    @Override
    public Integer deleteGroupPurchaseProducts(List<GroupPurchaseProduct> purchaseProducts) throws Exception {

        int updateCnt = 0;
        int updateCntTotal = 0;
        for (GroupPurchaseProduct product : purchaseProducts) {
            updateCnt = groupPurchaseProductDao.deleteGroupPurchaseProduct(product);
            updateCntTotal = updateCntTotal + updateCnt;
        }
        return updateCntTotal;
    }


    @Override
    public Integer insertGroupPurchaseProduct(GroupPurchaseProduct purchaseProduct) throws Exception {
        List<GroupPurchaseProduct> purchaseProducts = new ArrayList<GroupPurchaseProduct>();
        purchaseProducts.add(purchaseProduct);
        return groupPurchaseProductDao.insertGroupPurchaseProduct(purchaseProducts);
    }

    @Override
    public Integer modifyGroupPurchaseProduct(GroupPurchaseProduct purchaseProduct) throws Exception {
        return groupPurchaseProductDao.modifyGroupPurchaseProduct(purchaseProduct);
    }

    @Override
    public Integer modifyGroupPurchaseProductForNotNull(GroupPurchaseProduct purchaseProduct) throws Exception {
        return groupPurchaseProductDao.modifyGroupPurchaseProductForNotNull(purchaseProduct);
    }

    @Override
    public Integer modifyGroupPurchaseStopSelling(GroupPurchaseProduct purchaseProduct) throws Exception {
        return groupPurchaseProductDao.modifyGroupPurchaseStopSelling(purchaseProduct);
    }

}
