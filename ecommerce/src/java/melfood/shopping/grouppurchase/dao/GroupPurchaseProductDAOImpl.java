package melfood.shopping.grouppurchase.dao;

import melfood.framework.core.BaseDAO;
import melfood.shopping.grouppurchase.dto.GroupPurchaseProduct;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Steven on 14/6/17.
 */
@Repository
public class GroupPurchaseProductDAOImpl extends BaseDAO implements GroupPurchaseProductDAO {

    @Override
    public List<GroupPurchaseProduct> getGroupPurchaseProducts(GroupPurchaseProduct purchaseProduct) throws Exception {
        return sqlSession.selectList("mySqlGroupPurchaseProductDao.getGroupPurchaseProducts", purchaseProduct);
    }

    @Override
    public Integer getTotalCntForGroupPurchaseProducts(GroupPurchaseProduct purchaseProduct) {
        return sqlSession.selectOne("mySqlGroupPurchaseProductDao.getTotalCntForGroupPurchaseProducts", purchaseProduct);
    }

    @Override
    public Integer deleteGroupPurchaseProduct(GroupPurchaseProduct purchaseProduct) throws Exception {
        return sqlSession.delete("mySqlGroupPurchaseProductDao.deleteGroupPurchaseProduct", purchaseProduct);
    }

    @Override
    public Integer deleteGroupPurchaseProducts(int groupPurchaseId) throws Exception {
        return sqlSession.delete("mySqlGroupPurchaseProductDao.deleteGroupPurchaseProducts", groupPurchaseId);
    }


    @Override
    public Integer insertGroupPurchaseProduct(List<GroupPurchaseProduct> purchaseProducts) throws Exception {
        return sqlSession.insert("mySqlGroupPurchaseProductDao.insertGroupPurchaseProducts", purchaseProducts);
    }

    @Override
    public Integer modifyGroupPurchaseProduct(GroupPurchaseProduct purchaseProduct) throws Exception {
        return sqlSession.update("mySqlGroupPurchaseProductDao.modifyGroupPurchaseProduct", purchaseProduct);
    }

    @Override
    public Integer modifyGroupPurchaseProductForNotNull(GroupPurchaseProduct purchaseProduct) throws Exception {
        return sqlSession.update("mySqlGroupPurchaseProductDao.modifyGroupPurchaseProductForNotNull", purchaseProduct);
    }

}
