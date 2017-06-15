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
	private String btwnFromHhmm;
	private String btwnToHhmm;
	private String amPm;

	private String orderingStartDt; // 주문 받을 일시
	private String orderingEndDt; // 주문을 더이상 받지 않을 일시

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

	public String getBtwnFromHhmm() {
		return btwnFromHhmm;
	}

	public void setBtwnFromHhmm(String btwnFromHhmm) {
		this.btwnFromHhmm = btwnFromHhmm;
	}

	public String getBtwnToHhmm() {
		return btwnToHhmm;
	}

	public void setBtwnToHhmm(String btwnToHhmm) {
		this.btwnToHhmm = btwnToHhmm;
	}

	public void setAmPm(String amPm) {
		this.amPm = amPm;
	}

	public String getAmPm() {

		return amPm;
	}

	public String getOrderingStartDt() {
		return orderingStartDt;
	}

	public void setOrderingStartDt(String orderingStartDt) {
		this.orderingStartDt = orderingStartDt;
	}

	public String getOrderingEndDt() {
		return orderingEndDt;
	}

	public void setOrderingEndDt(String orderingEndDt) {
		this.orderingEndDt = orderingEndDt;
	}
}
