/** 
 * 2016 DeliveryCalendarServiceImpl.java
 * Created by steven.min
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.shopping.delivery;

import melfood.framework.uitl.html.Option;
import melfood.framework.uitl.html.Properties;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author steven.min
 *
 */
@Service
public class DeliveryCalendarServiceImpl implements DeliveryCalendarService {

	@Autowired
	DeliveryCalendarDAO deliveryCalendarDAO;

	@Override
	public DeliveryCalendar getDeliveryCalendar(DeliveryCalendar deliveryCalendar) throws Exception {
		List<DeliveryCalendar> deliveryCalendars = this.getDeliveryCalendars(deliveryCalendar);
		if (deliveryCalendars != null && deliveryCalendars.size() > 0) {
			return deliveryCalendars.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<DeliveryCalendar> getDeliveryCalendars(DeliveryCalendar deliveryCalendar) throws Exception {

		// 검색시작년월일이 존재하지 않을경우 현재날짜 기준으로 앞으로 예정된 일짜에 해당하는 목록만 가저오게한다.
		if (StringUtils.isBlank(deliveryCalendar.getSearchDateFrom())) {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			String searchDateFrom = df.format(cal.getTime());
			deliveryCalendar.setSearchDateFrom(searchDateFrom);
		}

		return deliveryCalendarDAO.getDeliveryCalendars(deliveryCalendar);
	}

	@Override
	public Integer getTotalCntForDeliveryCalendars(DeliveryCalendar deliveryCalendar) throws Exception {
		if (StringUtils.isBlank(deliveryCalendar.getSearchDateFrom())) {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			String searchDateFrom = df.format(cal.getTime());
			deliveryCalendar.setSearchDateFrom(searchDateFrom);
		}

		return deliveryCalendarDAO.getTotalCntForDeliveryCalendars(deliveryCalendar);
	}

	@Override
	public Boolean existDeliveryCalendar(DeliveryCalendar deliveryCalendar) throws Exception {
		List<DeliveryCalendar> deliveryCalendars = this.getDeliveryCalendars(deliveryCalendar);
		if (deliveryCalendars != null && deliveryCalendars.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Integer insertDeliveryCalendar(DeliveryCalendar deliveryCalendar) throws Exception {
		return deliveryCalendarDAO.insertDeliveryCalendar(deliveryCalendar);
	}


	@Override
	public Integer modifyDeliveryCalendarForNotNull(DeliveryCalendar deliveryCalendar) throws Exception {
		return deliveryCalendarDAO.modifyDeliveryCalendarForNotNull(deliveryCalendar);
	}

	@Override
	public Integer deleteDeliveryCalendar(DeliveryCalendar deliveryCalendar) throws Exception {
		return deliveryCalendarDAO.deleteDeliveryCalendar(deliveryCalendar);
	}

	@Override
	public List<Option> getDeliverableAreaList(DeliveryCalendar deliveryCalendar) throws Exception {
		List<Option> options = new ArrayList<Option>();

		if (StringUtils.isBlank(deliveryCalendar.getUseYn())) {
			deliveryCalendar.setUseYn("Y");
		}

		List<DeliveryCalendar> deliveryCalendars = this.getDeliveryCalendars(deliveryCalendar);
		for (DeliveryCalendar area : deliveryCalendars) {
			options.add(new Option(area.getDeliveryBaseAddressPostcode(), area.getDeliveryBaseAddressSuburb()));
		}
		return options;
	}

	@Override
	public String generateCmbxForDeliverableArea(List<Option> deliverableAreas, Properties property) throws Exception {
		return this.generateCmbxForDeliverableArea(deliverableAreas, property, true);
	}

	@Override
	public String generateCmbxForDeliverableArea(List<Option> deliverableAreas, Properties property, boolean placeholder) throws Exception {
		DeliveryCalendar deliveryCalendar = new DeliveryCalendar();
		deliveryCalendar.setPagenationPage(0);
		deliveryCalendar.setPagenationPageSize(99999);
		deliveryCalendar.setUseYn("Y");

		List<Option> list = this.getDeliverableAreaList(deliveryCalendar);

		StringBuffer selectHtml = new StringBuffer("<select ");
		if (StringUtils.isNotBlank(property.getId())) selectHtml.append("id='" + property.getId() + "' ");
		if (StringUtils.isNotBlank(property.getName())) selectHtml.append("name='" + property.getName() + "' ");
		if (StringUtils.isNotBlank(property.getCssClass())) selectHtml.append("class='" + property.getCssClass() + "' ");
		if (StringUtils.isNotBlank(property.getCssStyle())) selectHtml.append("style='" + property.getCssStyle() + "' ");
		if (StringUtils.isNotBlank(property.getOnclick())) selectHtml.append("onclick='" + property.getOnclick() + "' ");
		if (StringUtils.isNotBlank(property.getOnchange())) selectHtml.append("onchange='" + property.getOnchange() + "' ");
		if (property.isMultipleSelect()) selectHtml.append("multiple='multiple' ");
		selectHtml.append("> \n");

		if (placeholder) selectHtml.append("<option value=''> - SELECT - </option>\n");

		if (list != null) {

			for (Option option : list) {
				selectHtml.append("<option value='" + option.getValue() + "'>" + option.getName() + "</option>\n");
			}
		}

		return selectHtml.toString();
	}


	
	
}
