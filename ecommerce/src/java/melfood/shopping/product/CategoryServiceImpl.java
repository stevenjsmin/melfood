/** 
 * 2016 CategoryServiceImpl.java
 * Created by steven.min
 *  
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.shopping.product;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import melfood.framework.uitl.html.Properties;

/**
 * TODO : 설명
 *
 * @author steven.min
 *
 */
@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	CategoryDAO categoryDAO;
	
	@Autowired
	ProductDAO productDAO;

	@Override
	public Category getCategory(Category paramCategory) throws Exception {
		List<Category> categories = this.getCategories(paramCategory);
		if (categories != null && categories.size() > 0) {
			return categories.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<Category> getCategories(Category category) throws Exception {
		return categoryDAO.getCategories(category);
	}

	@Override
	public Integer getTotalCntForCategories(Category category) {
		return categoryDAO.getTotalCntForCategories(category);
	}

	@Override
	public Integer deleteCategory(Category category) throws Exception {
		productDAO.setNullForCategory(category.getCategoryId());
		return categoryDAO.deleteCategory(category);
	}

	@Override
	public Integer insertCategory(Category category) throws Exception {
		return categoryDAO.insertCategory(category);
	}

	@Override
	public Integer modifyCategory(Category category) throws Exception {
		return categoryDAO.modifyCategory(category);
	}

	@Override
	public Integer modifyCategoryForNotNull(Category category) throws Exception {
		return categoryDAO.modifyCategoryForNotNull(category);
	}

	@Override
	public String generateCmbx(Properties property) throws Exception {
		return this.generateCmbx(property, 99999);
	}

	@Override
	public String generateCmbx(Properties property, int defaultSelectedValue) throws Exception {
		return this.generateCmbx(property, defaultSelectedValue, true);
	}

	@Override
	public String generateCmbx(Properties property, boolean placeholder) throws Exception {
		return this.generateCmbx(property, 99999, placeholder);
	}

	@Override
	public String generateCmbx(Properties property, int defaultSelectedValue, boolean placeholder) throws Exception {
		Category category = new Category();
		category.setPagenationPage(0);
		category.setPagenationPageSize(99999);
		List<Category> categories = this.getCategories(category);

		StringBuffer selectHtml = new StringBuffer("<select ");
		if (StringUtils.isNotBlank(property.getId())) selectHtml.append("id='" + property.getId() + "' ");
		if (StringUtils.isNotBlank(property.getName())) selectHtml.append("name='" + property.getName() + "' ");
		if (StringUtils.isNotBlank(property.getCssClass())) selectHtml.append("class='" + property.getCssClass() + "' ");
		if (StringUtils.isNotBlank(property.getCssStyle())) selectHtml.append("style='" + property.getCssStyle() + "' ");
		if (StringUtils.isNotBlank(property.getOnclick())) selectHtml.append("onclick='" + property.getOnclick() + "' ");
		if (StringUtils.isNotBlank(property.getOnchange())) selectHtml.append("onchange='" + property.getOnchange() + "' ");
		if (property.isMultipleSelect()) selectHtml.append("multiple='multiple' ");
		selectHtml.append("> \n");

		if (placeholder) selectHtml.append("<option value=''> - SELECT - </option>\n");

		if (categories != null) {

			for (Category aCategory : categories) {
				if (aCategory.getCategoryId() == defaultSelectedValue) {
					selectHtml.append("<option value='" + aCategory.getCategoryId() + "' selected>" + aCategory.getCategoryName() + "</option>\n");
				} else {
					selectHtml.append("<option value='" + aCategory.getCategoryId() + "'>" + aCategory.getCategoryName() + "</option>\n");
				}
			}
		}

		return selectHtml.toString();
	}

	@Override
	public boolean existCategory(Category category) throws Exception {
		return (this.getCategory(category) != null) ? true : false;
	}

}
