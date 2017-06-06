/** 
 * 2016 ProductDAOImpl.java
 * Created by steven.min
 *  
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.shopping.product;

import java.util.List;

import org.springframework.stereotype.Repository;

import melfood.framework.core.BaseDAO;

/**
 * TODO: Class description
 *
 * @author steven.min
 *
 */
@Repository
public class ProductDAOImpl extends BaseDAO implements ProductDAO {

	@Override
	public List<Product> getProducts(Product product) throws Exception {
		return sqlSession.selectList("mySqlProductDao.getProducts", product);
	}

	@Override
	public Integer getTotalCntForProducts(Product product) {
		return sqlSession.selectOne("mySqlProductDao.getTotalCntForProducts", product);
	}

	@Override
	public Integer deleteProduct(Product product) throws Exception {
		return sqlSession.delete("mySqlProductDao.deleteProduct", product);
	}

	@Override
	public Integer insertProduct(Product product) throws Exception {
		int nextProdId = sqlSession.selectOne("mySqlProductDao.getNextProductId");
		product.setProdId(nextProdId);
		return sqlSession.insert("mySqlProductDao.insertProduct", product);
	}

	@Override
	public Integer modifyProduct(Product product) throws Exception {
		return sqlSession.update("mySqlProductDao.modifyProduct", product);
	}

	@Override
	public Integer modifyProductForNotNull(Product product) throws Exception {
		return sqlSession.update("mySqlProductDao.modifyProductForNotNull", product);
	}

	@Override
	public Integer setNullForCategory(int categoryId) throws Exception {
		return sqlSession.update("mySqlProductDao.setNullForCategory", categoryId);
	}

}
