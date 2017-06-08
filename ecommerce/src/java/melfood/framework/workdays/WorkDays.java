/** 
 * 2016 WorkDays.java
 * Created by steven.min
 *  
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.workdays;

import melfood.framework.common.dto.BaseDto;

/**
 * TODO: Class description
 *
 * @author steven.min
 *
 */
public class WorkDays extends BaseDto implements java.io.Serializable {

	private String country;
	private String state;
	private String year;
	private String month;
	private int workdays;
	private String comment;

	public WorkDays() {
	}

	public WorkDays(String year, String month, int workdays) {
		this.year = year;
		this.month = month;
		this.workdays = workdays;
	}

	public WorkDays(String country, String state, String year, String month, int workdays) {
		this.country = country;
		this.state = state;
		this.year = year;
		this.month = month;
		this.workdays = workdays;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public int getWorkdays() {
		return workdays;
	}

	public void setWorkdays(int workdays) {
		this.workdays = workdays;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
