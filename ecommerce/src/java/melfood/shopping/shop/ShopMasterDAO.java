package melfood.shopping.shop;

import java.util.List;

/**
 * Created by Steven on 16/7/17.
 */
public interface ShopMasterDAO {

    public List<ShopTemplate> getShopTemplates(ShopTemplate shopTemplate) throws Exception;

    public List<ShopMaster> getShopMasters(ShopMaster shopMaster) throws Exception;

    public Integer getTotalCntForGetShopMasters(ShopMaster shopMaster);

    public Integer insertShopMaster(ShopMaster shopMaster) throws Exception;

    public Integer modifyShopMaster(ShopMaster shopMaster) throws Exception;

    public Integer deleteShopMaster(ShopMaster shopMaster) throws Exception;
}
