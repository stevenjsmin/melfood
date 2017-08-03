/**
 * 2016 DeliveryCalendar.java
 * Created by steven.min
 * <p>
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
 */
public class DeliveryCalendar extends BaseDto {

    private Integer deliveryCalendarId;
    private String sellerId;
    private String yyyyMmDd;
    private String amPm;
    private String estDeliverytimeFromHhmm;
    private String estDeliverytimeToHhmm;
    private String deliveryBaseAddressPostcode;
    private String deliveryBaseAddressState;
    private String deliveryBaseAddressSuburb;
    private String deliveryBaseAddressStreet;
    private String deliveryBaseAddressNote;
    private Float deliveryFeePerKm;
    private Float deliveryBasicFee;
    private Integer deliveryLimitKm;
    private String deliveryBaseAddressGmapLatitude;
    private String deliveryBaseAddressGmapLongitude;
    private String deliveryBaseAddressGmapFormattedAddress;

    private Postcode postcode;
    private User seller;

    public DeliveryCalendar() {
    }

    public DeliveryCalendar(Integer deliveryCalendarId) {
        this.deliveryCalendarId = deliveryCalendarId;
    }

    public DeliveryCalendar(String sellerId) {
        this.sellerId = sellerId;
    }

    public DeliveryCalendar(String sellerId, String yyyyMmDd) {
        this.sellerId = sellerId;
        this.yyyyMmDd = yyyyMmDd;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public Integer getDeliveryCalendarId() {
        return deliveryCalendarId;
    }

    public void setDeliveryCalendarId(Integer deliveryCalendarId) {
        this.deliveryCalendarId = deliveryCalendarId;
    }

    public String getYyyyMmDd() {
        return yyyyMmDd;
    }

    public void setYyyyMmDd(String yyyyMmDd) {
        this.yyyyMmDd = yyyyMmDd;
    }

    public String getAmPm() {
        return amPm;
    }

    public void setAmPm(String amPm) {
        this.amPm = amPm;
    }

    public String getEstDeliverytimeFromHhmm() {
        return estDeliverytimeFromHhmm;
    }

    public void setEstDeliverytimeFromHhmm(String estDeliverytimeFromHhmm) {
        this.estDeliverytimeFromHhmm = estDeliverytimeFromHhmm;
    }

    public String getEstDeliverytimeToHhmm() {
        return estDeliverytimeToHhmm;
    }

    public void setEstDeliverytimeToHhmm(String estDeliverytimeToHhmm) {
        this.estDeliverytimeToHhmm = estDeliverytimeToHhmm;
    }

    public String getDeliveryBaseAddressPostcode() {
        return deliveryBaseAddressPostcode;
    }

    public void setDeliveryBaseAddressPostcode(String deliveryBaseAddressPostcode) {
        this.deliveryBaseAddressPostcode = deliveryBaseAddressPostcode;
    }

    public String getDeliveryBaseAddressState() {
        return deliveryBaseAddressState;
    }

    public void setDeliveryBaseAddressState(String deliveryBaseAddressState) {
        this.deliveryBaseAddressState = deliveryBaseAddressState;
    }

    public String getDeliveryBaseAddressSuburb() {
        return deliveryBaseAddressSuburb;
    }

    public void setDeliveryBaseAddressSuburb(String deliveryBaseAddressSuburb) {
        this.deliveryBaseAddressSuburb = deliveryBaseAddressSuburb;
    }

    public String getDeliveryBaseAddressStreet() {
        return deliveryBaseAddressStreet;
    }

    public void setDeliveryBaseAddressStreet(String deliveryBaseAddressStreet) {
        this.deliveryBaseAddressStreet = deliveryBaseAddressStreet;
    }

    public String getDeliveryBaseAddressNote() {
        return deliveryBaseAddressNote;
    }

    public void setDeliveryBaseAddressNote(String deliveryBaseAddressNote) {
        this.deliveryBaseAddressNote = deliveryBaseAddressNote;
    }

    public Float getDeliveryFeePerKm() {
        return deliveryFeePerKm;
    }

    public void setDeliveryFeePerKm(Float deliveryFeePerKm) {
        this.deliveryFeePerKm = deliveryFeePerKm;
    }

    public Float getDeliveryBasicFee() {
        return deliveryBasicFee;
    }

    public void setDeliveryBasicFee(Float deliveryBasicFee) {
        this.deliveryBasicFee = deliveryBasicFee;
    }

    public Integer getDeliveryLimitKm() {
        return deliveryLimitKm;
    }

    public void setDeliveryLimitKm(Integer deliveryLimitKm) {
        this.deliveryLimitKm = deliveryLimitKm;
    }

    public String getDeliveryBaseAddressGmapLatitude() {
        return deliveryBaseAddressGmapLatitude;
    }

    public void setDeliveryBaseAddressGmapLatitude(String deliveryBaseAddressGmapLatitude) {
        this.deliveryBaseAddressGmapLatitude = deliveryBaseAddressGmapLatitude;
    }

    public String getDeliveryBaseAddressGmapLongitude() {
        return deliveryBaseAddressGmapLongitude;
    }

    public void setDeliveryBaseAddressGmapLongitude(String deliveryBaseAddressGmapLongitude) {
        this.deliveryBaseAddressGmapLongitude = deliveryBaseAddressGmapLongitude;
    }

    public String getDeliveryBaseAddressGmapFormattedAddress() {
        return deliveryBaseAddressGmapFormattedAddress;
    }

    public void setDeliveryBaseAddressGmapFormattedAddress(String deliveryBaseAddressGmapFormattedAddress) {
        this.deliveryBaseAddressGmapFormattedAddress = deliveryBaseAddressGmapFormattedAddress;
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
}
