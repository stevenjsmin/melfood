/** 
 * 2015 Code.java
 * Created by Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.code;

import melfood.framework.common.dto.BaseDto;

/**
 * Framework Common Code Model
 * 
 * @author Steven J.S Min
 * 
 */
public class Code extends BaseDto {

	private String category;
	private String type;
	private String value;
	private String label;
	private String description;
	private int displayOrder;

	public Code() {
	}

	public Code(String category) {
		this.category = category;
	}

	public Code(String category, String type) {
		this.category = category;
		this.type = type;
	}

	public Code(String category, String type, String value) {
		this.category = category;
		this.type = type;
		this.value = value;
	}

	public Code(String category, String type, String value, String label) {
		this.category = category;
		this.type = type;
		this.label = label;
		this.value = value;
	}

	public Code(String category, String type, String value, String label, String description) {
		this.category = category;
		this.type = type;
		this.value = value;
		this.label = label;
		this.description = description;
	}

	public Code(String category, String type, String value, String label, String description, int displayOrder) {
		this.category = category;
		this.type = type;
		this.value = value;
		this.label = label;
		this.description = description;
		this.displayOrder = displayOrder;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}
