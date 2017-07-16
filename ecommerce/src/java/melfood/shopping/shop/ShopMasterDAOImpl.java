package melfood.shopping.shop;

import melfood.framework.core.BaseDAO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Steven on 16/7/17.
 */
@Repository
public class ShopMasterDAOImpl extends BaseDAO implements ShopMasterDAO {

    @Override
    public List<ShopTemplate> getShopTemplates(ShopTemplate shopTemplate) throws Exception {
        return sqlSession.selectList("mySqlShopMasterDao.getShopTemplates", shopTemplate);
    }

    @Override
    public List<ShopMaster> getShopMasters(ShopMaster shopMaster) throws Exception {
        return sqlSession.selectList("mySqlShopMasterDao.getShopMasters", shopMaster);
    }

    @Override
    public Integer getTotalCntForGetShopMasters(ShopMaster shopMaster) {
        return sqlSession.selectOne("mySqlShopMasterDao.getTotalCntForGetShopMasters", shopMaster);
    }

    @Override
    public Integer insertShopMaster(ShopMaster shopMaster) throws Exception {
        return sqlSession.insert("mySqlShopMasterDao.insertShopMaster", shopMaster);
    }

    @Override
    public Integer modifyShopMaster(ShopMaster shopMaster) throws Exception {
        return sqlSession.update("mySqlShopMasterDao.modifyShopMaster", shopMaster);
    }

    @Override
    public Integer deleteShopMaster(ShopMaster shopMaster) throws Exception {
        return sqlSession.delete("mySqlShopMasterDao.deleteShopMaster", shopMaster);
    }
}
