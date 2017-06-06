/** 
 * 2016 DeliveryCalendar.java
 * Created by steven.min
 *  
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.shopping.delivery;

import melfood.framework.common.dto.BaseDto;
import melfood.framework.postcode.Postcode;
import melfood.framework.user.User;

/**
 * TODO: Class description
 *
 * @author steven.min
 *
 */
public class DeliveryCalendar extends BaseDto {

	private String sellerId;
	private String yyyyMmDd;
	private int deliverySeq;

	private String addressPostcode;
	private String addressState;
	private String addressSuburb;
	private String addressStreet;
	private String addressNote;

	private String isPickup;
	private String amPm;

	private Postcode postcode;
	private User seller;

	public DeliveryCalendar() {
	}

	public DeliveryCalendar(String sellerId) {
		this.sellerId = sellerId;
	}

	public DeliveryCalendar(String sellerId, String yyyyMmDd) {
		this.sellerId = sellerId;
		this.yyyyMmDd = yyyyMmDd;
	}

	public DeliveryCalendar(String sellerId, String yyyyMmDd, int deliverySeq) {
		this.sellerId = sellerId;
		this.yyyyMmDd = yyyyMmDd;
		this.deliverySeq = deliverySeq;
	}

	public DeliveryCalendar(String sellerId, String yyyyMmDd, String deliverySeq) {
		this.sellerId = sellerId;
		this.yyyyMmDd = yyyyMmDd;
		this.deliverySeq = Integer.parseInt(deliverySeq);
	}

	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	/**
	 * 배달가능 년월일을 가저온다.<br>
	 * 년월일 형식은 YYYY-MM-DD 형식이어야한다.
	 * 
	 * @return
	 */
	public String getYyyyMmDd() {
		return yyyyMmDd;
	}

	/**
	 * 배달가능 년월일을 설정한다.<br>
	 * 년월일 형식은 YYYY-MM-DD 형식이어야한다.
	 * 
	 * @param yyyyMmDd
	 */
	public void setYyyyMmDd(String yyyyMmDd) {
		this.yyyyMmDd = yyyyMmDd;
	}

	/**
	 * 배달가능한 오전,오후 또는 종일을 값들중에 하나를 가저온다.<br>
	 * [AM | PM | ALL]
	 * 
	 * @return
	 */
	public String getAmPm() {
		return amPm;
	}

	/**
	 * 배달가능한 오전,오후 또는 종일을 값들중에 하나를 설정한다.<br>
	 * [AM | PM | ALL]
	 * 
	 * @param amPm
	 */
	public void setAmPm(String amPm) {
		this.amPm = amPm;
	}

	public Postcode getPostcode() {
		return postcode;
	}

	public void setPostcode(Postcode postcode) {
		this.postcode = postcode;
	}

	public User getSeller() {
		return seller;
	}

	public void setSeller(User seller) {
		this.seller = seller;
	}

	public String getIsPickup() {
		return isPickup;
	}

	public void setIsPickup(String isPickup) {
		this.isPickup = isPickup;
	}

	public int getDeliverySeq() {
		return deliverySeq;
	}

	public void setDeliverySeq(int deliverySeq) {
		this.deliverySeq = deliverySeq;
	}

	public String getAddressPostcode() {
		return addressPostcode;
	}

	public void setAddressPostcode(String addressPostcode) {
		this.addressPostcode = addressPostcode;
	}

	public String getAddressState() {
		return addressState;
	}

	public void setAddressState(String addressState) {
		this.addressState = addressState;
	}

	public String getAddressSuburb() {
		return addressSuburb;
	}

	public void setAddressSuburb(String addressSuburb) {
		this.addressSuburb = addressSuburb;
	}

	public String getAddressStreet() {
		return addressStreet;
	}

	public void setAddressStreet(String addressStreet) {
		this.addressStreet = addressStreet;
	}

	public String getAddressNote() {
		return addressNote;
	}

	public void setAddressNote(String addressNote) {
		this.addressNote = addressNote;
	}

}
