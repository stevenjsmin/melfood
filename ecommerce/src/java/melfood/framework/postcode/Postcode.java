package melfood.framework.postcode;

import melfood.framework.common.dto.BaseDto;

public class Postcode extends BaseDto implements java.io.Serializable {

	private int postcodeId;
	private String postcode;
	private String suburb;
	private String state;
	private String type;
	private Double longitude;
	private Double latitude;
	private String createDatetime;
	private String modifyDatetime;
	private String creator;

	private String stateLabel;

	public Postcode() {
	}

	public Postcode(int postcodeId) {
		this.postcodeId = postcodeId;
	}

	public Postcode(String postcodeId) {
		this.postcodeId = Integer.parseInt(postcodeId);
	}

	public Postcode(String postcode, String suburb) {
		this.postcode = postcode;
		this.suburb = suburb;
	}

	public Postcode(String postcode, String suburb, String state) {
		this.postcode = postcode;
		this.suburb = suburb;
		this.state = state;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSuburb() {
		return suburb;
	}

	public void setSuburb(String suburb) {
		this.suburb = suburb;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public String getCreateDatetime() {
		return createDatetime;
	}

	public void setCreateDatetime(String createDatetime) {
		this.createDatetime = createDatetime;
	}

	public String getModifyDatetime() {
		return modifyDatetime;
	}

	public void setModifyDatetime(String modifyDatetime) {
		this.modifyDatetime = modifyDatetime;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	/**
	 * @return the stateLabel
	 */
	public String getStateLabel() {
		return stateLabel;
	}

	/**
	 * @param stateLabel the stateLabel to set
	 */
	public void setStateLabel(String stateLabel) {
		this.stateLabel = stateLabel;
	}

	public int getPostcodeId() {
		return postcodeId;
	}

	public void setPostcodeId(int postcodeId) {
		this.postcodeId = postcodeId;
	}

}
