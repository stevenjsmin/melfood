package melfood.shopping.grouppurchase.dao;

import melfood.shopping.grouppurchase.dto.GroupPurchase;
import melfood.shopping.product.ProductImage;

import java.util.List;

/**
 * Created by Steven on 14/6/17.
 */
public interface GroupPurchaseDAO {

    public List<GroupPurchase> getGroupPurchases(GroupPurchase groupPurchase) throws Exception;

    public Integer getTotalCntForGroupPurchases(GroupPurchase groupPurchase);

    public Integer deleteGroupPurchase(GroupPurchase groupPurchase) throws Exception;

    public Integer insertGroupPurchase(GroupPurchase groupPurchase) throws Exception;

    public Integer modifyGroupPurchase(GroupPurchase groupPurchase) throws Exception;

    public Integer modifyGroupPurchaseForNotNull(GroupPurchase groupPurchase) throws Exception;

    public Integer insertGroupPurchaseImage(ProductImage productImage) throws Exception;

    public Integer insertGroupPurchaseImages(List<ProductImage> productImages, int groupPurchaseId) throws Exception;

    public List<ProductImage> getProductImages(ProductImage productImage) throws Exception;
    public Integer getTotalCntForProductImages(ProductImage productImage);

    public Integer deleteProductImage(ProductImage productImage) throws Exception;
}
