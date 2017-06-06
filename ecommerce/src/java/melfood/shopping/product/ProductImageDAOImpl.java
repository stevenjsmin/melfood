/** 
 * 2016 ProductImageDAOImpl.java
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
public class ProductImageDAOImpl extends BaseDAO implements ProductImageDAO {

	@Override
	public List<ProductImage> getProductImages(ProductImage productImage) throws Exception {
		return sqlSession.selectList("mySqlProductImageDao.getProductImages", productImage);
	}

	@Override
	public Integer getTotalCntForProductImages(ProductImage productImage) {
		return sqlSession.selectOne("mySqlProductImageDao.getTotalCntForProductImages", productImage);
	}

	@Override
	public Integer deleteProductImage(ProductImage productImage) throws Exception {
		return sqlSession.delete("mySqlProductImageDao.deleteProductImage", productImage);
	}

	@Override
	public Integer insertProductImage(ProductImage productImage) throws Exception {
		int nextSeq = sqlSession.selectOne("mySqlProductImageDao.getNextProductImageSeq", productImage.getProdId());
		productImage.setImageSeq(nextSeq);
		return sqlSession.delete("mySqlProductImageDao.insertProductImage", productImage);
	}

	@Override
	public Integer insertProductImages(List<ProductImage> productImages, int prodId) throws Exception {
		int nextSeq = sqlSession.selectOne("mySqlProductImageDao.getNextProductImageSeq", prodId);
		for (int i = 0; i < productImages.size(); i++) {
			productImages.get(i).setImageSeq(nextSeq);
			nextSeq++;
		}
		return sqlSession.insert("mySqlProductImageDao.insertProductImages", productImages);
	}

	@Override
	public Integer modifyProductImage(ProductImage productImage) throws Exception {
		return sqlSession.update("mySqlProductImageDao.modifyProductImage", productImage);
	}

	@Override
	public Integer modifyProductImageForNotNull(ProductImage productImage) throws Exception {
		return sqlSession.update("mySqlProductImageDao.modifyProductImageForNotNull", productImage);
	}

}
