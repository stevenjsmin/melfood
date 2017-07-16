package melfood.shopping.shop;

import java.util.List;

/**
 * Created by Steven on 16/7/17.
 */
public interface ShopMasterDAO {

    public List<ShopTemplate> getShopTemplates(ShopTemplate shopTemplate) throws Exception;

    public List<ShopMaster> getShopMasters(ShopMaster shopMaster) throws Exception;

    public Integer getTotalCntForGetShopMasters(ShopMaster shopMaster);

    /**
     * 샵 정보를 저장 후, 저장된 샵ID를 반환한다.
     *
     * @param shopMaster
     * @return
     * @throws Exception
     */
    public Integer insertShopMaster(ShopMaster shopMaster) throws Exception;

    public Integer modifyShopMaster(ShopMaster shopMaster) throws Exception;

    public Integer deleteShopMaster(ShopMaster shopMaster) throws Exception;

    public Integer modifyShopGateImageFileInfo(ShopMaster shopMaster) throws Exception;
}
