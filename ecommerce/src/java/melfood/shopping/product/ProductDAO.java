/** 
 * 2016 ProductDAO.java
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
public interface ProductDAO {
	public List<Product> getProducts(Product product) throws Exception;
	public Integer getTotalCntForProducts(Product product);

	public Integer deleteProduct(Product product) throws Exception;

	public Integer insertProduct(Product product) throws Exception;
	
	public Integer modifyProduct(Product product) throws Exception;
	public Integer modifyProductForNotNull(Product product) throws Exception;
	
	public Integer setNullForCategory(int categoryId) throws Exception;
}
