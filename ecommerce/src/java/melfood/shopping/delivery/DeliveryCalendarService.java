/** 
 * 2016 DeliveryCalendarService.java
 * Created by steven.min
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.shopping.delivery;

import melfood.framework.uitl.html.Option;
import melfood.framework.uitl.html.Properties;

import java.util.List;

/**
 * @author steven.min
 *
 */
public interface DeliveryCalendarService {
	
	public DeliveryCalendar getDeliveryCalendar(DeliveryCalendar deliveryCalendar) throws Exception;
	public List<DeliveryCalendar> getDeliveryCalendars(DeliveryCalendar deliveryCalendar) throws Exception;

	public Integer getTotalCntForDeliveryCalendars(DeliveryCalendar deliveryCalendar) throws Exception;
	public Boolean existDeliveryCalendar(DeliveryCalendar deliveryCalendar) throws Exception;
	public Integer insertDeliveryCalendar(DeliveryCalendar deliveryCalendar) throws Exception;
	public Integer modifyDeliveryCalendarForNotNull(DeliveryCalendar deliveryCalendar) throws Exception;
	public Integer deleteDeliveryCalendar(DeliveryCalendar deliveryCalendar) throws Exception;


	public List<Option> getDeliverableAreaList(DeliveryCalendar deliveryCalendar) throws Exception;
	public String generateCmbxForDeliverableArea(List<Option> deliverableAreas, Properties property) throws Exception;
	public String generateCmbxForDeliverableArea(List<Option> deliverableAreas, Properties property, boolean placeholder) throws Exception;
}
