/** 
 * 2016 ProductServiceImpl.java
 * Created by steven.min
 *  
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.shopping.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TODO: Class description
 *
 * @author steven.min
 *
 */
@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductDAO productDAO;

	@Override
	public Product getProduct(Product product) throws Exception {
		List<Product> products = this.getProducts(product);
		if (products != null && products.size() > 0) {
			return products.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<Product> getProducts(Product product) throws Exception {
		return productDAO.getProducts(product);
	}

	@Override
	public Integer getTotalCntForProducts(Product product) {
		return productDAO.getTotalCntForProducts(product);
	}

	@Override
	public Integer deleteProduct(Product product) throws Exception {
		return productDAO.deleteProduct(product);
	}

	@Override
	public Integer insertProduct(Product product) throws Exception {
		return productDAO.insertProduct(product);
	}

	@Override
	public Integer modifyProduct(Product product) throws Exception {
		return productDAO.modifyProduct(product);
	}

	@Override
	public Integer modifyProductForNotNull(Product product) throws Exception {
		return productDAO.modifyProductForNotNull(product);
	}

	@Override
	public boolean existProduct(Product product) throws Exception {
		return (this.getProduct(product) != null) ? true : false;
	}


}
