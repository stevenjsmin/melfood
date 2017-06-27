package melfood.shopping.grouppurchase;

import melfood.framework.uitl.html.Option;
import melfood.shopping.grouppurchase.dto.GroupPurchase;
import melfood.shopping.product.ProductImage;

import java.util.List;

/**
 * Created by Steven on 14/6/17.
 */
public interface GroupPurchaseService {

    public GroupPurchase getGroupPurchase(int groupPurchaseId) throws Exception;
    public List<GroupPurchase> getGroupPurchases(GroupPurchase groupPurchase) throws Exception;

    public Integer getTotalCntForGroupPurchases(GroupPurchase groupPurchase);

    public Integer deleteGroupPurchase(GroupPurchase groupPurchase) throws Exception;

    public Integer insertGroupPurchase(GroupPurchase groupPurchase) throws Exception;

    public Integer modifyGroupPurchase(GroupPurchase groupPurchase) throws Exception;

    public Integer modifyGroupPurchaseForNotNull(GroupPurchase groupPurchase) throws Exception;

    public List<Option> getOrganizers()throws Exception;
    public List<Option> getOrganizers(String selectedUserId)throws Exception;
    public List<Option> getMarketSuburbs()throws Exception;
    public List<Option> getDiscountMethods()throws Exception;
    public List<Option> getStopSellingOptions()throws Exception;

    public List<ProductImage> transferFileToAttachementFileDb(int groupPurchaseId) throws Exception;

    public List<ProductImage> transferFileToAttachementFileDb(int groupPurchaseId, String creator) throws Exception;

    public ProductImage getProductImage(ProductImage productImage) throws Exception;
    public List<ProductImage> getProductImages(ProductImage productImage) throws Exception;

    public Integer getTotalCntForProductImages(ProductImage productImage);

    public Integer deleteProductImage(int prodId, int imageSeq) throws Exception;

    /**
     * 몰 프론트페이지게 보여줄 공구 리스트를 가져온다
     *
     * @param groupPurchase
     * @return
     * @throws Exception
     */
    public List<GroupPurchase> getGroupPurchaseForMallFront(GroupPurchase groupPurchase) throws Exception;

    /**
     * 몰 프론트페이지게 보여줄 공구 리스트를 가져오기위한 카운트
     *
     * @param groupPurchase
     * @return
     */
    public Integer getTotalCntGroupPurchaseForMallFront(GroupPurchase groupPurchase);
}
