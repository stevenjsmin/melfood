/** 
 * 2016 CategoryDAOImpl.java
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
public class CategoryDAOImpl extends BaseDAO implements CategoryDAO {

	@Override
	public List<Category> getCategories(Category category) throws Exception {
		return sqlSession.selectList("mySqlCategoryDao.getCategories", category);
	}

	@Override
	public Integer getTotalCntForCategories(Category category) {
		return sqlSession.selectOne("mySqlCategoryDao.getTotalCntForCategories", category);
	}

	@Override
	public Integer deleteCategory(Category category) throws Exception {
		return sqlSession.delete("mySqlCategoryDao.deleteCategory", category);
	}

	@Override
	public Integer insertCategory(Category category) throws Exception {
		int nextId = sqlSession.selectOne("mySqlCategoryDao.getNextCategoryId");
		category.setCategoryId(nextId);
		return sqlSession.delete("mySqlCategoryDao.insertCategory", category);
	}

	@Override
	public Integer modifyCategory(Category category) throws Exception {
		return sqlSession.update("mySqlCategoryDao.modifyCategory", category);
	}

	@Override
	public Integer modifyCategoryForNotNull(Category category) throws Exception {
		return sqlSession.update("mySqlCategoryDao.modifyCategoryForNotNull", category);
	}

}
