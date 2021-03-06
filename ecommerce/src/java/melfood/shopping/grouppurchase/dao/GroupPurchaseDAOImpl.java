package melfood.shopping.grouppurchase.dao;

import melfood.framework.core.BaseDAO;
import melfood.shopping.grouppurchase.dto.GroupPurchase;
import melfood.shopping.product.ProductImage;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Steven on 14/6/17.
 */
@Repository
public class GroupPurchaseDAOImpl extends BaseDAO implements GroupPurchaseDAO {

    @Override
    public List<GroupPurchase> getGroupPurchases(GroupPurchase groupPurchase) throws Exception {
        return sqlSession.selectList("mySqlGroupPurchaseDao.getGroupPurchase", groupPurchase);
    }

    @Override
    public Integer getTotalCntForGroupPurchases(GroupPurchase groupPurchase) {
        return sqlSession.selectOne("mySqlGroupPurchaseDao.getTotalCntGroupPurchase", groupPurchase);
    }

    @Override
    public Integer deleteGroupPurchase(GroupPurchase groupPurchase) throws Exception {
        return sqlSession.delete("mySqlGroupPurchaseDao.deleteGroupPurchase", groupPurchase);
    }

    @Override
    public Integer insertGroupPurchase(GroupPurchase groupPurchase) throws Exception {
        return sqlSession.insert("mySqlGroupPurchaseDao.insertGroupPurchase", groupPurchase);
    }

    @Override
    public Integer modifyGroupPurchase(GroupPurchase groupPurchase) throws Exception {
        return sqlSession.update("mySqlGroupPurchaseDao.modifyGroupPurchase", groupPurchase);
    }

    @Override
    public Integer modifyGroupPurchaseForNotNull(GroupPurchase groupPurchase) throws Exception {
        return sqlSession.update("mySqlGroupPurchaseDao.modifyGroupPurchaseForNotNull", groupPurchase);
    }

    @Override
    public Integer insertGroupPurchaseImage(ProductImage productImage) throws Exception {
        int nextSeq = sqlSession.selectOne("mySqlGroupPurchaseDao.getNextPurchaseGroupImageSeq", productImage.getProdId());
        productImage.setImageSeq(nextSeq);
        return sqlSession.delete("mySqlGroupPurchaseDao.insertGroupPurchaseImage", productImage);
    }

    @Override
    public Integer insertGroupPurchaseImages(List<ProductImage> productImages, int groupPurchaseId) throws Exception {
        int nextSeq = sqlSession.selectOne("mySqlGroupPurchaseDao.getNextPurchaseGroupImageSeq", groupPurchaseId);
        for (int i = 0; i < productImages.size(); i++) {
            productImages.get(i).setImageSeq(nextSeq);
            nextSeq++;
        }
        return sqlSession.insert("mySqlGroupPurchaseDao.insertGroupPurchaseImages", productImages);
    }

    @Override
    public List<ProductImage> getProductImages(ProductImage productImage) throws Exception {
        return sqlSession.selectList("mySqlGroupPurchaseDao.getProductImages", productImage);
    }

    @Override
    public Integer getTotalCntForProductImages(ProductImage productImage) {
        return sqlSession.selectOne("mySqlGroupPurchaseDao.getTotalCntForProductImages", productImage);
    }

    public Integer deleteProductImage(ProductImage productImage) throws Exception {
        return sqlSession.delete("mySqlGroupPurchaseDao.deleteProductImage", productImage);
    }

    /**
     * 몰 프론트페이지게 보여줄 공구 리스트를 가져온다
     *
     * @param groupPurchase
     * @return
     * @throws Exception
     */
    @Override
    public List<GroupPurchase> getGroupPurchaseForMallFront(GroupPurchase groupPurchase) throws Exception {
        return sqlSession.selectList("mySqlGroupPurchaseDao.getGroupPurchaseForMallFront", groupPurchase);
    }

    /**
     * 몰 프론트페이지게 보여줄 공구 리스트를 가져오기위한 카운트
     *
     * @param groupPurchase
     * @return
     */
    @Override
    public Integer getTotalCntGroupPurchaseForMallFront(GroupPurchase groupPurchase) {
        return sqlSession.selectOne("mySqlGroupPurchaseDao.getTotalCntGroupPurchaseForMallFront", groupPurchase);
    }
}
