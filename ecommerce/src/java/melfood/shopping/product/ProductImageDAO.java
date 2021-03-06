/** 
 * 2016 ProductImageDAO.java
 * Created by steven.min
 *  
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.shopping.product;

import java.util.List;

/**
 * TODO: Class description
 *
 * @author steven.min
 *
 */
public interface ProductImageDAO {
	public List<ProductImage> getProductImages(ProductImage productImage) throws Exception;
	public Integer getTotalCntForProductImages(ProductImage productImage);

	public Integer deleteProductImage(ProductImage productImage) throws Exception;

	public Integer insertProductImage(ProductImage productImage) throws Exception;
	public Integer insertProductImages(List<ProductImage> productImages, int prodId) throws Exception;	
	
	public Integer modifyProductImage(ProductImage productImage) throws Exception;
	public Integer modifyProductImageForNotNull(ProductImage productImage) throws Exception;
}
