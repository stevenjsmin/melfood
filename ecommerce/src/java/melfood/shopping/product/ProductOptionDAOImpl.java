/** 
 * 2016 ProductOptionDAOImpl.java
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
public class ProductOptionDAOImpl extends BaseDAO implements ProductOptionDAO {

	@Override
	public List<ProductOptionValue> getProductOptions(ProductOptionValue productOption) throws Exception {
		return sqlSession.selectList("mySqlProductOptionDao.getProductOptions", productOption);
	}

	@Override
	public Integer getTotalCntForProductOptions(ProductOptionValue productOption) {
		return sqlSession.selectOne("mySqlProductOptionDao.getTotalCntForProductOptions", productOption);
	}

	@Override
	public Integer insertProductOption(ProductOptionValue productOption) throws Exception {
		int nextOptionSeq = sqlSession.selectOne("mySqlProductOptionDao.getNextProductOptionSeq", productOption.getProdId());
		productOption.setOptionSeq(nextOptionSeq);
		return sqlSession.insert("mySqlProductOptionDao.insertProductOption", productOption);
	}

	@Override
	public Integer modifyProductOption(ProductOptionValue productOption) throws Exception {
		return sqlSession.update("mySqlProductOptionDao.modifyProductOption", productOption);
	}

	@Override
	public Integer modifyProductOptionForNotNull(ProductOptionValue productOption) throws Exception {
		return sqlSession.update("mySqlProductOptionDao.modifyProductOptionForNotNull", productOption);
	}

	@Override
	public Integer deleteProductOption(ProductOptionValue productOption) throws Exception {
		return sqlSession.delete("mySqlProductOptionDao.deleteProductOption", productOption);
	}

	@Override
	public List<ProductOptionValue> getProductOptionValues(ProductOptionValue productOption) throws Exception {
		return sqlSession.selectList("mySqlProductOptionDao.getProductOptionValues", productOption);
	}

	@Override
	public Integer getTotalCntForProductOptionValues(ProductOptionValue productOption) {
		return sqlSession.selectOne("mySqlProductOptionDao.getTotalCntForProductOptionValues", productOption);
	}

	@Override
	public Integer insertProductOptionValue(ProductOptionValue productOption) throws Exception {
		int nextValueSeq = sqlSession.selectOne("mySqlProductOptionDao.getNextProductOptionValueSeq", productOption);
		productOption.setValueSeq(nextValueSeq);
		return sqlSession.insert("mySqlProductOptionDao.insertProductOptionValue", productOption);
	}

	@Override
	public Integer modifyProductOptionValue(ProductOptionValue productOption) throws Exception {
		return sqlSession.update("mySqlProductOptionDao.modifyProductOptionValue", productOption);
	}

	@Override
	public Integer modifyProductOptionValueForNotNull(ProductOptionValue productOption) throws Exception {
		return sqlSession.update("mySqlProductOptionDao.modifyProductOptionValueForNotNull", productOption);
	}

	@Override
	public Integer deleteProductOptionValue(ProductOptionValue productOption) throws Exception {
		return sqlSession.delete("mySqlProductOptionDao.deleteProductOptionValue", productOption);
	}

}
