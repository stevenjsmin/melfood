/** 
 * 2016 Category.java
 * Created by steven.min
 *  
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.shopping.product;

import melfood.framework.common.dto.BaseDto;

/**
 * TODO: Class description
 *
 * @author steven.min
 *
 */
public class Category extends BaseDto {
	private int categoryId;
	private String categoryName;
	private String categoryDescription;

	public Category() {
	}

	public Category(int categoryId) {
		this.categoryId = categoryId;
	}
	
	public Category(String categoryId) {
		this.categoryId = Integer.parseInt(categoryId);
	}

	public Category(int categoryId, String categoryName) {
		this.categoryId = categoryId;
		this.categoryName = categoryName;
	}
	
	public Category(String categoryId, String categoryName) {
		this.categoryId = Integer.parseInt(categoryId);
		this.categoryName = categoryName;
	}

	public Category(int categoryId, String categoryName, String categoryDescription) {
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.categoryDescription = categoryDescription;
	}

	/**
	 * @return the categoryId
	 */
	public int getCategoryId() {
		return categoryId;
	}

	/**
	 * @param categoryId the categoryId to set
	 */
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	/**
	 * @return the categoryName
	 */
	public String getCategoryName() {
		return categoryName;
	}

	/**
	 * @param categoryName the categoryName to set
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	/**
	 * @return the categoryDescription
	 */
	public String getCategoryDescription() {
		return categoryDescription;
	}

	/**
	 * @param categoryDescription the categoryDescription to set
	 */
	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}

}
