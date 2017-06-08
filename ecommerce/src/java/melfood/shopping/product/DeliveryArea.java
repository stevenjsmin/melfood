/** 
 * 2016 DeliveryArea.java
 * Created by steven.min
 *  
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.shopping.product;

import melfood.framework.common.dto.BaseDto;
import melfood.framework.postcode.Postcode;

/**
 * TODO: Class description
 *
 * @author steven.min
 *
 */
public class DeliveryArea extends BaseDto {
	private int prodId;
	private int postcodeId;

	private Postcode postcode;

	public DeliveryArea() {
		this.postcode = new Postcode();
	}

	public DeliveryArea(int prodId, int postcodeId) {
		this.prodId = prodId;
		this.postcodeId = postcodeId;
	}

	public DeliveryArea(String prodId, String postcodeId) {
		this.prodId = Integer.parseInt(prodId);
		this.postcodeId = Integer.parseInt(postcodeId);
	}

	public int getProdId() {
		return prodId;
	}

	public void setProdId(int prodId) {
		this.prodId = prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = Integer.parseInt(prodId);
	}

	public int getPostcodeId() {
		return postcodeId;
	}

	public void setPostcodeId(int postcodeId) {
		this.postcodeId = postcodeId;
	}

	public void setPostcodeId(String postcodeId) {
		this.postcodeId = Integer.parseInt(postcodeId);
	}

	public Postcode getPostcode() {
		return postcode;
	}

	public void setPostcode(Postcode postcode) {
		this.postcode = postcode;
	}


}
