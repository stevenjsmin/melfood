/** 
 * 2016 ProductOptionGroup.java
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
public class ProductOptionGroup extends BaseDto {

	private String optionLabel;
	private String optionCmbx;

	public ProductOptionGroup() {
	}

	public ProductOptionGroup(String optionLabel, String optionCmbx) {
		this.optionLabel = optionLabel;
		this.optionCmbx = optionCmbx;
	}

	public String getOptionLabel() {
		return optionLabel;
	}

	public void setOptionLabel(String optionLabel) {
		this.optionLabel = optionLabel;
	}

	public String getOptionCmbx() {
		return optionCmbx;
	}

	public void setOptionCmbx(String optionCmbx) {
		this.optionCmbx = optionCmbx;
	}

}
