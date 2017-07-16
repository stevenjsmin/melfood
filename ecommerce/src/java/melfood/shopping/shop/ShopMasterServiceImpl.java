package melfood.shopping.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Steven on 16/7/17.
 */
@Service
public class ShopMasterServiceImpl implements ShopMasterService {
    @Autowired
    ShopMasterDAO shopMasterDAO;

    @Override
    public ShopMaster getShopMaster(ShopMaster shopMaster) throws Exception {
        List<ShopMaster> list = this.getShopMasters(shopMaster);
        if (list.size() > 0) return list.get(0);
        else
            return null;
    }

    @Override
    public List<ShopMaster> getShopMasters(ShopMaster shopMaster) throws Exception {
        return shopMasterDAO.getShopMasters(shopMaster);
    }

    @Override
    public Integer getTotalCntForGetShopMasters(ShopMaster shopMaster) {
        return shopMasterDAO.getTotalCntForGetShopMasters(shopMaster);
    }

    @Override
    public Integer insertShopMaster(ShopMaster shopMaster) throws Exception {
        return shopMasterDAO.insertShopMaster(shopMaster);
    }

    @Override
    public Integer modifyShopMaster(ShopMaster shopMaster) throws Exception {
        return shopMasterDAO.modifyShopMaster(shopMaster);
    }

    @Override
    public Integer deleteShopMaster(ShopMaster shopMaster) throws Exception {
        return shopMasterDAO.deleteShopMaster(shopMaster);
    }

    @Override
    public List<ShopTemplate> getShopTemplates(ShopTemplate shopTemplate) throws Exception {
        return shopMasterDAO.getShopTemplates(shopTemplate);
    }
}
