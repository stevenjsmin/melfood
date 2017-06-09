/** 
 * 2016 ShopTemplateSection.java
 * Created by steven.min
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.shopping.shop;

import melfood.framework.common.dto.BaseDto;

public class ShopTemplateSection extends BaseDto {

	private String sellerId;
	private String layoutAttributeId;
	private String layoutAttributeName;
	private String fixed_contents;
	private String tags;

	public ShopTemplateSection() {
	}

	public ShopTemplateSection(String sellerId) {
		this.sellerId = sellerId;
	}

	public ShopTemplateSection(String sellerId, String layoutAttributeId) {
		this.sellerId = sellerId;
		this.layoutAttributeId = layoutAttributeId;
	}

	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public String getLayoutAttributeId() {
		return layoutAttributeId;
	}

	public void setLayoutAttributeId(String layoutAttributeId) {
		this.layoutAttributeId = layoutAttributeId;
	}

	public String getLayoutAttributeName() {
		return layoutAttributeName;
	}

	public void setLayoutAttributeName(String layoutAttributeName) {
		this.layoutAttributeName = layoutAttributeName;
	}

	public String getFixed_contents() {
		return fixed_contents;
	}

	public void setFixed_contents(String fixed_contents) {
		this.fixed_contents = fixed_contents;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

}
