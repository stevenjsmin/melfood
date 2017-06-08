/** 
 * 2016 ShopTemplate.java
 * Created by steven.min
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.shopping.shop;

import java.util.List;

import melfood.framework.common.dto.BaseDto;

public class ShopTemplate extends BaseDto {

	private String sellerId;
	private String layoutId;
	private String layoutName;
	private String description;

	// 템플릿을 구성하고있는 각 섹션집합
	private List<ShopTemplateSection> templateSections;

	public ShopTemplate() {
	}

	public ShopTemplate(String sellerId) {
		this.sellerId = sellerId;
	}

	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public String getLayoutId() {
		return layoutId;
	}

	public void setLayoutId(String layoutId) {
		this.layoutId = layoutId;
	}

	public String getLayoutName() {
		return layoutName;
	}

	public void setLayoutName(String layoutName) {
		this.layoutName = layoutName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 템플릿을 구성하고있는 각 섹션집합을 얻어온다.
	 * 
	 * @return
	 */
	public List<ShopTemplateSection> getTemplateSections() {
		return templateSections;
	}

	public void setTemplateSections(List<ShopTemplateSection> templateSections) {
		this.templateSections = templateSections;
	}

}
