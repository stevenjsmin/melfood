package melfood.shopping.shop;

import melfood.framework.uitl.html.Option;

import java.util.List;

/**
 * Created by Steven on 16/7/17.
 */
public interface ShopMasterService {

    public ShopMaster getShopMaster(ShopMaster shopMaster) throws Exception;
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

    public List<ShopTemplate> getShopTemplates(ShopTemplate shopTemplate) throws Exception;

    public List<Option> getShopTemplateOptions(ShopTemplate shopTemplate) throws Exception;

    public List<Option> getShopTemplateOptions(ShopTemplate shopTemplate, int selectedTemplateId) throws Exception;

    public int transferToAttachementFileDb(int shopId) throws Exception;

    public Integer removeShopGateImageFileInfo(int shopId) throws Exception;


}
