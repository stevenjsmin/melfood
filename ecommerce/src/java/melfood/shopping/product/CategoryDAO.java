/** 
 * 2016 CategoryDAO.java
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
public interface CategoryDAO {
	public List<Category> getCategories(Category category) throws Exception;
	public Integer getTotalCntForCategories(Category category);

	public Integer deleteCategory(Category category) throws Exception;

	public Integer insertCategory(Category category) throws Exception;
	
	public Integer modifyCategory(Category category) throws Exception;
	public Integer modifyCategoryForNotNull(Category category) throws Exception;

}
