/** 
 * 2016 CategoryService.java
 * Created by steven.min
 *  
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.shopping.product;

import java.util.List;

import melfood.framework.uitl.html.Properties;

/**
 * TODO : 설명
 *
 * @author steven.min
 *
 */
public interface CategoryService {
	public Category getCategory(Category category) throws Exception;
	public List<Category> getCategories(Category category) throws Exception;
	public Integer getTotalCntForCategories(Category category);

	public Integer deleteCategory(Category category) throws Exception;

	public Integer insertCategory(Category category) throws Exception;

	public Integer modifyCategory(Category category) throws Exception;
	public Integer modifyCategoryForNotNull(Category category) throws Exception;

	public String generateCmbx(Properties property) throws Exception;
	public String generateCmbx(Properties property, boolean placeholder) throws Exception;
	public String generateCmbx(Properties property, int defaultSelectedValue) throws Exception;
	public String generateCmbx(Properties property, int defaultSelectedValue, boolean placeholder) throws Exception;
	
	public boolean existCategory(Category category) throws Exception;


}
